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

package org.polarsys.kitalpha.transposer.examples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.INewWizard;

import org.polarsys.kitalpha.examples.generic.wizard.AbstractExampleWizard;

/**
 * 
 * @author Guillaume Gebhart
 *
 */
public class TransposerExtendedUseCaseWizard extends AbstractExampleWizard
		implements INewWizard {

	@Override
	protected Collection<?> getProjectDescriptors() {

		List<ProjectDescriptor> projects = new ArrayList<ProjectDescriptor>(1);
		projects
				.add(new ProjectDescriptor(
						"org.polarsys.kitalpha.transposer.examples", "zips/ExtendedTransformUmlToEcore.zip", "org.polarsys.kitalpha.transposer.examples.m2m.uml.to.ecore.extended")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		return projects;
	}

	@Override
	protected void log(Exception e) {
		if (e instanceof CoreException) {
			TExamplePlugin.getDefault().getLog().log(
					((CoreException) e).getStatus());
		} else {
			TExamplePlugin.getDefault().getLog().log(
					new Status(IStatus.ERROR, TExamplePlugin.getDefault()
							.getBundle().getSymbolicName(), IStatus.ERROR, e
							.getMessage(), e));
		}

	}

}