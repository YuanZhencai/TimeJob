///*
// * JBoss, Home of Professional Open Source
// * Copyright 2010, Red Hat Middleware LLC, and individual contributors
// * by the @authors tag. See the copyright.txt in the distribution for a
// * full listing of individual contributors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// * http://www.apache.org/licenses/LICENSE-2.0
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.wcs.arquillian.ui.test;
//
//import java.net.URL;
//
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.drone.api.annotation.Drone;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.arquillian.test.api.ArquillianResource;
//import org.jboss.shrinkwrap.api.spec.WebArchive;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import com.thoughtworks.selenium.DefaultSelenium;
//
///**
// * Tests Arquillian Drone extension against CDI Login example.
// * 
// * Uses legacy Selenium driver bound to Firefox browser.
// * 
// * @author <a href="mailto:kpiwko@redhat.com">Karel Piwko</a>
// */
//@RunWith(Arquillian.class)
//public class LoginScreenDefaultSeleniumTestCase {
//    
//    @Deployment(testable = false)
//    public static WebArchive createDeployment() {
//        return Deployments.createLoginScreenDeployment();
//    }
//
//    private static final String USERNAME = "demo";
//    private static final String PASSWORD = "demo";
//
//    private static final String LOGGED_IN = "xpath=//li[contains(text(),'Welcome')]";
//    private static final String LOGGED_OUT = "xpath=//li[contains(text(),'Goodbye')]";
//
//    private static final String USERNAME_FIELD = "id=loginForm:username";
//    private static final String PASSWORD_FIELD = "id=loginForm:password";
//
//    private static final String LOGIN_BUTTON = "id=loginForm:login";
//    private static final String LOGOUT_BUTTON = "id=loginForm:logout";;
//
//    private static final String TIMEOUT = "15000";
//    
//    @Drone
//    DefaultSelenium driver;
//
//    @ArquillianResource
//    URL deploymentUrl;
//
//    @Test
//    public void testLoginAndLogout() {
//        Assert.assertNotNull("Deployment URL should not be null", deploymentUrl);
//
//        driver.open(deploymentUrl + "home.jsf");
//
//        driver.type(USERNAME_FIELD, USERNAME);
//        driver.type(PASSWORD_FIELD, PASSWORD);
//        driver.click(LOGIN_BUTTON);
//        driver.waitForPageToLoad(TIMEOUT);
//
//        Assert.assertTrue("User should be logged in!", driver.isElementPresent(LOGGED_IN));
//
//        driver.click(LOGOUT_BUTTON);
//        driver.waitForPageToLoad(TIMEOUT);
//        Assert.assertTrue("User should not be logged in!", driver.isElementPresent(LOGGED_OUT));
//    }
//}
