/*******************************************************************************
 * Copyright (c) 2014 Thales Global Services S.A.S.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *   Thales Global Services S.A.S - initial API and implementation
 ******************************************************************************/

package org.polarsys.kitalpha.ad.af.dsl.as.desc.helper.model;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.polarsys.kitalpha.ad.af.dsl.as.model.afdesc.ArchitectureFramework;
import org.polarsys.kitalpha.ad.af.dsl.as.model.afdesc.Configuration;
import org.polarsys.kitalpha.ad.af.dsl.as.model.afdesc.ConfigurationElement;
import org.polarsys.kitalpha.ad.af.dsl.as.model.afdesc.GenerationConfiguration;
import org.polarsys.kitalpha.ad.af.dsl.as.model.afdesc.NamedElement;

/**
 * @author Boubekeur Zendagui
 */

public class AFSpecConfigurationHelper {

	/**
	 * 
	 * @param af_element any AF Desc model element
	 * @return 
	 */
	public static String getAFProjectName(NamedElement af_element){
		GenerationConfiguration generationConfiguration = getAFGenerationConfiguration(af_element);
		return generationConfiguration.getRootProjectName();
	}
	
	/**
	 * This method return the AF Configuration element
	 * @param af_element any AF Desc model element
	 * @return The {@link Configuration} element of the ArchitectureFramework 
	 * element containing the af_element 
	 */
	public static Configuration getAFConfiguration(NamedElement af_element){
		ArchitectureFramework af = (ArchitectureFramework) EcoreUtil.getRootContainer(af_element);
		return af.getOwned_configuration();
	}
	
	/**
	 * 
	 * @param af_element any AF Desc model element
	 * @return
	 */
	public static GenerationConfiguration getAFGenerationConfiguration(NamedElement af_element){
		Configuration configuration = getAFConfiguration(af_element);
		for (ConfigurationElement configurationElement : configuration.getOwned_element())
		{
			if (configurationElement instanceof GenerationConfiguration)
				return (GenerationConfiguration) configurationElement;
		}
		return null;
	}
}