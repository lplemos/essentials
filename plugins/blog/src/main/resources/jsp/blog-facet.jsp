<%@ include file="/WEB-INF/jsp/include/imports.jsp" %>
<%--
  Copyright 2014 Hippo B.V. (http://www.onehippo.com)

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  --%>

<%--@elvariable id="facets" type="org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean"--%>
<%--@elvariable id="facetLimit" type="java.lang.Integer"--%>
<%--@elvariable id="query" type="java.lang.String"--%>
<hst:setBundle basename="essentials.facets"/>
<form action="<hst:actionURL />" method="get">
  <div class="row form-group">
    <div class="col-xs-8">
      <input type="search" value="<c:out value='${query}'/>" name="query" class="form-control"
             placeholder="<fmt:message key='facets.placeholder'/>">
    </div>
    <div class="col-xs-4">
      <button type="submit" class="btn btn-primary pull-right"><fmt:message key='facets.searchbutton'/></button>
    </div>
  </div>
</form>
<c:if test="${facets ne null}">
  <c:set var="facetLimit" value="50"/>
  <ul class="nav nav-list">
    <c:forEach var="facetvalue" items="${facets.folders}">
      <c:if test="${not empty facetvalue.folders}">
        <li><label class="nav-header">${facetvalue.name}</label>
          <ul class="nav nav-list">
            <c:forEach items="${facetvalue.folders}" var="item" varStatus="index">
              <c:choose>
                <c:when test="${item.leaf and item.count gt 0}">
                  <hst:facetnavigationlink remove="${item}" current="${facets}" var="removeLink"/>
                  <li class="active">
                    <a href="${removeLink}">${item.name}&nbsp;<span class="alert-danger"><fmt:message key='facets.remove'/></span></a>
                  </li>
                </c:when>
                <c:otherwise>
                  <hst:link var="link" hippobean="${item}" navigationStateful="true"/>
                  <li <c:if test="${index.count > facetLimit}">class="extra"</c:if>>
                    <a href="${link}">${item.name}&nbsp;<span>(${item.count})</span></a>
                  </li>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </ul>
        </li>
      </c:if>
    </c:forEach>
  </ul>
</c:if>
<%--@elvariable id="editMode" type="java.lang.Boolean"--%>
<c:if test="${editMode and facets eq null}">
  <img src="<hst:link path='/images/essentials/catalog-component-icons/facets.png'/>"> Click to edit Facets
</c:if>
