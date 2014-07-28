/*******************************************************************************
 * Copyright (c) 2014 Thales Global Services S.A.S.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *  Thales Global Services S.A.S - initial API and implementation
 ******************************************************************************/

package org.polarsys.kitalpha.ad.af.dsl.cs.text;

import org.polarsys.kitalpha.ad.af.dsl.cs.text.scoping.AfdescContainerManager;
import org.polarsys.kitalpha.ad.af.dsl.cs.text.scoping.AfdescGlobalScopeProvider;

/**
 * 
 * @author Amine Lajmi
 *
 */
public class AfdescRuntimeModule extends org.polarsys.kitalpha.ad.af.dsl.cs.text.AbstractAfdescRuntimeModule {

	public Class<? extends org.eclipse.xtext.resource.IContainer.Manager> bindIContainer$Manager() {
		return AfdescContainerManager.class;
	}
	
	public Class<? extends org.eclipse.xtext.scoping.IGlobalScopeProvider> bindIGlobalScopeProvider() {
		return AfdescGlobalScopeProvider.class;
	}
	
}