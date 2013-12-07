/*
 * Copyright 2013 Hippo B.V. (http://www.onehippo.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onehippo.cms7.essentials.rest.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.ws.rs.core.Application;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet;
import org.apache.cxf.jaxrs.utils.ResourceUtils;
import org.apache.cxf.message.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;

/**
 * @version "$Id$"
 */
public class RestServlet extends CXFNonSpringJaxrsServlet {

    private static final String IGNORE_APP_PATH_PARAM = "jaxrs.application.address.ignore";
    public static final String ATTRIBUTE_INJECTOR = "GuiceCXF#Injector";
    private static final long serialVersionUID = 1L;
    @Override
    protected void createServerFromApplication(String cName, final ServletConfig servletConfig, final String splitChar) throws ServletException {
        final Injector injector = (Injector) servletConfig.getServletContext().getAttribute(ATTRIBUTE_INJECTOR);
        final JAXRSServerFactoryBean bean = injector.getInstance(JAXRSServerFactoryBean.class);
        setAllInterceptors(bean, servletConfig, splitChar);
        setInvoker(bean, servletConfig);
        setExtensions(bean, servletConfig);
        setDocLocation(bean, servletConfig);
        setSchemasLocations(bean, servletConfig);

        bean.setBus(getBus());
        bean.create();
    }

    private String getClassNameAndProperties(String cName, Map<String, List<String>> props) {
        String theName = cName.trim();
        int ind = theName.indexOf('(');
        if (ind != -1 && theName.endsWith(")")) {
            props.putAll(parseMapListSequence(theName.substring(ind + 1, theName.length() - 1)));
            theName = theName.substring(0, ind).trim();
        }
        return theName;
    }



}