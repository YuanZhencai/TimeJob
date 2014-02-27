package com.wcs.filenet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import com.ibm.filenet.helper.ce.util.Assert;
import com.ibm.filenet.helper.pe.BPMException;
import com.ibm.filenet.helper.pe.BPMWorkflowServices;
import com.ibm.filenet.helper.pe.QueueElementHelper;
import com.ibm.filenet.helper.pe.QueueHelper;
import com.ibm.filenet.helper.pe.RosterHelper;
import com.ibm.filenet.helper.pe.SessionHelper;
import com.ibm.filenet.helper.pe.StepElementHelper;
import com.ibm.filenet.helper.pe.WorkObjectHelper;
import com.ibm.filenet.helper.pe.util.ToStringUtil;
import com.ibm.filenet.helper.vo.WorkItem;
import com.wcs.connection.PEConnection;

import filenet.vw.api.VWException;
import filenet.vw.api.VWFetchType;
import filenet.vw.api.VWParticipant;
import filenet.vw.api.VWQueue;
import filenet.vw.api.VWQueueElement;
import filenet.vw.api.VWQueueQuery;
import filenet.vw.api.VWRoster;
import filenet.vw.api.VWRosterElement;
import filenet.vw.api.VWRosterQuery;
import filenet.vw.api.VWSession;
import filenet.vw.api.VWStepElement;
import filenet.vw.api.VWWorkObject;
import filenet.vw.api.VWWorkObjectNumber;
import filenet.vw.api.VWXMLData;

public class PETest {
    private static Logger logger = Logger.getLogger(PETest.class);

    private void setDataMap(VWStepElement stepElement, Map<String, Object> dataMap) throws VWException {
        if (dataMap == null) return;
        Assert.notNull(stepElement, "流程实例为NULL.");
        for (String attrName : dataMap.keySet()) {
            Object attrValue = dataMap.get(attrName);
            if ((attrName != null) && (attrName.equals("selectedResponse"))) {
                if ((attrValue != null) && (!(attrValue.equals("")))) stepElement.setSelectedResponse(attrValue.toString());
                else {
                    logger.error("设置的selectedResponse为空！");
                }
            } else if ((attrName != null) && (attrName.equals("vo"))) {
                if ((attrValue != null) && (!(attrValue.equals("")))) {
                    String xmlContent = attrValue.toString();
                    VWXMLData xmlData = new VWXMLData();
                    xmlData.setXML(xmlContent);
                    stepElement.setParameterValue("vo", xmlData, true);
                } else {
                    logger.error("设置的xmlContent为空！");
                }
            } else if (attrValue != null) stepElement.setParameterValue(attrName, attrValue, true);
        }
    }

    public void test() {
        VWSession session = null;
        try {
            session = PEConnection.getInstance().getSession("fnadmin", "fnadmin123");
            BPMWorkflowServices bpmWorkflowServices = new BPMWorkflowServices(session);
            List<WorkItem> workItems = bpmWorkflowServices.queryWorkItemsInRoster(null, null);
            logger.debug("workItems:" + workItems.size());
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (session != null && session.isLoggedOn()) {
                try {
                    session.logoff();
                } catch (VWException e) {
                    logger.error("", e);
                }
            }
        }
    }

    public void createFlow(VWSession session, String workflowName, Map<String, Object> dataMap) {
        try {
            VWStepElement stepElement = session.createWorkflow(workflowName);

            setDataMap(stepElement, dataMap);
            stepElement.doDispatch();
            // String assignUserID = "liqing1";
            // stepElement.doLock(true);
            // stepElement.doReassign(assignUserID , true, "Inbox");
            logger.debug("WorkflowNumber:" + stepElement.getWorkflowNumber());
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (session != null && session.isLoggedOn()) {
                try {
                    session.logoff();
                } catch (VWException e) {
                    logger.error("", e);
                }
            }
        }
    }

    public String getDistinguishedNameByUsername(VWSession session, String userName) {
        String distinguishedName = null;
        try {
            int userId = session.convertUserNameToId(userName);
            VWParticipant participant = session.convertIdToUserNamePx(userId);
            distinguishedName = participant.getDistinguishedName();
            logger.debug("distinguishedName:" + distinguishedName);
        } catch (VWException e) {
            logger.error("", e);
        }
        return distinguishedName;
    }

    public void create() {
        VWSession session = null;
        try {
            session = PEConnection.getInstance().getSession("fnadmin", "fnadmin123");
            // session = PEConnection.getInstance().getSession("liqing1", "tihfilenet");

            VWStepElement stepElement = session.createWorkflow("Yuan_Workflow");
            String[] parameterNames = stepElement.getParameterNames();
            for (String parameterName : parameterNames) {
                logger.debug("parameterName:" + parameterName + " value:" + stepElement.getParameterValue(parameterName));
            }
            ArrayList<String> usernames = new ArrayList<String>();
            ArrayList<VWParticipant> participants = new ArrayList<VWParticipant>();
            usernames.add("liqing1");
            for (String username : usernames) {
                int userId = session.convertUserNameToId(username);
                VWParticipant participant = session.convertIdToUserNamePx(userId);
                participants.add(participant);
            }
            stepElement.setParameterValue("step1_Tracker", participants.toArray(), true);
            stepElement.doDispatch();
            logger.debug("WorkflowNumber:" + stepElement.getWorkflowNumber());

            // PETest peTest = new PETest();
            // Map<String, Object> dataMap = new HashMap<String, Object>();
            // ArrayList<String> usernames = new ArrayList<String>();
            // ArrayList<String> participants = new ArrayList<String>();
            // usernames.add("liqing1");
            // for (String username : usernames) {
            // participants.add(peTest.getDistinguishedNameByUsername(session, username));
            // }
            // dataMap.put("participant", participants.toArray());
            // peTest.createFlow(session, "WorkflowName", null);
            // String condition = "F_WobNum = x'49E806BCFE40634497F7421F0D72F7F4'";
            // List<VWStepElement> stepElements = RosterHelper.getStepElements(session, condition);
            // logger.debug("stepElements:" + stepElements.size());
            // VWStepElement stepElement = stepElements.get(0);
            // stepElement.doLock(true);
            // stepElement.doDispatch();
            // stepElement.doLock(true);
            // stepElement.doReassign(peTest.getDistinguishedNameByUsername(session, "chenhong"), true, "Inbox");

        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (session != null && session.isLoggedOn()) {
                try {
                    session.logoff();
                } catch (VWException e) {
                    logger.error("", e);
                }
            }
        }
    }

    public void startWorkflow() {
        BPMWorkflowServices bpmWorkflowService = null;
        try {
            VWSession session = SessionHelper.logon("liqing1", "tihfilenet");
            bpmWorkflowService = new BPMWorkflowServices(session);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<String> userNames = new ArrayList<String>();
            // 用户liqing1
            userNames.add("chenhong");
            userNames.add("linlin");
            // 设置参数step1_Tracker的值
            dataMap.put("participant", userNames.toArray());
            // 启动流程
            bpmWorkflowService.startProcess("WorkflowName", dataMap);
        } catch (BPMException e) {
            logger.error("创建流程失败", e);
        } finally {
            if (bpmWorkflowService != null) {
                bpmWorkflowService.destroyBPMWorkflowServices();
            }
        }
    }

    public void completeStepWorkflow() {
        BPMWorkflowServices bpmWorkflowService = null;
        try {
            VWSession session = SessionHelper.logon("chenhong", "tihfilenet");
            bpmWorkflowService = new BPMWorkflowServices(session);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            // 通过流程单号完成工作项
            bpmWorkflowService.completeWorkItem("19A1E491586DCA44A23B259EFA9C1AC8", dataMap);
        } catch (BPMException e) {
            logger.error("完成节点失败", e);
        } finally {
            if (bpmWorkflowService != null) {
                bpmWorkflowService.destroyBPMWorkflowServices();
            }
        }
    }

    public void startProcess() {
        BPMWorkflowServices bpmWorkflowService = null;
        try {
            VWSession session = SessionHelper.logon("liqing1", "tihfilenet");
            bpmWorkflowService = new BPMWorkflowServices(session);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<String> userNames = new ArrayList<String>();
            // 用户liqing1
            userNames.add("liqing1");
            List<VWParticipant> participants = SessionHelper.getParticipants(session, userNames);
            // 设置参数step1_Tracker的值
            dataMap.put("step1_Tracker", participants.toArray());
            // 启动流程
            bpmWorkflowService.startProcess("Yuan_Workflow", dataMap);
        } catch (BPMException e) {
            logger.error("创建流程失败", e);
        } finally {
            if (bpmWorkflowService != null) {
                bpmWorkflowService.destroyBPMWorkflowServices();
            }
        }
    }

    public void queryWorkItemsInMyInbox() {
        BPMWorkflowServices bpmWorkflowService = null;
        try {
            VWSession vwSession = SessionHelper.logon("liqing1", "tihfilenet");
            bpmWorkflowService = new BPMWorkflowServices(vwSession);
            // 用主题字段来进行过滤查找任务
            String filter = "F_Subject = 'Yuan_Workflow_Subject'";
            // 查找我的收件箱中的任务
            List<WorkItem> workItems = bpmWorkflowService.queryWorkItemsInMyInbox(filter, null);
            logger.debug("My Inbox workItems " + workItems.size());
        } catch (BPMException e) {
            logger.error("查找任务失败", e);
        } finally {
            if (bpmWorkflowService != null) {
                bpmWorkflowService.destroyBPMWorkflowServices();
            }
        }
    }

    public void queryWorkItemsInDocManager() {
        BPMWorkflowServices bpmWorkflowService = null;
        try {
            VWSession vwSession = SessionHelper.logon("liqing1", "tihfilenet");
            bpmWorkflowService = new BPMWorkflowServices(vwSession);
            // 用主题字段来进行过滤查找任务
            String filter = "F_Subject = 'Yuan_Workflow_Subject'";
            // 查找docManager的公共收件箱中的任务
            List<WorkItem> workItems = bpmWorkflowService.queryWorkItems("docManager", filter, null);
            logger.debug("My Inbox workItems " + workItems.size());
        } catch (BPMException e) {
            logger.error("查找任务失败", e);
        } finally {
            if (bpmWorkflowService != null) {
                bpmWorkflowService.destroyBPMWorkflowServices();
            }
        }
    }

    public void completeStep1WorkItem() {
        BPMWorkflowServices bpmWorkflowService = null;
        try {
            VWSession session = SessionHelper.logon("liqing1", "tihfilenet");
            bpmWorkflowService = new BPMWorkflowServices(session);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            // 路由值
            dataMap.put("state", "toStep2");
            List<String> userNames = new ArrayList<String>();
            // 用户chenhong
            userNames.add("chenhong");
            userNames.add("linlin");
            List<VWParticipant> participants = SessionHelper.getParticipants(session, userNames);
            // 设置参数step1_Tracker的值
            dataMap.put("step2_Tracker", participants.toArray());
            // 通过流程单号完成工作项
            bpmWorkflowService.completeWorkItem("A85D73D0CF3F124D9D679628701399FC", dataMap);
        } catch (BPMException e) {
            logger.error("完成节点失败", e);
        } finally {
            if (bpmWorkflowService != null) {
                bpmWorkflowService.destroyBPMWorkflowServices();
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void completeStep2WorkItem() {
        BPMWorkflowServices bpmWorkflowService = null;
        VWSession session = null;
        try {
            session = SessionHelper.logon("linlin", "tihfilenet");
            StringBuffer filter = new StringBuffer();
            filter.append("1 = 1");
            filter.append(" AND F_Subject = 'Yuan_Workflow_Subject'");
            filter.append(" AND F_WorkFlowNumber = x'362A1228D5C5CA47B16F09D9A02F6EB8'");
//            int userId = new Long(session.getCurrentUserSecId()).intValue();
//            logger.info("userId:" + userId);
//            filter.append(" AND F_BoundUser =").append(userId);

             VWRoster roster = session.getRoster("DefaultRoster");
             VWRosterQuery rQuery = roster.createQuery(null, null, null, 0, filter.toString(), null,
             VWFetchType.FETCH_TYPE_ROSTER_ELEMENT);
            
             logger.info("Fetch Count:" + rQuery.fetchCount());
             logger.info("hasNext:" + rQuery.hasNext());
             ToStringBuilder b = ToStringUtil.createBuilder(roster);
            
             while (rQuery.hasNext()) {
             VWRosterElement rosterElement = (VWRosterElement) rQuery.next();
             VWWorkObject wo = rosterElement.fetchWorkObject(false, false);
             b.append(WorkObjectHelper.vwString(wo));
             VWStepElement st = rosterElement.fetchStepElement(false, false);
             b.append(StepElementHelper.vwString(st));
             logger.info("vwString:" + b.toString());
             String workflowNumber = st.getWorkflowNumber();
             String workFlowNumber = st.getWorkFlowNumber();
             String workObjectNumber = st.getWorkObjectNumber();
             logger.info("workflowNumber:" + workflowNumber);
             logger.info("workFlowNumber:" + workFlowNumber);
             logger.info("workObjectNumber:" + workObjectNumber);
             }

//             VWQueue vwQueue = session.getQueue("Inbox");
//             vwQueue.setBufferSize(100);
//             VWQueueQuery qQuery = vwQueue.createQuery(null, null, null, VWQueue.QUERY_NO_OPTIONS, filter.toString(), null,
//             VWFetchType.FETCH_TYPE_STEP_ELEMENT);
//             logger.info("Fetch Count:" + qQuery.fetchCount());
//             logger.info("hasNext:" + qQuery.hasNext());
//             while (qQuery.hasNext()) {
//             VWStepElement st = (VWStepElement) qQuery.next();
//             logger.info("swString:" + StepElementHelper.vwString(st));
//             String workflowNumber = st.getWorkflowNumber();
//             String workFlowNumber = st.getWorkFlowNumber();
//             String workObjectNumber = st.getWorkObjectNumber();
//             logger.info("workflowNumber:" + workflowNumber);
//             logger.info("workFlowNumber:" + workFlowNumber);
//             logger.info("workObjectNumber:" + workObjectNumber);
             // st.doLock(true);
             // st.setSelectedResponse("approve");
             // st.doSave(false);
             // st.doDispatch();
            
//             }

            // VWStepElement stepElement = (VWStepElement) qQuery.next();

            // VWWorkObject workObject = QueueHelper.getWorkObjectByWorkflowNumber(session, "Inbox",
            // "7A16C545D71C8343A1471B6D178FE3C1");
            // logger.debug("workObject:" + workObject.getWorkflowNumber());
            // stepElement.doLock(true);
            // stepElement.setSelectedResponse("approve");
            // stepElement.doSave(false);
            // stepElement.doDispatch();

            // bpmWorkflowService = new BPMWorkflowServices(session);
            // Map<String, Object> dataMap = new HashMap<String, Object>();
            // dataMap.put("selectedResponse", "approve");
            // // 通过流程单号完成工作项
            // bpmWorkflowService.completeWorkItem("7A16C545D71C8343A1471B6D178FE3C1", dataMap);
        } catch (Exception e) {
            logger.error("完成节点失败", e);
        } finally {
            SessionHelper.logoff(session);
        }
    }

    public void completeStep3WorkItem() {
        BPMWorkflowServices bpmWorkflowService = null;
        try {
            VWSession session = SessionHelper.logon("linlin", "tihfilenet");
            bpmWorkflowService = new BPMWorkflowServices(session);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            // 通过流程单号完成工作项
            bpmWorkflowService.completeWorkItem("FC0309C56641C342AC8D3D96D51F9633", dataMap);
        } catch (BPMException e) {
            logger.error("完成节点失败", e);
        } finally {
            if (bpmWorkflowService != null) {
                bpmWorkflowService.destroyBPMWorkflowServices();
            }
        }
    }

    public void getStepElementByWorkflowNumber(){
        VWSession session = SessionHelper.logon("liqing1", "tihfilenet");
        try {
            VWStepElement stepElement = RosterHelper.getStepElementByWorkflowNumber(session, "4E443C037E76EC438D2FC80586FA454D");
            logger.debug(" getWorkflowNumber:"+stepElement.getWorkflowNumber());
            logger.debug("getWorkObjectNumber:"+stepElement.getWorkObjectNumber());
            logger.debug("getStepName:" + stepElement.getStepName());
//            stepElement.doLock(true);
//            stepElement.setSelectedResponse("approve");
//            stepElement.doSave(false);
//            stepElement.doDispatch();
        } catch (VWException e) {
            logger.error("", e);
        }
        try {
            session.logoff();
        } catch (VWException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
    	VWSession vwSession = SessionHelper.logon("linlin", "tihfilenet");
    	try {
    		VWRoster roster = vwSession.getRoster("DefaultRoster");
            int queryFlags = 0;
            String filter = "F_WobNum = :WobNum";
            VWWorkObjectNumber vwWobNum = new VWWorkObjectNumber("01CBD9F404672541A0899C57B2A314B2");
            Object[] substitutionVars = { vwWobNum };
            roster.setBufferSize(1);
            VWRosterQuery rQuery = roster.createQuery(null, null, null, queryFlags, filter, substitutionVars, VWFetchType.FETCH_TYPE_WORKOBJECT);
            System.out.println("[fetchCount]" + rQuery.fetchCount());
            if (rQuery.hasNext()) {
            	VWWorkObject re = (VWWorkObject) rQuery.next();
            	VWStepElement st = re.fetchStepElement();
            	System.out.println("[getStepName]"+st.getStepName());
            }
		} catch (VWException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
    		vwSession.logoff();
        } catch (VWException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

}
