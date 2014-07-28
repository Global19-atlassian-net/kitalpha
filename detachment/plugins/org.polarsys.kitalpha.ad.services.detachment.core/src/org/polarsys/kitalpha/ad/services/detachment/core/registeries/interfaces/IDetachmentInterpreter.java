/*******************************************************************************
 * Copyright (c) 2013 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    THALES GLOBAL SERVICES - Initial API and implementation
 *******************************************************************************/
package org.polarsys.kitalpha.ad.services.detachment.core.registeries.interfaces;

import org.eclipse.emf.ecore.resource.ResourceSet;


/**
 * 
 * @author Faycal Abka
 */
public interface IDetachmentInterpreter {
	
	/**
	 * The algorithm to apply on the models
	 * 
	 * @param wizardData
	 * @param resourceSet
	 */
	public void process(IDetachmentWizardData wizardData, ResourceSet resourceSet);
	
}