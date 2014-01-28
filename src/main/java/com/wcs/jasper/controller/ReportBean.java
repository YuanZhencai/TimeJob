package com.wcs.jasper.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wcs.jasper.service.JasperService;


@ManagedBean(name = "reportBean")
@ViewScoped
public class ReportBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Inject
    private JasperService jasperService;
    
    private String reportContent;
    
    private static final  String TITLE = "JasperReport";

    
    public ReportBean() {
        logger.debug("ReportBean.ReportBean()");
    }
    
    @PostConstruct
    public void init() {
        logger.debug("ReportBean.init()");
    }
    
    public void searchPersonReport() {
        reportContent = jasperService.personReport().toString();
    }
    
    public void searchWfDetailsReport() {
        reportContent = jasperService.wfDetailsReport().toString();
    }
    
    public void searchJavaBeanReport() {
        reportContent = jasperService.javaBeanReport().toString();
    }
    
    public void searchDepartmentReport() {
        reportContent = jasperService.departmentReport().toString();
    }
    
    public void exportReport() {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
                .getResponse();
        String fileName = "report.html";
        try {
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            if(reportContent != null){
                servletOutputStream.write(reportContent.getBytes("utf-8"));
            }
            servletOutputStream.flush();
            servletOutputStream.close();
            servletOutputStream = null;
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    
    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public String getTitle() {
        return TITLE;
    }

    public static void main(String[] args) {
        String a = "ssssssssss[/img]";
        
        int indexOf = a.indexOf("[/img]");
        String substring = a.substring(0,indexOf);
        System.out.println("substring:" + substring);
    }
}
