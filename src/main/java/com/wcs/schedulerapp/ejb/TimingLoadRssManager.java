package com.wcs.schedulerapp.ejb;

import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * <p>Project: HelloWorld</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2013 Wilmar Consultancy Services</p>
 * <p>All Rights Reserved.</p>
 * @author <a href="mailto:yuanzhencai@wcs-global.com">Yuan</a>
 */
@Stateless
@Singleton
public class TimingLoadRssManager {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /* 每天每小时每分钟 */
    // @Schedule(minute = "*", dayOfWeek = "*", hour = "*")
    /* 每天8,13,16,20点执行 */
    @Schedule(dayOfWeek = "*", hour = "*", minute = "*")
    public void run() {
//        logger.info("begin TimingLoadRssManager.run()"+new Date());
//    	String source = "http://www.jsds.gov.cn/module/rss/rssfeed.jsp?colid=283";
//        getRssSource(source , "");
//    	logger.info("end TimingLoadRssManager.run()"+new Date());
//    	throw new RuntimeException();
    }
    
    @Schedule(dayOfWeek = "*", hour = "*", minute = "*", second = "15", persistent = true)
    public void sendTimeoutEmails() {
//        logger.info("TimingLoadRssManager.sendTimeoutEmails()");
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<SyndEntry> getRssSource(String source, String keywords) {
        logger.info("RssService.getRssSource() source:" + source);
        List<SyndEntry> seList = new ArrayList<SyndEntry>();
        try {
            URL feedUrl = new URL(source);
            //设置超时 
            URLConnection uc = (HttpURLConnection) feedUrl.openConnection(); 
            uc.setConnectTimeout(3000);
//            uc.setReadTimeout(3000);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(uc));
            List<SyndEntry> list = feed.getEntries();
            if (list.size() != 0) {
                if (keywords != null && !"".equals(keywords.trim())) {
                    // 过滤关键字
                    SyndEntry se = null;
                    for (int i = 0; i < list.size(); i++) {
                        se = list.get(i);
                        if (se.getTitle().contains(keywords.trim())) {
                            if(se.getPublishedDate()==null){
                                se.setPublishedDate(new Date());
                            }
                            seList.add(se);
                        }
                    }
                } else {
                    seList = list;
                }
            }
            logger.info("getRssSource success:" + seList.size());
        } catch (ConnectException ce) {
            logger.info("url:" + source);
            logger.error("连接超时", ce);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            logger.info("url:" + source);
        }
        return seList;
    }
    
    public static void main(String[] args) {
        TimingLoadRssManager tm = new TimingLoadRssManager();
        tm.getRssSource("http://221.226.40.111/module/rss/rssfeed.jsp?colid=283", "");
    }
}

