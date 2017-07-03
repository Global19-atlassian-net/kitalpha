/*******************************************************************************
 * Copyright (c) 2017 Thales Global Services S.A.S.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *  Thales Global Services S.A.S - initial API and implementation
 ******************************************************************************/
package org.eclipse.kitalpha.richtext.common.impl;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.kitalpha.richtext.common.intf.MDERichTextWidget;
import org.eclipse.kitalpha.richtext.common.intf.SaveStrategy;
import org.eclipse.kitalpha.richtext.common.messages.Messages;
import org.eclipse.kitalpha.richtext.common.util.MDERichTextHelper;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * @author Faycal Abka
 *
 */
public abstract class AbstractMDERichTextWidget implements MDERichTextWidget {
	
	private EObject owner;
	private EStructuralFeature feature;
	
	private SaveStrategy saveStrategy;
	
	private final SaveStrategy DEFAULT_SAVE_STRATEGY = new SaveStrategy() {
		@Override
		public void save(String editorText, EObject objectOwner, EStructuralFeature objectFeature) {
			objectOwner.eSet(objectFeature, editorText);
		}
	};
	
	
	public AbstractMDERichTextWidget(Composite parent) {
		setSaveStrategy(DEFAULT_SAVE_STRATEGY);
	}
	
	public AbstractMDERichTextWidget(Composite parent, int style) {
		this(parent);
	}
	
	
	@Override
	public EObject getElement() {
		return this.owner;
	}

	@Override
	public EStructuralFeature getFeature() {
		return this.feature;
	}

	@Override
	public void bind(EObject owner, EStructuralFeature feature) {
		this.owner = owner;
		this.feature = feature;
		
		setBaseHrefPath(MDERichTextHelper.getProjectPath(owner));
		
		loadContent();
	}

	
	@Override
	public void loadContent() {
		
		areNotNull(getElement(), getFeature());
		
		Object text = getElement().eGet(getFeature());
		String oldValue = getText();
		
		String value = (String)((text instanceof String)? text: ""); //$NON-NLS-1$
		if (value != null && !value.equals(oldValue)){ 
			setText(value);
		}
	}

	
	@Override
	public final void saveContent() {
		areNotNull(getElement(), getFeature());
		String text = getText();
		if (text != null){
			getSaveStrategy().save(text, getElement(), getFeature());
		}
	}
	
	@Override
	public final void setSaveStrategy(SaveStrategy strategy) {
		Assert.isLegal(strategy != null, Messages.RichTextWidget_Common_NullableStrategy_Error);
		this.saveStrategy = strategy;
	}
	
	protected SaveStrategy getSaveStrategy(){
		return this.saveStrategy;
	}
	
	protected String escapeSingleQuote(String value) {
		value = value.replace("'", "&#39;");
		return value;
	}
	
	protected final void areNotNull(Object... objects) {
		if (objects != null){
			int index = 0;
			for (Object object : objects) {
				index++;
				Assert.isNotNull(object, Messages.bind(Messages.RichTextWidget_Common_Nullable_Value_Error, new int[]{index}));
			}
		}
	}
}
