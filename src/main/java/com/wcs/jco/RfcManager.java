package com.wcs.jco;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.Environment;

public class RfcManager {
    private static Logger logger = LoggerFactory.getLogger(RfcManager.class);
    private static String ABAP_AS_POOLED = "cmdpms";

    private static JCOProvider provider = null;

    private static JCoDestination destination = null;
    static {
        Properties properties = loadProperties();

        provider = new JCOProvider();

        // catch IllegalStateException if an instance is already registered
        try {
            Environment.registerDestinationDataProvider(provider);
        } catch (IllegalStateException e) {
            logger.debug(e.getMessage(), e);
        }

        provider.changePropertiesForABAP_AS(ABAP_AS_POOLED, properties);
    }

    public static Properties loadProperties() {
        RfcManager manager = new RfcManager();
        Properties prop = new Properties();
        try {
            prop.load(manager.getClass().getResourceAsStream("/sap_conf.properties"));
        } catch (IOException e) {
            logger.debug(e.getMessage(), e);
        }
        return prop;
    }

    public static JCoDestination getDestination() throws JCoException {
        if (destination == null) {
            destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
        }
        return destination;
    }

    public static JCoFunction getFunction(String functionName) {
        JCoFunction function = null;
        try {
            function = getDestination().getRepository().getFunctionTemplate(functionName).getFunction();
        } catch (JCoException e) {
            logger.debug(e.getMessage(), e);
        } catch (NullPointerException e) {
            logger.debug(e.getMessage(), e);
        }
        return function;
    }

    public static void execute(JCoFunction function) {
        logger.debug("SAP Function Name : " + function.getName());
        JCoParameterList paramList = function.getImportParameterList();

        if (paramList != null) {
            logger.debug("Function Import Structure : " + paramList.toString());
        }

        try {
            function.execute(getDestination());
        } catch (JCoException e) {
            logger.debug(e.getMessage(), e);
        }
        paramList = function.getExportParameterList();

        if (paramList != null) {
            logger.debug("Function Export Structure : " + paramList.toString());
        }
    }

    public static String ping() {
        String msg = null;
        try {
            getDestination().ping();
            msg = "Destination " + ABAP_AS_POOLED + " is ok";
        } catch (JCoException ex) {
            logger.debug(msg, ex);
        }
        logger.debug(msg);
        return msg;
    }

    public void callRfcExample() {
        // 获取RFC 对象
        JCoFunction function = RfcManager.getFunction("ZRFC_MDS_DATA_HR");
        // 设置import 参数
        JCoParameterList importParam = function.getImportParameterList();
        // importParam.setValue("field_name", "val");

        // String yestertoday = DateUtil.calculateDate(new Date(), -60, DateUtil.FULL_DATE_NUM); // 请求当天
//        String today = DateUtil.formatDate(new Date(), "yyyy-MM-dd", DateUtil.FULL_DATE_NUM); // 请求当天
        importParam.setValue("G_BEGDA", "2013-10-10");
        importParam.setValue("G_ENDDA", "2013-10-18");
        // importParam.setValue("G_FLAG", "1");
        // 执行RFC
        RfcManager.execute(function);

        // 获取RFC返回的字段值
        JCoParameterList exportParam = function.getExportParameterList();
        // String exParamA = exportParam.getString("field_A");
        // String exParamB = exportParam.getString("field_B");
        // 遍历RFC返回的表对象
        JCoParameterList tableParameterList = function.getTableParameterList();
        for (JCoField jCoField : tableParameterList) {
            logger.debug(jCoField.getName() + ":" + jCoField.getTable().getRow());
        }

        JCoTable tb1 = function.getTableParameterList().getTable("TAB_ZHR_MDS001");
        JCoTable tb2 = function.getTableParameterList().getTable("TAB_ZHR_MDS002");
        JCoTable tb3 = function.getTableParameterList().getTable("TAB_ZHR_MDS003");
        logger.debug("[TAB_ZHR_MDS001]" + tb1.getRow());
        logger.debug("[TAB_ZHR_MDS002]" + tb2.getRow());
        logger.debug("[TAB_ZHR_MDS003]" + tb3.getRow());
        // for (int i = 0; i < tb.getNumRows(); i++) {
        // tb.setRow(i);
        // logger.debug(tb.getString("field01"));
        // logger.debug(tb.getString("field02"));
        // }
    }

    public static void main(String[] args) {
        new RfcManager().callRfcExample();
    }
}
