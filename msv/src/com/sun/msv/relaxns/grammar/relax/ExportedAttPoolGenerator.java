/*
 * @(#)$Id$
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package com.sun.tranquilo.relaxns.grammar.relax;

import com.sun.tranquilo.grammar.*;
import com.sun.tranquilo.grammar.relax.*;

/**
 * creates Expression that validates exported attPool.
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
class ExportedAttPoolGenerator extends ExpressionCloner implements RELAXExpressionVisitorExpression
{
	ExportedAttPoolGenerator( ExpressionPool pool ) { super(pool); }
		
	private String targetNamespace;
	public Expression create( RELAXModule module, Expression exp )
	{
		targetNamespace = module.targetNamespace;
		return exp.visit(this);
	}
		
	public Expression onAttribute( AttributeExp exp )
	{
		if(!(exp.nameClass instanceof SimpleNameClass ))
			return exp;	// leave it as is. or should we consider this as a failed assertion?
			
		SimpleNameClass nc = (SimpleNameClass)exp.nameClass;
		if( !nc.namespaceURI.equals("") )
			return exp;	// externl attributes. leave it as is.
			
		return pool.createAttribute(
			new SimpleNameClass( targetNamespace, nc.localName ),
			exp.exp );
	}
		
	// we are traversing attPools. thus these will never be possible.
	public Expression onElement( ElementExp exp )			{ throw new Error(); }
	public Expression onTag( TagClause exp )				{ throw new Error(); }
	public Expression onElementRules( ElementRules exp )	{ throw new Error(); }
	public Expression onHedgeRules( HedgeRules exp )		{ throw new Error(); }
		
	public Expression onRef( ReferenceExp exp )
	{
		// this class implements RELAXbrabraVisitor. So this method should never be called
		throw new Error();
	}
		
	public Expression onAttPool( AttPoolClause exp )
	{// create exported version for them, too.
			
		// note that thsi exp.exp may be a AttPool of a different module.
		// In that case, calling visit method is no-op. But at least
		// it doesn't break anything.
		return exp.exp.visit(this);
	}
}
