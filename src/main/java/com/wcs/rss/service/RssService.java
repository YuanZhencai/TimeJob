package com.wcs.rss.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.wcs.filenet.service.WorkflowService;

public class RssService implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(WorkflowService.class);

    public List<SyndEntry> getRssSource(String source) {
        logger.info("RssService.getRssSource() source:" + source);
        List<SyndEntry> seList = new ArrayList<SyndEntry>();
        try {
            URL feedUrl = new URL(source);
            // 设置超时
            URLConnection uc = (HttpURLConnection) feedUrl.openConnection();
//            uc.setConnectTimeout(3000);
//            uc.setReadTimeout(3000);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(uc));
            seList = feed.getEntries();
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

    public String getFile() throws Exception {

        FileInputStream inputStream = new FileInputStream("D:\\workspace\\TimeJob\\src\\main\\webapp\\NewFile.html");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder html = new StringBuilder();

        String line = null;
        while ((line = reader.readLine()) != null) {
            html.append(line + "\n");
        }
        inputStream.close();
        return html.toString();
    }

    public static void main(String[] args) {
        RssService rssService = new RssService();
        try {
            String source = "";
            StringBuffer descriptionValue = new StringBuffer();
            List<SyndEntry> rssSource = rssService.getRssSource(source);
            for (SyndEntry se : rssSource) {
                SyndContent description = se.getDescription();
                String value = description.getValue();
                descriptionValue.append(value);
            }
            String html = rssService.getFile();
            String replace = html.replace("content", descriptionValue.toString());
            FileOutputStream os = new FileOutputStream("D:\\workspace\\TimeJob\\src\\main\\webapp\\New.html");
            os.write(replace.getBytes("UTF-8"));
            os.close();
            logger.info("replace:" + replace);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
