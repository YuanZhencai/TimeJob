/** * DateUtil.java 
* Created on 2013-9-16 下午4:53:39 
*/

package com.wcs.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * <p>Project: mds</p> 
 * <p>Title: DateUtil.java</p> 
 * <p>Description: </p> 
 * <p>Copyright (c) 2013 Wilmar Consultancy Services</p>
 * <p>All Rights Reserved.</p>
 * @author <a href="mailto:guyanyu@wcs-global.com">Gu yanyu</a>
 */

public class DateUtil {
    
    public final static String FULL_DATE_NUM = "fullDateNum"; 
    public final static String NO_FULL_DATE_NUM = "noFullDateNum"; 

    
    /**
     * @param date
     * @param dayCount
     * @return
     */
    public static String calculateDate(Date date, int dayCount, String flag) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
        if(date != null && FULL_DATE_NUM.equals(flag)) {
            return formatter.format(new Date(date.getTime() + dayCount * 24 * 60 * 60 * 1000)).replaceAll("-", "");  
        }else if(date != null && NO_FULL_DATE_NUM.equals(flag)){
            return formatter.format(new Date(date.getTime() + dayCount * 24 * 60 * 60 * 1000));
        }else{
            return null;
        }
    }
    
    
    /**
     * @param date
     * @param formatterStr
     * @param flag
     * @return
     */
    public static String formatDate(Date date, String formatterStr, String flag) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatterStr);
        if(date != null && formatterStr != null && !"".equals(formatterStr) && FULL_DATE_NUM.equals(flag)) {
            return formatter.format(date).replaceAll("-", "");
        }if(date != null && formatterStr != null && !"".equals(formatterStr) && NO_FULL_DATE_NUM.equals(flag)){
            return formatter.format(date);
        }else {
            return null;
        }
    }
    
}
