package com.wcs.jasper.service;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wcs.base.service.EntityService;
import com.wcs.base.util.JSFUtils;
import com.wcs.connection.DBConnection;
import com.wcs.jasper.controller.vo.ReportVo;
import com.wcs.jasper.xml.model.Department;
import com.wcs.jasper.xml.model.Person;

/**
 * <p>
 * Project: TimeJob
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright (c) 2013 Wilmar Consultancy Services
 * </p>
 * <p>
 * All Rights Reserved.
 * </p>
 * 
 * @author <a href="mailto:yuanzhencai@wcs-global.com">Yuan</a>
 */
@Stateless
public class JasperService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@EJB
	private EntityService entityService;

	public JasperService() {
		logger.debug("JasperService.JasperService()");
	}

	public StringBuffer personReport() {
		String sourceFileName = "sample.jrxml";
		String sql = "SELECT * FROM P FETCH FIRST 10 rows only";
		return createReport(sourceFileName, sql);
	}

	public StringBuffer wfDetailsReport() {
		String sourceFileName = "report2.jrxml";
		String sql = "SELECT * FROM WF_INSTANCEMSTR wf FETCH FIRST 10 ROWS ONLY";
		return createReport(sourceFileName, sql);
	}

	public StringBuffer javaBeanReport() {
		String sourceFileName = "emptyDSReport.jrxml";
		List<ReportVo> beans = new ArrayList<ReportVo>();
		ReportVo rv1 = new ReportVo("1", "张三");
		ReportVo rv2 = new ReportVo("2", "李四");
		ReportVo rv3 = new ReportVo("3", "王五");
		beans.add(rv1);
		beans.add(rv2);
		beans.add(rv3);
		JRDataSource dataSource = new JRBeanCollectionDataSource(beans);
		Map<String, Object> parameters = new HashMap<String, Object>();
		return createReport(sourceFileName, dataSource, parameters);
	}

	public StringBuffer departmentReport() {
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

		return createReport("depart.jrxml", dataSource, null);
	}

	private StringBuffer createReport(String template, JRDataSource dataSource,
			Map<String, Object> parameters) {
		StringBuffer sbuffer = new StringBuffer();
		String realPath = JSFUtils.getRealPath();
		String templatePath = realPath + "report" + File.separator + template;
		logger.debug("templatePath:" + templatePath);
		try {
			JasperReport compileReport = JasperCompileManager
					.compileReport(templatePath);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					compileReport, parameters, dataSource);
			JRXhtmlExporter exporter = new JRXhtmlExporter();
			exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER,
					sbuffer);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING,
					"UTF-8");
			exporter.exportReport();
		} catch (Exception e) {
			logger.error("", e);
		}
		return sbuffer;
	}

	private StringBuffer createReport(String template, String sql) {
		String realPath = JSFUtils.getRealPath();
		String templatePath = realPath + "report" + File.separator + template;
		logger.debug("templatePath:" + templatePath);
		Connection connection = entityService.getJdbcConnection();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("SQLSTR", sql);
		StringBuffer sbuffer = new StringBuffer();
		try {
			JasperReport compileReport = JasperCompileManager
					.compileReport(templatePath);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					compileReport, parameters, connection);
			JRXhtmlExporter exporter = new JRXhtmlExporter();
			exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER,
					sbuffer);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING,
					"UTF-8");
			exporter.exportReport();
			connection.close();
		} catch (Exception e) {
			logger.error("", e);
		}
		return sbuffer;
	}

	public static void main(String[] args) {
		JasperService jasperService = new JasperService();
		for (int i = 0; i < 5; i++) {
			StringBuffer report = jasperService.personReport();
			System.out.println("report: " + report.toString());
		}
	}
}
