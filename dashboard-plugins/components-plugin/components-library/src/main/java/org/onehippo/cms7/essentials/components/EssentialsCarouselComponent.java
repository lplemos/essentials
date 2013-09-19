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

package org.onehippo.cms7.essentials.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hippoecm.hst.content.beans.standard.HippoDocument;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.info.EssentialsCarouselComponentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

/**
 * HST component used for HST menus.
 *
 * @version "$Id$"
 */
@ParametersInfo(type = EssentialsCarouselComponentInfo.class)
public class EssentialsCarouselComponent extends CommonComponent {


    private static Logger log = LoggerFactory.getLogger(EssentialsCarouselComponent.class);

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) {
        final EssentialsCarouselComponentInfo componentInfo = getComponentParametersInfo(request);
        final List<HippoDocument> items = getCarouselItems(request, componentInfo);
        request.setAttribute(ATTRIBUTE_DOCUMENTS, items);
    }

    public List<HippoDocument> getCarouselItems(final HstRequest request, final EssentialsCarouselComponentInfo componentInfo) {
        final List<HippoDocument> beans = new ArrayList<>();
        addBeanForPath(request, componentInfo.getCarouselItem1(), beans);
        addBeanForPath(request, componentInfo.getCarouselItem2(), beans);
        addBeanForPath(request, componentInfo.getCarouselItem3(), beans);
        addBeanForPath(request, componentInfo.getCarouselItem4(), beans);
        addBeanForPath(request, componentInfo.getCarouselItem5(), beans);
        addBeanForPath(request, componentInfo.getCarouselItem6(), beans);
        addBeanForPath(request, componentInfo.getCarouselItem7(), beans);
        addBeanForPath(request, componentInfo.getCarouselItem8(), beans);
        addBeanForPath(request, componentInfo.getCarouselItem9(), beans);
        addBeanForPath(request, componentInfo.getCarouselItem10(), beans);
        return beans;
    }

    private void addBeanForPath(final HstRequest request, final String path, final Collection<HippoDocument> beans) {
        if (Strings.isNullOrEmpty(path)) {
            return;
        }

        log.debug("Fetching carousel item for path: [{}]", path);
        final HippoDocument bean = getHippoBeanForPath(path, HippoDocument.class, request);
        if (bean != null) {
            beans.add(bean);
        }
    }
}