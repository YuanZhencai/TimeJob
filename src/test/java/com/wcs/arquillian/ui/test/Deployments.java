/*
 * JBoss, Home of Professional Open Source Copyright 2010, Red Hat Middleware LLC, and individual contributors by the @authors
 * tag. See the copyright.txt in the distribution for a full listing of individual contributors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wcs.arquillian.ui.test;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import com.wcs.arquillian.ui.Credentials;
import com.wcs.arquillian.ui.LoggedIn;
import com.wcs.arquillian.ui.Login;
import com.wcs.arquillian.ui.User;
import com.wcs.arquillian.ui.UsersProducer;

/**
 * This class shares deployment method for all available tests.
 * 
 * @author <a href="mailto:kpiwko@redhat.com">Karel Piwko</a>
 */
public class Deployments {
    public static final String ARCHIVE_NAME = "cdi-login.war";
    public static final String BUILD_DIRECTORY = "target";
    public static final String WEBAPP_SRC = "src/main/webapp";

    /**
     * Creates a WAR of a CDI-based application using ShrinkWrap
     * 
     * @return WebArchive to be tested
     */
    public static WebArchive createLoginScreenDeployment() {

        return ShrinkWrap
                .create(WebArchive.class, ARCHIVE_NAME)
                .addClasses(Credentials.class, LoggedIn.class, Login.class, User.class, UsersProducer.class)
                .addAsResource("import.sql")
                // .addAsManifestResource("test-persistence.xml", "persistence.xml")
                .addAsResource("test-jpa-persistence.xml", "META-INF/persistence.xml")
                .addAsWebResource(new File(WEBAPP_SRC, "index.html")).addAsWebResource(new File(WEBAPP_SRC, "home.xhtml"))
                .addAsWebResource(new File(WEBAPP_SRC, "users.xhtml")).addAsWebResource(new File(WEBAPP_SRC, "template.xhtml"))
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/faces-config.xml"))
                .setWebXML(new File(WEBAPP_SRC, "WEB-INF/web.xml"));
    }
}
