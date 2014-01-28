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
//public class BatchJobC implements BatchJobInterface
//{
//    static Logger logger = Logger.getLogger("BatchJobC");
//
//    @Asynchronous
//    public void executeJob(Timer timer)
//    {
//        logger.info("Start of BatchJobC at " + new Date() + "...");
//        JobInfo jobInfo = (JobInfo) timer.getInfo();//获取与定时器相关的信息。
//        try
//        {
//            logger.info("Running job: " + jobInfo);
//            System.out.println("~~~~~~~~~~~CCCCCCCCCCCCCCC~~~~~~~~~~~");
////            Thread.sleep(30000);
//            Thread.sleep(6000);
//        }
//        catch (InterruptedException ex)
//        {
//        }
//        logger.info("End of BatchJobC at " + new Date());
//    }
//    // Add business logic below. (Right-click in editor and choose
//    // "Insert Code > Add Business Method")
//}
