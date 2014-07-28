package org.polarsys.kitalpha.ad.viewpoint.dsl.generation.build.templates;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;

@SuppressWarnings("all")
public class GenBuildDocumentation implements IGenerator {
  private String viewpointShortName = null;
  
  private String repositoryUrl = null;
  
  private String buildFilePath = null;
  
  public String setRepositoryURL(final String url) {
    String _repositoryUrl = this.repositoryUrl = url;
    return _repositoryUrl;
  }
  
  public String setBuildFilePath(final String path) {
    String _buildFilePath = this.buildFilePath = path;
    return _buildFilePath;
  }
  
  public String setViewpointShortName(final String name) {
    String _viewpointShortName = this.viewpointShortName = name;
    return _viewpointShortName;
  }
  
  public void doGenerate(final Resource input, final IFileSystemAccess fsa) {
    CharSequence _generateContent = this.generateContent();
    fsa.generateFile("README.txt", _generateContent);
  }
  
  public CharSequence generateContent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/*******************************************************************************");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* Copyright (c) 2014 Thales Global Services S.A.S.");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*  All rights reserved. This program and the accompanying materials");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*  are made available under the terms of the Eclipse Public License v1.0");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*  which accompanies this distribution, and is available at");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*  http://www.eclipse.org/legal/epl-v10.html");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* ");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* Contributors:");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*  Thales Global Services S.A.S - initial API and implementation");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("******************************************************************************/");
    _builder.newLine();
    _builder.newLine();
    _builder.append("This file describes the steps to be taken towards the configuration of a Jenkins build job for the viewpoint ");
    _builder.append(this.viewpointShortName, "");
    _builder.append(".");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("PREREQUISITES:");
    _builder.newLine();
    _builder.newLine();
    _builder.append("Make sure to have the following prerequisites:");
    _builder.newLine();
    _builder.append("- Jenkins build system installed either locally or on a remote server, you need to have administrative rights to be able to congfigure jobs.");
    _builder.newLine();
    _builder.append("- A target application installed in your local machine, e.g. Kitalpha.");
    _builder.newLine();
    _builder.newLine();
    _builder.append("JOB CONFIGURAITON:");
    _builder.newLine();
    _builder.newLine();
    _builder.append("1. Log on to Jenkins application");
    _builder.newLine();
    _builder.append("2. Create a new free-style Jenkins Job");
    _builder.newLine();
    _builder.append("3. In \"Gestion de code source\" section, select the type of repository where the viewpoints plugins are located");
    _builder.newLine();
    _builder.append("4. In \"URL du repository\" section, enter ");
    _builder.append(this.repositoryUrl, "");
    _builder.newLineIfNotEmpty();
    _builder.append("5. In \"Repertoire local du module\" section, enter \"svn/svn_viewpoint\"");
    _builder.newLine();
    _builder.append("6. In \"Build\" section, click on \"avanc\u00E9\" button");
    _builder.newLine();
    _builder.append("7. In \"Fichier du build\", enter ");
    _builder.append(this.buildFilePath, "");
    _builder.newLineIfNotEmpty();
    _builder.append("8. Save the job and you are done.");
    _builder.newLine();
    return _builder;
  }
}