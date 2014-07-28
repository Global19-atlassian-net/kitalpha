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

package org.polarsys.kitalpha.ad.viewpoint.dsl.services.preference.internal;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.polarsys.kitalpha.ad.viewpoint.dsl.services.preference.helper.VPRootProjectNamePreferencesHelper;

/**
 * @author Boubekeur Zendagui
 */

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public PreferenceInitializer() {
	}

	/**
	 * @see AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		String customizedValue = VPRootProjectNamePreferencesHelper.getCustomizedValue();
		if (! VPRootProjectNamePreferencesHelper.useTargetApplicationValue() &&
				(customizedValue == null || (customizedValue != null && customizedValue.trim().length() == 0)))
		{
			VPRootProjectNamePreferencesHelper.restorDefautValues();
		}
	}
}