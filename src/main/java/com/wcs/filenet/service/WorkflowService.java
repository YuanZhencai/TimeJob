package com.wcs.filenet.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ibm.filenet.helper.pe.BPMException;
import com.ibm.filenet.helper.pe.BPMWorkflowServices;
import com.ibm.filenet.helper.vo.HistoryVo;
import com.ibm.filenet.helper.vo.WorkItem;
import com.wcs.connection.PEConnection;

import filenet.vw.api.VWException;
import filenet.vw.api.VWSession;
import filenet.vw.api.VWStepHistory;
import filenet.vw.api.VWStepOccurrenceHistory;
import filenet.vw.api.VWWorkObject;
import filenet.vw.api.VWWorkflowHistory;

/**
 * <p>
 * Project: TimeJob
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright (c) 2013 Wilmar Consultancy Services
 * </p>
 * <p>
 * All Rights Reserved.
 * </p>
 * 
 * @author <a href="mailto:yuanzhencai@wcs-global.com">Yuan</a>
 */
@Stateless
public class WorkflowService implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(WorkflowService.class);

	private BPMWorkflowServices workflowService = null;

	public WorkflowService() {
		logger.debug("WorkflowService");
	}

	@PostConstruct
	public void init() {
		logger.debug("WorkflowService.init");
		// try {
		// VWSession session = PEConnection.getInstance().getSession("liqing1",
		// "tihfilenet");
		// workflowService = new BPMWorkflowServices(session);
		// } catch (VWException e) {
		// logger.error("", e);
		// }
	}

	public boolean assignWorkItem(String workObjectNumber, String assignUserID) {
		boolean assignWorkItem = false;
		try {
			VWSession session = PEConnection.getInstance().getSession(
					"liqing1", "tihfilenet");
			workflowService = new BPMWorkflowServices(session);
			assignWorkItem = workflowService.assignWorkItem(workObjectNumber,
					assignUserID);
		} catch (VWException e) {
			logger.error("", e);
		} catch (BPMException e) {
			logger.error("", e);
		} finally {
			workflowService.destroyBPMWorkflowServices();
		}
		return assignWorkItem;
	}

	public boolean completeAllWorkItems(List<String> queueNames,
			String workflowNumber) {
		boolean completeAllWorkItems = false;
		try {
			VWSession session = PEConnection.getInstance().getSession(
					"liqing1", "tihfilenet");
			workflowService = new BPMWorkflowServices(session);
			completeAllWorkItems = workflowService.completeAllWorkItems(
					queueNames, workflowNumber);
		} catch (VWException e) {
			logger.error("", e);
		} catch (BPMException e) {
			logger.error("", e);
		} finally {
			workflowService.destroyBPMWorkflowServices();
		}
		return completeAllWorkItems;
	}

	public boolean completeWorkItem(String workObjectNumber,
			Map<String, Object> dataMap) {
		boolean completeWorkItem = false;
		try {
			VWSession session = PEConnection.getInstance().getSession(
					"liqing1", "tihfilenet");
			workflowService = new BPMWorkflowServices(session);
			completeWorkItem = workflowService.completeWorkItem(
					workObjectNumber, dataMap);
		} catch (VWException e) {
			logger.error("", e);
		} catch (BPMException e) {
			logger.error("", e);
		} finally {
			workflowService.destroyBPMWorkflowServices();
		}
		return completeWorkItem;
	}

	public int convertUserNameToId(String userName) {
		int convertUserNameToId = 0;
		try {
			VWSession session = PEConnection.getInstance().getSession(
					"liqing1", "tihfilenet");
			workflowService = new BPMWorkflowServices(session);
			convertUserNameToId = workflowService.convertUserNameToId(userName);
		} catch (VWException e) {
			logger.error("", e);
		} catch (BPMException e) {
			logger.error("", e);
		} finally {
			workflowService.destroyBPMWorkflowServices();
		}
		return convertUserNameToId;
	}

	public VWWorkflowHistory getProcessInfo(String workObjectNumber) {
		VWWorkflowHistory processInfo = null;
		try {
			VWSession session = PEConnection.getInstance().getSession(
					"liqing1", "tihfilenet");
			workflowService = new BPMWorkflowServices(session);
			processInfo = workflowService.getProcessInfo(workObjectNumber);
		} catch (VWException e) {
			logger.error("", e);
		} catch (BPMException e) {
			logger.error("", e);
		} finally {
			workflowService.destroyBPMWorkflowServices();
		}
		return processInfo;
	}

	public List<HistoryVo> getWorkflowHistory(String workflowNumber,
			String[] fieldNames) {
		List<HistoryVo> historyVos = new ArrayList<HistoryVo>();
		try {
			VWSession session = PEConnection.getInstance().getSession(
					"liqing1", "tihfilenet");
			workflowService = new BPMWorkflowServices(session);
			historyVos = workflowService.getWorkflowHistory(workflowNumber,
					fieldNames);
		} catch (VWException e) {
			logger.error("", e);
		} finally {
			workflowService.destroyBPMWorkflowServices();
		}
		return historyVos;
	}

	public List<HistoryVo> getWorkflowHistoryByFilter(String filter,
			String[] fieldNames) {
		List<HistoryVo> historyVos = new ArrayList<HistoryVo>();
		try {
			VWSession session = PEConnection.getInstance().getSession(
					"liqing1", "tihfilenet");
			workflowService = new BPMWorkflowServices(session);
			historyVos = workflowService.getWorkflowHistoryByFilter(filter,
					fieldNames);
		} catch (VWException e) {
			logger.error("", e);
		} finally {
			workflowService.destroyBPMWorkflowServices();
		}
		return historyVos;
	}

	public WorkItem getWorkItem(String workObjectNumber, String[] fieldNames)
			throws BPMException {
		WorkItem workItem = new WorkItem();
		try {
			VWSession session = PEConnection.getInstance().getSession(
					"liqing1", "tihfilenet");
			workflowService = new BPMWorkflowServices(session);
			workItem = workflowService
					.getWorkItem(workObjectNumber, fieldNames);
		} catch (VWException e) {
			logger.error("", e);
		} finally {
			workflowService.destroyBPMWorkflowServices();
		}
		return workItem;
	}

	public VWWorkObject openWorkItem(String workObjectNumber)
			throws BPMException {
		VWWorkObject openWorkItem = null;
		try {
			VWSession session = PEConnection.getInstance().getSession(
					"liqing1", "tihfilenet");
			workflowService = new BPMWorkflowServices(session);
			openWorkItem = workflowService.openWorkItem(workObjectNumber);
		} catch (VWException e) {
			logger.error("", e);
		} finally {
			workflowService.destroyBPMWorkflowServices();
		}
		return openWorkItem;
	}

	public List<WorkItem> queryWorkItems(String queueName, String condition,
			String[] fieldNames) throws BPMException {
		List<WorkItem> queryWorkItems = new ArrayList<WorkItem>();
		try {
			VWSession session = PEConnection.getInstance().getSession(
					"liqing1", "tihfilenet");
			workflowService = new BPMWorkflowServices(session);
			queryWorkItems = workflowService.queryWorkItems(queueName,
					condition, fieldNames);
		} catch (VWException e) {
			logger.error("", e);
		} finally {
			workflowService.destroyBPMWorkflowServices();
		}
		return queryWorkItems;
	}

	public List<WorkItem> queryWorkItemsInMyInbox(String condition,
			String[] fieldNames) throws BPMException {
		List<WorkItem> queryWorkItemsInMyInbox = new ArrayList<WorkItem>();
		try {
			VWSession session = PEConnection.getInstance().getSession(
					"liqing1", "tihfilenet");
			workflowService = new BPMWorkflowServices(session);
			queryWorkItemsInMyInbox = workflowService.queryWorkItemsInMyInbox(
					condition, fieldNames);
		} catch (VWException e) {
			logger.error("", e);
		} finally {
			workflowService.destroyBPMWorkflowServices();
		}
		return queryWorkItemsInMyInbox;
	}

	public List<WorkItem> queryWorkItemsInRoster(String condition,
			String[] fieldNames) throws BPMException {
		List<WorkItem> queryWorkItemsInRoster = new ArrayList<WorkItem>();
		try {
			VWSession session = PEConnection.getInstance().getSession(
					"liqing1", "tihfilenet");
			workflowService = new BPMWorkflowServices(session);
			queryWorkItemsInRoster = workflowService.queryWorkItemsInRoster(
					condition, fieldNames);
		} catch (VWException e) {
			logger.error("", e);
		} finally {
			workflowService.destroyBPMWorkflowServices();
		}
		return queryWorkItemsInRoster;
	}

	public boolean saveWorkItem(String workObjectNumber,
			Map<String, Object> dataMap) throws BPMException {
		boolean saveWorkItem = false;
		try {
			VWSession session = PEConnection.getInstance().getSession(
					"liqing1", "tihfilenet");
			workflowService = new BPMWorkflowServices(session);
			saveWorkItem = workflowService.saveWorkItem(workObjectNumber,
					dataMap);
		} catch (VWException e) {
			logger.error("", e);
		} finally {
			workflowService.destroyBPMWorkflowServices();
		}
		return saveWorkItem;
	}

	public boolean startProcess(String workflowName, Map<String, Object> dataMap)
			throws BPMException {
		boolean startProcess = false;
		try {
			VWSession session = PEConnection.getInstance().getSession(
					"liqing1", "tihfilenet");
			workflowService = new BPMWorkflowServices(session);
			startProcess = workflowService.startProcess(workflowName, dataMap);
		} catch (VWException e) {
			logger.error("", e);
		} finally {
			workflowService.destroyBPMWorkflowServices();
		}
		return startProcess;
	}

	public boolean terminalWorkItem(String workObjectNumber)
			throws BPMException {
		boolean terminalWorkItem = false;
		try {
			VWSession session = PEConnection.getInstance().getSession(
					"liqing1", "tihfilenet");
			workflowService = new BPMWorkflowServices(session);
			terminalWorkItem = workflowService
					.terminalWorkItem(workObjectNumber);
		} catch (VWException e) {
			logger.error("", e);
		} finally {
			workflowService.destroyBPMWorkflowServices();
		}
		return terminalWorkItem;
	}

	public boolean updateWorkItems(String condition, Map<String, Object> dataMap) {
		boolean updateWorkItems = false;
		try {
			VWSession session = PEConnection.getInstance().getSession(
					"liqing1", "tihfilenet");
			workflowService = new BPMWorkflowServices(session);
			updateWorkItems = workflowService.updateWorkItems(condition,
					dataMap);
		} catch (VWException e) {
			logger.error("", e);
		} finally {
			workflowService.destroyBPMWorkflowServices();
		}
		return updateWorkItems;
	}

}
