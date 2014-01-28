/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.schedulerapp.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.wcs.schedulerapp.common.JobInfo;
import com.wcs.schedulerapp.ejb.JobSessionBean;

/**
 * 
 * @author Christopher Lam
 */
@ManagedBean(name = "JobList")
@RequestScoped
public class JobList implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private JobSessionBean jobSessionBean;
	private List<JobInfo> jobList = null;

	/** Creates a new instance of JobList */
	public JobList() {
	}

	@PostConstruct
	public void initialize() {
		System.out.println("JobList - initialize() called!");
		jobList = jobSessionBean.getJobList();
	}

	/*
	 * Returns a list of active Jobs/Timers
	 */
	public List<JobInfo> getJobs() {
		System.out.println("JobList - getJobs() called!");
		if (jobList == null) {
			jobList = new ArrayList<JobInfo>();
		}
		return jobList;
	}
}
