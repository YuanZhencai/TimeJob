package com.wcs.jasper.xml;



import java.util.ArrayList;
import java.util.List;

import com.wcs.jasper.xml.converter.BeanToXMLConverter;
import com.wcs.jasper.xml.model.DataList;
import com.wcs.jasper.xml.model.Department;
import com.wcs.jasper.xml.model.NameList;
import com.wcs.jasper.xml.model.Person;

/**
 * <p>Project: TimeJob</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2013 Wilmar Consultancy Services</p>
 * <p>All Rights Reserved.</p>
 * @author <a href="mailto:yuanzhencai@wcs-global.com">Yuan</a>
 */
public class BeanToXmlTest {

    /**
     * <p>Description: </p>
     * @param args
     */
    public static void main(String[] args) {

        
        List<Person> ps1 = new ArrayList<Person>();
        Person p1 = new Person();
        p1.setName("张三");
        p1.setGender("男");
        p1.setAge(35);
        ps1.add(p1);
        Department d1 = new Department();
        d1.setName("wcs");
        d1.setPersons(ps1);
        
        List<Person> ps2 = new ArrayList<Person>();
        Person p2 = new Person();
        p2.setName("李四");
        p2.setGender("男");
        p2.setAge(20);
        
        Person p3 = new Person();
        p3.setName("王五");
        p3.setGender("男");
        p3.setAge(25);
        ps2.add(p2);
        ps2.add(p3);
        
        Department d2 = new Department();
        d2.setName("btc");
        d2.setPersons(ps2);
        
        List<Department> departments = new ArrayList<Department>();
        departments.add(d1);
        departments.add(d2);
        
        DataList dataList = new DataList();
        dataList.setDataList(departments);
        
        String writeToXMLFile = BeanToXMLConverter.writeToXMLFile(dataList, "departments");
        
        System.out.println(writeToXMLFile);
        
    }

}
