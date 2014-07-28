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

/**
 * 
 */
package org.polarsys.kitalpha.composer.api.library;

import org.eclipse.emf.ecore.EObject;

import org.polarsys.kitalpha.composer.metamodel.allocation.base.Root;

/**
 * @author Guillaume Gebhart
 *
 */
public interface ISearchAlgorithm {

	
	public String getIncludeFromAllocation(Root root, EObject element);
}