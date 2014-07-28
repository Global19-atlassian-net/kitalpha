/*******************************************************************************
 * Copyright (c) 2014 Thales Global Services S.A.S.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *   Thales Global Services S.A.S - initial API and implementation
 *******************************************************************************/

package org.polarsys.kitalpha.emde.extension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.EMFEditPlugin;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.IChildCreationExtender.Descriptor;
import org.eclipse.osgi.util.NLS;
import org.polarsys.kitalpha.emde.extension.i18n.Messages;
import org.polarsys.kitalpha.emde.extension.registry.ItemProviderAdapterFactoriesRegistryProvider;
import org.polarsys.kitalpha.emde.extension.registry.URIFactory;
import org.polarsys.kitalpha.emde.extension.utils.Log;

/**
 * @author Thomas Guiu
 * 
 */

public abstract class DefaultModelExtensionManager implements ModelExtensionManager {

	public DefaultModelExtensionManager() {
		super();
		loadExtensibleModels();
	}

	private static final Set<String> discardedModels = new HashSet<String>();

	private final Map<String, ExtensibleModel> extensibleModelsKeyURI = new HashMap<String, ExtensibleModel>();
	private final Map<String, ExtendedModel> extendedModelsKeyURI = new HashMap<String, ExtendedModel>();
	private List<ExtensionManagerDelegate> delegates = new ArrayList<ExtensionManagerDelegate>();

	public boolean isExtensionModelDisabled(String extensibleModel, String extendedModel) {
		return isExtensionModelDisabled(getExtendedModel(extendedModel));
	}

	public boolean canDisableExtensionModel(ExtendedModel extended) {
		return true;
	}

	public boolean isExtensionModelDisabled(Object object) {
		return object instanceof EObject && isExtensionModelDisabled((EObject) object);
	}

	public boolean isExtensionModelDisabled(EObject eObject) {
		if (eObject.eResource() == null) {
			return false;
		}
		String nsURI = eObject.eClass().getEPackage().getNsURI();
		for (ExtensionManagerDelegate delegate : delegates) {
			if (delegate.getManagedNsUris().contains(nsURI)) {
				Boolean extensionModelDisabled = delegate.isExtensionModelDisabled(eObject);
				if (extensionModelDisabled != null)
					return extensionModelDisabled.booleanValue();
			}
		}
		ExtendedModel extended = getExtendedModel(eObject.eClass().getEPackage().getNsURI().toString());
		return ((extended != null) && isExtensionModelDisabled(extended));
	}

	/**
	 * Loads the extensible model.
	 */
	protected void loadExtensibleModels() {
		// Process extensible model and extended models
		//
		for (Map.Entry<String, URIFactory> entry : ItemProviderAdapterFactoriesRegistryProvider.getItemProviderAdapterFactories().entrySet()) {
			try {
				if (discardedModels.contains(entry.getKey()))
					continue;
				// Process extensible model
				//
				ExtensibleModel extensibleModel = new ExtensibleModel(entry.getKey(), entry.getValue());
				// Save
				//
				extensibleModelsKeyURI.put(entry.getKey(), extensibleModel);
				// Process extended models
				//
				Collection<Descriptor> descriptors = EMFEditPlugin.getChildCreationExtenderDescriptorRegistry().getDescriptors(entry.getKey());
				if ((descriptors == null) || descriptors.isEmpty()) {
					continue;
				}
				for (Descriptor descriptor : descriptors) {
					IChildCreationExtender extender = null;
					try {
						extender = descriptor.createChildCreationExtender();
					} catch (WrappedException e1) {
						discardedModels.add(entry.getKey());
						Log.getDefault().logError("Cannot create extender for model:'" + entry.getKey() + "' -> model is discarded.", e1);
					}
					if (extender == null)
						continue;
					String factory = null;
					try {
						if (extender.getClass().getDeclaringClass() != null)
							factory = extender.getClass().getDeclaringClass().getName();
					} catch (NoClassDefFoundError e) {
						String name = extender.getClass().getName();
						int indexOf = name.indexOf("$");
						if (indexOf != -1)
							factory = name.substring(0, indexOf);
					}
					if (factory != null) {

						URIFactory uriFactory = ItemProviderAdapterFactoriesRegistryProvider.getURIFactoryFromAdapterFactoryName(factory);
						if (uriFactory != null) {
							ExtendedModel extendedModel = new ExtendedModel(extensibleModel, uriFactory.getNsURI(), uriFactory);
							extendedModelsKeyURI.put(uriFactory.getNsURI(), extendedModel);
						} else {
							String msg = NLS.bind(Messages.ImplementationRegistryProvider_unknownClass, new String[] { ItemProviderAdapterFactoriesRegistryProvider.getName(), extender.getClass().getDeclaringClass().getName() });
							Log.getDefault().logError(msg);
						}
					}
				}
			} catch (Throwable e) {
				Log.getDefault().logError("Cannot load model:" + entry.getKey(), e);
			}
		}
	}

	public Collection<AdapterFactory> getExtendedModelAdapterFactories(String clazz) {
		Collection<AdapterFactory> adapterFactories = new ArrayList<AdapterFactory>();
		if (clazz == null || clazz.trim().length() == 0) {
			return adapterFactories;
		}
		clazz = clazz.trim();
		for (ExtensibleModel extensibleModel : extensibleModelsKeyURI.values()) {
			if (extensibleModel.getURIFactory().getAdapterFactoryName().equals(clazz) == false) {
				continue;
			}
			// Process All Extended Models
			//
			for (ExtendedModel extendedModel : extensibleModel.getAllExtendedModels()) {
				adapterFactories.add(extendedModel.getAdapterFactory());
			}
		}
		return adapterFactories;
	}

	protected static void fireExtensionEvent(String nsURI, boolean enable) {
		ModelExtensionHelper.fireExtensionEvent(nsURI, enable);
	}

	protected ExtendedModel getExtendedModel(String extendedModel) {
		return extendedModelsKeyURI.get(extendedModel);
	}

	/**
	 * 
	 * @return an {@link ExtensibleModel}
	 */
	public ExtensibleModel getExtensibleModel(String extensibleModel) {
		if ((extensibleModel != null) && (extensibleModel.trim().length() > 0)) {
			return extensibleModelsKeyURI.get(extensibleModel.trim());
		}
		return null;
	}

	public List<ExtensionManagerDelegate> getDelegates() {
		return delegates;
	}

}