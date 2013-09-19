/*
 * Copyright 2013 Hippo B.V. (http://www.onehippo.com)
 */

package org.onehippo.cms7.essentials.components;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.info.EssentialsDocumentListComponentInfo;
import org.onehippo.cms7.essentials.components.paging.IterablePagination;
import org.onehippo.cms7.essentials.components.paging.Pageable;
import org.onehippo.cms7.essentials.components.utils.query.HstQueryBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;

/**
 * HST component used for listing of documents.
 *
 * @version "$Id$"
 */
@ParametersInfo(type = EssentialsDocumentListComponentInfo.class)
public class EssentialsListComponent extends CommonComponent {

    private static Logger log = LoggerFactory.getLogger(EssentialsListComponent.class);

    /**
     * Request parameter to set the current page.
     */
    protected static final String REQUEST_PARAM_PAGE = "page";

    /**
     * Request attribute to store pageable result in.
     */
    protected static final String REQUEST_ATTR_PAGEABLE = "pageable";

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) {
        final EssentialsDocumentListComponentInfo paramInfo = getComponentParametersInfo(request);
        final String path = getScopePath(paramInfo);
        log.debug("Calling EssentialsListComponent for documents path:  [{}]", path);
        final HippoBean scope = getScopeBean(request, path);
        if (scope == null) {
            log.warn("Search scope was null");
            handleInvalidScope(request, response);
            return;
        }

        final Pageable<HippoBean> pageable = doSearch(request, paramInfo, scope);
        request.setAttribute(REQUEST_ATTR_PAGEABLE, pageable);
    }

    protected Pageable<HippoBean> doSearch(final HstRequest request, final EssentialsDocumentListComponentInfo paramInfo, final HippoBean scope) {
        try {
            final HstQuery build = buildQuery(request, paramInfo, scope);
            if (build != null) {
                return executeQuery(request, paramInfo, build);
            }
        } catch (QueryException e) {
            log.error("Error running query", e.getMessage());
            log.debug("Query exception: ", e);
        }
        return null;
    }

    private void handleInvalidScope(final HstRequest request, final HstResponse response) {
        // TODO determine what to do with invalid scope
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        if (log.isDebugEnabled()) {
            throw new HstComponentException("EssentialsListComponent needs a valid scope to display documents");
        }
    }

    /**
     * Build the HST query for the list.
     *
     * @param request the current request
     * @param paramInfo the parameter info
     * @param scope the scope of the query
     * @return the HST query to execute
     */
    protected HstQuery buildQuery(final HstRequest request, final EssentialsDocumentListComponentInfo paramInfo, final HippoBean scope) {
        final HstQueryBuilder builder = new HstQueryBuilder(this, request);
        final String documentTypes = paramInfo.getDocumentTypes();
        final String[] types = parseDocumentTypes(documentTypes);
        return builder.scope(scope).documents(types).includeSubtypes().build();
    }

    /**
     * Execute the list query.
     *
     * @param request the current request
     * @param paramInfo the parameter info
     * @param query the query to execute
     * @return the pageable result
     * @throws QueryException query exception when query fails
     */
    protected Pageable<HippoBean> executeQuery(final HstRequest request, final EssentialsDocumentListComponentInfo paramInfo, final HstQuery query) throws QueryException {
        final HstQueryResult execute = query.execute();
        final Pageable<HippoBean> pageable = new IterablePagination<>(
                execute.getHippoBeans(),
                execute.getTotalSize(),
                getPageSize(request, paramInfo),
                getCurrentPage(request));
        pageable.setShowPagination(isShowPagination(request, paramInfo));
        return pageable;
    }


    /**
     * Determine the current page of the list query.
     *
     * @param request the current request
     * @return the current page of the query
     */
    protected int getCurrentPage(final HstRequest request) {
        return getIntParameter(request, REQUEST_PARAM_PAGE, 1);
    }

    /**
     * Determine the page size of the list query.
     *
     * @param request the current request
     * @param paramInfo the settings of the component
     * @return the page size of the query
     */
    protected int getPageSize(final HstRequest request, final EssentialsDocumentListComponentInfo paramInfo) {
        return paramInfo.getPageSize();
    }

    /**
     * Determine the path to use for the scope of the query. Returns null
     * when no path is defined.
     *
     * @param paramInfo the settings of the component
     * @return the scope of the query
     */
    protected String getScopePath(final EssentialsDocumentListComponentInfo paramInfo) {
        return paramInfo.getPath();
    }

    /**
     * Determine whether pagination should be shown.
     *
     * @param request the current request
     * @param paramInfo the settings of the component
     * @return
     */
    protected boolean isShowPagination(final HstRequest request, final EssentialsDocumentListComponentInfo paramInfo) {
        final Boolean showPagination = paramInfo.getShowPagination();
        if(showPagination == null) {
            log.debug("Show pagination not configured, use default value 'true'");
            return true;
        }
        return showPagination.booleanValue();
    }

    /**
     * For given string, comma separate it and convert to array
     *
     * @param documentTypes comma separated document types
     * @return empty array if empty
     */
    private String[] parseDocumentTypes(final String documentTypes) {
        if (Strings.isNullOrEmpty(documentTypes)) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final Iterable<String> iterable = Splitter.on(",").trimResults().omitEmptyStrings().split(documentTypes);
        return Iterables.toArray(iterable, String.class);
    }

}