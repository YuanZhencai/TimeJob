package com.wcs.jasper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wcs.connection.DBConnection;
import com.wcs.jasper.controller.vo.ReportVo;
import com.wcs.jasper.xml.model.Department;
import com.wcs.jasper.xml.model.Person;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;

public class JasperReportTest {

    /**
     * <p>Description: </p>
     * @param args
     */
    public static void main(String[] args) {
        
        String sourceFileName = "D:\\workspace\\TimeJob\\src\\main\\webapp\\report\\depart.jrxml";
        StringBuffer sbuffer = new StringBuffer();
        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            JasperReport compileReport = JasperCompileManager.compileReport(sourceFileName);
            List<Person> persons = null; 
            
            List<Department> departments = new ArrayList<Department>();
            persons = new ArrayList<Person>();
            Department department1 = new Department("开发部");
            persons.add(new Person("张三", "男", 22));
            persons.add(new Person("李四", "男", 24));
            persons.add(new Person("王五", "男", 25));
            department1.setPersons(persons);
            departments.add(department1);
            persons = new ArrayList<Person>();
            Department department2 = new Department("测试部");
            persons.add(new Person("小红", "女", 22));
            persons.add(new Person("小博", "男", 24));
            persons.add(new Person("小婷", "女", 25));
            persons.add(new Person("小花", "女", 25));
            department2.setPersons(persons);
            departments.add(department2);
            
            JRDataSource dataSource = new JRBeanCollectionDataSource(departments);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameters, dataSource);
            JRXhtmlExporter exporter = new JRXhtmlExporter();
            exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, sbuffer);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
            exporter.exportReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("sbuffer:" + sbuffer);
//        String sourceFileName = "D:\\workspace\\TimeJob\\src\\main\\webapp\\report\\emptyDSReport.jrxml";
//        StringBuffer sbuffer = new StringBuffer();
//        try {
//            Map<String, Object> parameters = new HashMap<String, Object>();
//            JasperReport compileReport = JasperCompileManager.compileReport(sourceFileName);
//            List<ReportVo> beans = new ArrayList<ReportVo>();
//            ReportVo rv1 = new ReportVo("1", "张三");
//            ReportVo rv2 = new ReportVo("2", "李四");
//            ReportVo rv3 = new ReportVo("3", "王五");
//            beans.add(rv1);
//            beans.add(rv2);
//            beans.add(rv3);
//            JRDataSource dataSource = new JRBeanCollectionDataSource(beans);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameters, dataSource);
//            JRXhtmlExporter exporter = new JRXhtmlExporter();
//            exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, sbuffer);
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
//            exporter.exportReport();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("sbuffer:" + sbuffer);
//        String sourceFileName = "D:\\workspace\\TimeJob\\src\\main\\webapp\\report\\emptyDSReport.jrxml";
//        StringBuffer sbuffer = new StringBuffer();
//        try {
//            Map<String, Object> parameters = new HashMap<String, Object>();
//            JasperReport compileReport = JasperCompileManager.compileReport(sourceFileName);
//            ReportVo[] beans = new ReportVo[3];
//            ReportVo rv1 = new ReportVo("1", "张三");
//            ReportVo rv2 = new ReportVo("2", "李四");
//            ReportVo rv3 = new ReportVo("3", "王五");
//            beans[0] = rv1;
//            beans[1] = rv2;
//            beans[2] = rv3;
//            JRDataSource dataSource = new JRBeanArrayDataSource(beans);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameters, dataSource);
//            JRXhtmlExporter exporter = new JRXhtmlExporter();
//            exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, sbuffer);
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
//            exporter.exportReport();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("sbuffer:" + sbuffer);
//        String sourceFileName = "D:\\workspace\\TimeJob\\src\\main\\webapp\\report\\emptyDSReport.jrxml";
//        StringBuffer sbuffer = new StringBuffer();
//        try {
//            Map<String, Object> parameters = new HashMap<String, Object>();
//            JasperReport compileReport = JasperCompileManager.compileReport(sourceFileName);
//            Map[] datas = new HashMap[3];
//            Map<String, Object> data1 = new HashMap<String, Object>();
//            data1.put("id", 1);
//            datas[0] = data1;
//            Map<String, Object> data2 = new HashMap<String, Object>();
//            data2.put("id", 2);
//            datas[1] = data2;
//            Map<String, Object> data3 = new HashMap<String, Object>();
//            data3.put("id", 3);
//            datas[2] = data3;
//            JRMapArrayDataSource dataSource = new JRMapArrayDataSource(datas);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameters, dataSource);
//            JRXhtmlExporter exporter = new JRXhtmlExporter();
//            exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, sbuffer);
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
//            exporter.exportReport();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("sbuffer:" + sbuffer);
        
//        String sourceFileName = "D:\\workspace\\TimeJob\\src\\main\\webapp\\report\\emptyDSReport.jrxml";
//        StringBuffer sbuffer = new StringBuffer();
//        try {
//            Map<String, Object> parameters = new HashMap<String, Object>();
//            parameters.put("id", "1");
//            JasperReport compileReport = JasperCompileManager.compileReport(sourceFileName);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameters, new JREmptyDataSource());
//            JRXhtmlExporter exporter = new JRXhtmlExporter();
//            exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, sbuffer);
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
//            exporter.exportReport();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("sbuffer:" + sbuffer);
//        

//        String driver = "com.ibm.db2.jcc.DB2Driver";
//        String url = "jdbc:db2://10.228.190.163:50000/Y_TIH";
//        String userName = "Yuan";
//        String passWord = "19900115";
//        Connection conn = null;
//        Statement st = null;
//        ResultSet rs = null;
//        String sql = "";
//        try {
//            Class.forName(driver).newInstance();
//
//            conn = DriverManager.getConnection(url, userName, passWord);

             
//            conn = DBConnection.getInstance().getConnection();
//            System.out.println("connect success");
//            st = conn.createStatement();
//
//            //st.execute("set current schema SKY");
//
//            sql = "SELECT * FROM P";
//            rs = st.executeQuery(sql);
//            while(rs.next()) {
//                System.out.println(rs.getString(1));
//            }

            
            
//            Map<String, Object> parameters = new HashMap<String, Object>();
//            parameters.put("SQLSTR", "SELECT * FROM P");
//            String sourceFileName = "D:\\workspace\\TimeJob\\src\\main\\webapp\\report\\sample.jasper";
//            String htmlFile = JasperRunManager.runReportToHtmlFile(sourceFileName, parameters, conn);
//            System.out.println("htmlFile:"  + htmlFile);
            
//            JasperPrint print = JasperFillManager.fillReport(sourceFileName, parameters, new JREmptyDataSource());
//            
//            JRExporter exporter = new JRPdfExporter();
//            
//            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
//            
//            exporter.exportReport();
            
//            conn.close();
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

}
