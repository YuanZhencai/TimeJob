package com.wcs.filenet.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.ibm.filenet.helper.pe.BPMException;
import com.ibm.filenet.helper.pe.BPMWorkflowServices;
import com.ibm.filenet.helper.pe.SessionHelper;
import com.ibm.filenet.helper.vo.HistoryVo;
import com.ibm.filenet.helper.vo.WorkItem;
import com.wcs.filenet.service.WorkflowService;

import filenet.vw.api.VWParticipant;
import filenet.vw.api.VWSession;

@ManagedBean
@ViewScoped
public class WorkflowBean {
    private static Logger logger = Logger.getLogger(WorkflowBean.class);

    @Inject
    private WorkflowService workflowService;

    private List<WorkItem> workItems = null;
    private String fieldValue = null;
    private Map<String, Object> filter = new HashMap<String, Object>();

    private List<HistoryVo> histories = null;
    private List<HistoryVo> dealedHistoryVos= null;

    public WorkflowBean() {
        logger.debug("WorkflowBean");
    }

    @PostConstruct
    public void init() {
        logger.debug("WorkflowBean.init");
    }

    public void queryWorkItemsInRoster() {
        try {
            String condition = getConditionByFilter(filter);
            workItems = workflowService.queryWorkItemsInRoster(condition, null);
        } catch (BPMException e) {
            logger.error("", e);
        }
    }

    public void queryWorkItemsInMyInbox() {
        try {
            workItems = workflowService.queryWorkItemsInMyInbox(getConditionByFilter(filter), null);
        } catch (BPMException e) {
            logger.error("", e);
        }
    }

    public void queryDocManagerWorkItems() {
        try {
            workItems = workflowService.queryWorkItems("docManager", getConditionByFilter(filter), null);
        } catch (BPMException e) {
            logger.error("", e);
        }
    }

    public void getWorkItemDetail(String workflowNumber) {
        String[] fieldNames = {"F_Originator",};
        WorkItem item;
        try {
            histories = new ArrayList<HistoryVo>();
//            item = workflowService.getWorkItem(workflowNumber, fieldNames);
//            HistoryVo createHistoryVo = new HistoryVo();
//            createHistoryVo.setWorkflowName(item.getWorkflowName());
//            createHistoryVo.setF_WorkFlowNumber(item.getWorkObjectNumber());
//            createHistoryVo.setSubject(item.getSubject());
//            createHistoryVo.setQueueName("Inbox");
//            createHistoryVo.setStepName("创建人");
//            createHistoryVo.setUserName(item.getOriginator());
//            createHistoryVo.setF_TimeStamp(item.getLaunchDate());
//            histories.add(createHistoryVo);
            
            histories.addAll(workflowService.getWorkflowHistory(workflowNumber, null));
//            HistoryVo currentHistoryVo = new HistoryVo();
//            currentHistoryVo.setWorkflowName(item.getWorkflowName());
//            currentHistoryVo.setF_WorkFlowNumber(item.getWorkObjectNumber());
//            currentHistoryVo.setSubject(item.getSubject());
//            currentHistoryVo.setQueueName(item.getQueueName());
//            currentHistoryVo.setStepName(item.getStepName());
//            currentHistoryVo.setUserName(item.getParticipantName());
//            histories.add(currentHistoryVo);
        } catch (Exception e) {
            logger.error("", e);
        }
    }
    
    public void searchHistoryByFilter() {
        //my dealed Workflows
        String conntion = "F_BoundUserId =" +204 + " AND F_EventType =" +360;
        // my applay Workflows
//        String conntion = "F_Originator =" +204 + " AND F_EventType =" +360;
        dealedHistoryVos = workflowService.getWorkflowHistoryByFilter(conntion, null);
    }

    
    public String getConditionByFilter(Map<String, Object> filter) {
        String condition = "1=1";
        if (filter == null) { return condition; }
        for (String key : filter.keySet()) {
            String value = filter.get(key).toString();
            if (!"".equals(value)) {
                condition += " AND " + key + " = " + value;
            }
        }
        return condition;
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
    
    
    
    public List<WorkItem> getWorkItems() {
        return workItems;
    }

    public void setWorkItems(List<WorkItem> workItems) {
        this.workItems = workItems;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Map<String, Object> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, Object> filter) {
        this.filter = filter;
    }

    public List<HistoryVo> getHistories() {
        return histories;
    }

    public void setHistories(List<HistoryVo> histories) {
        this.histories = histories;
    }

    public List<HistoryVo> getDealedHistoryVos() {
        return dealedHistoryVos;
    }

    public void setDealedHistoryVos(List<HistoryVo> dealedHistoryVos) {
        this.dealedHistoryVos = dealedHistoryVos;
    }

    public static void main(String[] args) {

        Map<String, Object> filter = new HashMap<String, Object>();
        filter.put("F_Subject", "文档捡入流程");
        filter.put("F_WobNum", "00F0A77CAB5CA1408112D25FA1B395EE");

        WorkflowBean workflowBean = new WorkflowBean();
        String condition = workflowBean.getConditionByFilter(filter);
        logger.debug("condition:" + condition);
    }
}
