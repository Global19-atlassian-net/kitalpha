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

package org.polarsys.kitalpha.ad.viewpoint.dsl.as.diagram.expression.helper.sirius;


/**
 * @author Boubekeur Zendagui
 */

public enum ExpressionKind implements IExpressionFormat{
	Acceleo_2_x, Acceleo_3_x, Ocl;
	
	/**
	 * @see IExpressionFormat#format(String)
	 */
	@Override
	public String format(String expression){
		if (isFormated(expression))
			return expression;
		else
		{
			switch (this) {
			case Acceleo_2_x:
				return getExpression_Acceleo_2(expression);
			case Acceleo_3_x:
				return getExpression_Acceleo_3(expression);
			case Ocl:
				return getExpression_Ocl(expression);
			}
			throw new RuntimeException("Expression kind not supported");
		}
	}
	
	/**
	 * @see IExpressionFormat#isFormated(String)
	 */
	@Override
	public boolean isFormated(String expression){
		switch (this) {
		case Acceleo_2_x:
			return containsDelimiters(expression, 2, 2);
		case Acceleo_3_x:
			return containsDelimiters(expression, 1, 2);
		case Ocl:
			return containsDelimiters(expression, 4, -1);
		}
		return false;
	}
	
	/**
	 * This method format the expression to be compatible with Acceleo 2. 
	 * It add <% at the begin of expression and %> at the end of expression.
	 * 
	 * @param expression to transform to Acceleo 2 expression. 
	 * @return a valid Acceleo 2 expression to use in VSM
	 */
	private String getExpression_Acceleo_2(String expression){
		return "<%" + expression + "%>";
	}
	
	/**
	 * This method format the expression to be compatible with Acceleo 3. 
	 * It add [ at the begin of expression and /] at the end of expression.
	 * 
	 * @param expression to transform to Acceleo 3 expression
	 * @return a valid Acceleo 3 expression to use in VSM
	 */
	private String getExpression_Acceleo_3(String expression){
		return "[" + expression + "/]";
	}
	
	/**
	 * This method format the expression to be compatible with OCL. 
	 * It add ocl: at the begin of expression.
	 * 
	 * @param expression to transform to OCL expression
	 * @return a valid OCL expression to use in VSM
	 */
	private String getExpression_Ocl(String expression){
		// TODO: implement support of OCL expression use in VSM
		throw new OCLExpressionNotSupported();
		// return "ocl:" + expression;
	}
	
	/**
	 * Check if an expression contains delimiter corresponding to it kind.
	 * @param expression the expression to check
	 * @param beginPrefixLenght the begin delimiter length
	 * @param endPrefixLenght the end delimiter length
	 * @return True if the expression contains delimiter, false else
	 */
	private boolean containsDelimiters (String expression, int beginPrefixLenght, int endPrefixLenght){
		boolean result = false;
		boolean checkExpressionEnd = false;

		// Check begin of expression
		String beginDelimiter = expression.substring(0, beginPrefixLenght);
		switch (this) {
		case Acceleo_2_x:
			result = beginDelimiter.equals("<%");
			checkExpressionEnd = true;
			break;
		case Acceleo_3_x:
			result = beginDelimiter.equals("[");
			checkExpressionEnd = true;
			break;
		case Ocl:
			result = beginDelimiter.equals("ocl:");
			break;
		}
		
		// Check end of expression
		if (checkExpressionEnd && endPrefixLenght > 0)
		{
			String endDelimiter = expression.substring(0, beginPrefixLenght);
			switch (this) {
			case Acceleo_2_x:
				result &= endDelimiter.equals("%>");
				break;
			case Acceleo_3_x:
				result &= endDelimiter.equals("/]");
				break;
			case Ocl:
				result &= true;
				break;
			}
		}
		
		return result;
	}
}