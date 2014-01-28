package com.wcs.jasper.xml.converter;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;

import org.apache.commons.betwixt.io.BeanWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Project: TimeJob</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2013 Wilmar Consultancy Services</p>
 * <p>All Rights Reserved.</p>
 * @author <a href="mailto:yuanzhencai@wcs-global.com">Yuan</a>
 */
public final class BeanToXMLConverter {
    private static Logger logger = LoggerFactory.getLogger(BeanToXMLConverter.class);
    
    private BeanToXMLConverter() {
    }
    
    public static String writeToXMLFile(Object obj, String fileName) {
        StringWriter outputWriter = new StringWriter();
        outputWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        BeanWriter beanWriter = new BeanWriter(outputWriter);
        //是否有根节点
        beanWriter.getXMLIntrospector().setWrapCollectionsInElement(false);
        //是否有ID
        beanWriter.setWriteIDs(false);
        beanWriter.enablePrettyPrint();
        String pathname = fileName + ".xml";
        File file = new File(pathname);
        try {
            beanWriter.write(obj);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(pathname);
            fileWriter.write(outputWriter.toString().toCharArray());
            fileWriter.flush();
            logger.debug(outputWriter.toString());
            outputWriter.close();
        } catch (Exception e) {
            logger.error("", e);
        }
        
        return file.getAbsolutePath();
    }

}
