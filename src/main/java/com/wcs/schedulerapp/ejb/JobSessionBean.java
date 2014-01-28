/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.schedulerapp.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.DuplicateKeyException;
import javax.ejb.LocalBean;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.wcs.schedulerapp.batchjob.BatchJobInterface;
import com.wcs.schedulerapp.common.JobInfo;

/**
 * 
 * @author Christopher Lam
 */
// @Startup
// @Singleton
@Stateless
@LocalBean
public class JobSessionBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	TimerService timerService; // Resource Injection 注入定时服务
	static Logger logger = Logger.getLogger("JobSessionBean");

	// Add business logic below. (Right-click in editor and choose
	// "Insert Code > Add Business Method")

	@PostConstruct
	public void initialise() {
		System.out.println("###PostConstruct - calling TimerSessionBean...");

		// TODO�����������е�JOBΪ�գ������ݿ����JOB_INFO ʵ�壬����ӵ�Timer�У�
	}

	@PreDestroy
	public void cleanup() {
		System.out.println("###Predestroy - cleaning up...");
	}

	/*
	 * Callback method for the timers. 回调方法的定时器。
	 * <p>定时器方法的声明很简单，只需在方法上面加入@Timeout 注释，另外定时器方法 必须遵守如下格式：void XXX(Timer
	 * timer)在定时事件发生时，此方法将被执行.</p>
	 */
	@Timeout
	public void timeout(Timer timer) {
		System.out.println("###Timer <" + timer.getInfo() + "> timeout at "
				+ new Date());
		try {
			JobInfo jobInfo = (JobInfo) timer.getInfo();// 获取与定时器相关的信息。
			System.out.println("~~~~~~~~~@Timeout~~~~~~~~~");
			BatchJobInterface batchJob = (BatchJobInterface) InitialContext
					.doLookup(jobInfo.getJobClassName());
			System.out.println("~~~~~~~~~~~调用timeout方法~~~~~~~~~~~~~");
			batchJob.executeJob(timer); // Asynchronous method
			System.out.println("~~~~~~~~~~~调用timeout方法完成~~~~~~~~~~~~~");
		} catch (NamingException ex) {
			logger.log(Level.SEVERE, null, ex);
		} catch (Exception ex1) {
			logger.severe("Exception caught: " + ex1);
		}
	}

	/*
	 * Returns the Timer object based on the given JobInfo 返回计时器对象的基础上给予jobinfo
	 */
	private Timer getTimer(JobInfo jobInfo) {
		Collection<Timer> timers = timerService.getTimers();
		System.out.println("~~~~~~~~~1111111111111~~~~~~~~~~~" + timers.size());
		for (Timer t : timers)// 查看定时器是否存在
		{
			System.out.println(t.getInfo() + "~~~~~~~2222222222~~~~~~~~~"
					+ jobInfo);
			if (jobInfo.equals((JobInfo) t.getInfo())) {
				System.out.println(t.getSchedule()
						+ "~~~~~~~ttttttttttt~~~~~~~~~" + t.getNextTimeout());
				return t;
			}
		}
		return null;
	}

	/*
	 * Creates a timer based on the information in the JobInfo
	 * 创建一个计时器的信息为基础的jobinfo 创建基于日历的一个周期性的定时器
	 */
	public JobInfo createJob(JobInfo jobInfo) throws Exception {
		// Check for duplicates 重复检查
		System.out.println("~~~~~重复检查~~~~~~");
		if (getTimer(jobInfo) != null)// 检查定时器是否存在
		{
			throw new DuplicateKeyException("Job with the ID already exist!");
		}

		TimerConfig timerAConf = new TimerConfig(jobInfo, true);

		ScheduleExpression schedExp = new ScheduleExpression();
		schedExp.start(jobInfo.getStartDate());
		schedExp.end(jobInfo.getEndDate());
		schedExp.second(jobInfo.getSecond());
		schedExp.minute(jobInfo.getMinute());
		schedExp.hour(jobInfo.getHour());
		schedExp.dayOfMonth(jobInfo.getDayOfMonth());
		schedExp.month(jobInfo.getMonth());
		schedExp.year(jobInfo.getYear());
		schedExp.dayOfWeek(jobInfo.getDayOfWeek());
		logger.info("### Scheduler expr: " + schedExp.toString());
		System.out.println("~~~~~~~~~~timerAConf~~~~~~~~~~~~~~~~~~"
				+ timerAConf);
		Timer newTimer = timerService.createCalendarTimer(schedExp, timerAConf);// 一个基于日历和周期性的定时器表达式
		System.out.println(newTimer.getNextTimeout()
				+ "~~~~~~~~~~newTimer~~~~~~~~~~" + newTimer.getSchedule());
		JobInfo ji = (JobInfo) newTimer.getInfo();// 获取与定时器相关的信息。
		logger.info("New timer created: " + ji);
		// getNextTimeout()获取定时器下一次到期前的时间
		ji.setNextTimeout(newTimer.getNextTimeout());// 表示下一次定时器到期的时间。

		return ji;
	}

	/*
	 * Returns a list of JobInfo for the active timers 返回一个列表的jobinfo为活跃定时器
	 */
	public List<JobInfo> getJobList() {
		logger.info("getJobList() called!!!");
		ArrayList<JobInfo> jobList = new ArrayList<JobInfo>();
		Collection<Timer> timers = timerService.getTimers();
		for (Timer t : timers) {
			JobInfo jobInfo = (JobInfo) t.getInfo();
			jobInfo.setNextTimeout(t.getNextTimeout());
			jobList.add(jobInfo);
		}
		return jobList;
	}

	/*
	 * Returns the updated JobInfo from the timer 返回更新的jobinfo从定时器
	 */
	public JobInfo getJobInfo(JobInfo jobInfo) {
		Timer t = getTimer(jobInfo);
		if (t != null) {
			JobInfo j = (JobInfo) t.getInfo();
			j.setNextTimeout(t.getNextTimeout());
			return j;
		}
		return null;
	}

	/*
	 * Updates a timer with the given JobInfo 更新计时器与给定的jobinfo
	 */
	public JobInfo updateJob(JobInfo jobInfo) throws Exception {
		System.out.println("~~~~~~~~更新计时器~~~~~~~~");
		Timer t = getTimer(jobInfo);
		if (t != null) {
			logger.info("Removing timer: " + t.getInfo());
			t.cancel();// 此方法不会清除定时器的持久化数据，在服务器下次启动时定时器仍会启动。
			logger.info("Updating job with expr: " + jobInfo.getExpression());
			return createJob(jobInfo);
		}
		return null;
	}

	/*
	 * Remove a timer with the given JobInfo 删除定时器与给定的jobinfo
	 */
	public void deleteJob(JobInfo jobInfo) {
		Timer t = getTimer(jobInfo);
		if (t != null) {
			t.cancel();// 此方法不会清除定时器的持久化数据，在服务器下次启动时定时器仍会启动。
		}
	}
}
