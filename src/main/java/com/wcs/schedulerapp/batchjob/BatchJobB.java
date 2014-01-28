package com.wcs.schedulerapp.batchjob;
///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.schedulerapp.batchjob;
//
//import com.schedulerapp.common.JobInfo;
//import java.util.Date;
//import java.util.logging.Logger;
//import javax.ejb.Asynchronous;
//import javax.ejb.Stateless;
//import javax.ejb.Timer;
//
///**
// *
// * @author Christopher Lam
// */
//@Stateless
//public class BatchJobB implements BatchJobInterface
//{
//    static Logger logger = Logger.getLogger("BatchJobB");
//
//    @Asynchronous
//    public void executeJob(Timer timer)
//    {
//        logger.info("Start of BatchJobB at " + new Date() + "...");
//        JobInfo jobInfo = (JobInfo) timer.getInfo();
//        try
//        {
//            logger.info("Running job: " + jobInfo);
//            System.out.println("~~~~~~~~~~~BBBBBBBBBBBBBBBBBB~~~~~~~~~~~");
////            Thread.sleep(30000);
//            Thread.sleep(6000);
//        }
//        catch (InterruptedException ex)
//        {
//        }
//        logger.info("End of BatchJobB at " + new Date());
//    }
//    // Add business logic below. (Right-click in editor and choose
//    // "Insert Code > Add Business Method")
//}
