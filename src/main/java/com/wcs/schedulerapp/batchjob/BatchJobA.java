/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.schedulerapp.batchjob;

import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.Timer;

import com.wcs.schedulerapp.common.JobInfo;
/**
 *
 * @author Christopher Lam
 */
@Stateless
public class BatchJobA implements BatchJobInterface 
{
    static Logger logger = Logger.getLogger("BatchJobA");

    public void executeJob(Timer timer)
    {
        logger.info("Start of BatchJobA at " + new Date() + "...");
        JobInfo jobInfo = (JobInfo) timer.getInfo();
        try
        {
            logger.info("Running job: " + jobInfo);
            System.out.println("~~~~~~~~~~AAAAAAAAAAAAAAAAAAAA~~~~~~~~~~~~~~~~~~");
//            Thread.sleep(30000);
            Thread.sleep(6000);
        }
        catch (InterruptedException ex)
        {
        }
        logger.info("End of BatchJobA at " + new Date());
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
