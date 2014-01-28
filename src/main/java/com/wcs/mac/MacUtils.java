package com.wcs.mac;

import java.io.IOException;

import org.apache.log4j.Logger;

public class MacUtils {
    private static Logger logger = Logger.getLogger(MacUtils.class);

    public static String getMacByIp(String ip) {
        String content = null;
        try {
            logger.debug("[ip] " + ip);
            Process process = Runtime.getRuntime().exec("nbtstat -a " + ip);
            content = InputStreamUtils.InputStreamTOString(process.getInputStream(), "GBK");
            logger.debug("[content] " + content);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return content;

    }

    /**
     * <p>
     * Description:
     * </p>
     * 
     * @param args
     */
    public static void main(String[] args) {

        MacUtils.getMacByIp("10.228.190.163");
    }

}
