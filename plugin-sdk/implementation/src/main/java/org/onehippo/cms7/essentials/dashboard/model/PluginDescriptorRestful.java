/*
 * Copyright 2014 Hippo B.V. (http://www.onehippo.com)
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

package org.onehippo.cms7.essentials.dashboard.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.onehippo.cms7.essentials.dashboard.rest.PluginModuleRestful;

import com.google.common.base.Strings;

/**
 * @version "$Id$"
 */
@XmlRootElement(name = "plugin")
public class PluginDescriptorRestful implements PluginDescriptor, Restful {

    private static final long serialVersionUID = 1L;

    private List<String> restClasses;
    private Vendor vendor;
    private List<EssentialsDependency> dependencies;
    private List<Repository> repositories;
    private String name;
    private String introduction;
    private String description;
    private List<String> imageUrls;
    private String id;
    private String packageClass;
    private String parameterServiceClass;
    private boolean hasConfiguration = false;
    private String packageFile;
    private String type;
    private String installState;
    private String icon;
    private Calendar dateInstalled;
    private String documentationLink;
    private List<PluginModuleRestful.PrefixedLibrary> libraries = new ArrayList<>();


    public void addLibrary(final PluginModuleRestful.PrefixedLibrary library) {
        libraries.add(library);
    }

    public void addAllLibraries(final List<PluginModuleRestful.PrefixedLibrary> libraries) {
        if (this.libraries == null) {
            this.libraries = new ArrayList<>();

        }
        this.libraries.addAll(libraries);
    }

    public void addAllRepositories(final List<Repository> repos) {
        if (this.repositories == null) {
            this.repositories = new ArrayList<>();

        }
        this.repositories.addAll(repos);
    }

    public List<PluginModuleRestful.PrefixedLibrary> getLibraries() {
        return libraries;
    }

    public void setLibraries(final List<PluginModuleRestful.PrefixedLibrary> libraries) {
        this.libraries = libraries;
    }

    public PluginDescriptorRestful(final String name) {
        this.name = name;
    }

    public PluginDescriptorRestful() {

    }

    public Calendar getDateInstalled() {
        return dateInstalled;
    }

    public void setDateInstalled(final Calendar dateInstalled) {
        this.dateInstalled = dateInstalled;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(final String type) {
        this.type = type;
    }

    @Override
    public String getInstallState() {
        return installState;
    }

    @Override
    public void setInstallState(final String installState) {
        this.installState = installState;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public String getPackageFile() {
        return packageFile;
    }

    @Override
    public void setPackageFile(final String packageFile) {
        this.packageFile = packageFile;
    }

    @Override
    public String getPackageClass() {
        return packageClass;
    }

    @Override
    public void setPackageClass(final String packageClass) {
        this.packageClass = packageClass;
    }

    @Override
    public String getParameterServiceClass() {
        return parameterServiceClass;
    }

    @Override
    public void setParameterServiceClass(final String parameterServiceClass) {
        this.parameterServiceClass = parameterServiceClass;
    }

    @Override
    public void setHasConfiguration(final boolean hasConfiguration) {
        this.hasConfiguration = hasConfiguration;
    }

    @Override
    public boolean getHasConfiguration() {
        return hasConfiguration;
    }

    @Override
    @XmlElementRef(type = VendorRestful.class)
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
    @JsonSubTypes({@JsonSubTypes.Type(value = VendorRestful.class, name = "vendor")})
    public Vendor getVendor() {
        return vendor;
    }


    @Override
    public void setVendor(final Vendor vendor) {
        this.vendor = vendor;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getDocumentationLink() {
        return documentationLink;
    }

    @Override
    public void setDocumentationLink(final String documentationLink) {
        this.documentationLink = documentationLink;
    }

    @XmlElementRef(type = DependencyRestful.class)
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
    @JsonSubTypes({@JsonSubTypes.Type(value = DependencyRestful.class, name = "dependency")})
    @Override
    public List<EssentialsDependency> getDependencies() {
        if (dependencies == null) {
            return new ArrayList<>();
        }
        return dependencies;
    }

    @Override
    public void setDependencies(final List<EssentialsDependency> dependencies) {
        this.dependencies = dependencies;
    }

    public void addDependency(final EssentialsDependency dependency) {
        if (dependencies == null) {
            dependencies = new ArrayList<>();
        }
        dependencies.add(dependency);

    }

    public void addRepository(final Repository repository) {
        if (repositories == null) {
            repositories = new ArrayList<>();
        }
        repositories.add(repository);
    }

    @Override
    public String getIntroduction() {
        return introduction;
    }

    @Override
    public void setIntroduction(final String introduction) {
        this.introduction = introduction;
    }


    public void addRestCLass(final String restClass) {

        if (restClasses == null) {
            restClasses = new ArrayList<>();
        }
        restClasses.add(restClass);
    }


    @Override
    public List<String> getRestClasses() {
        return restClasses;
    }

    @Override
    public void setRestClasses(final List<String> restClasses) {
        this.restClasses = restClasses;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public List<String> getImageUrls() {
        return imageUrls;
    }

    @Override
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @XmlElementRef(type = RepositoryRestful.class)
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
    @JsonSubTypes({@JsonSubTypes.Type(value = RepositoryRestful.class, name = "repository")})
    @Override
    public List<Repository> getRepositories() {
        if (repositories == null) {
            return new ArrayList<>();
        }
        return repositories;
    }

    @Override
    public String getIcon() {
        if(Strings.isNullOrEmpty(icon)){
            return "/essentials/images/icons/missing-icon.png";
        }
        return icon;
    }

    @Override
    public void setIcon(final String icon) {
        this.icon = icon;
    }

    @Override
    public void setRepositories(final List<Repository> repositories) {
        this.repositories = repositories;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PluginRestful{");
        sb.append("restClasses=").append(restClasses);
        sb.append(", vendor=").append(vendor);
        sb.append(", dependencies=").append(dependencies);
        sb.append(", name='").append(name).append('\'');
        sb.append(", introduction='").append(introduction).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", dateInstalled=").append(dateInstalled);
        sb.append('}');
        return sb.toString();
    }
}