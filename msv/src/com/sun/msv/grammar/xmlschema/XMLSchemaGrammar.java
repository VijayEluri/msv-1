/*
 * @(#)$Id$
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package com.sun.msv.grammar.xmlschema;

import com.sun.msv.grammar.Grammar;
import com.sun.msv.grammar.Expression;
import com.sun.msv.grammar.ExpressionPool;
import com.sun.msv.grammar.trex.TREXPatternPool;
import java.util.Map;

/**
 * set of XML Schema. This set can be used to validate a document.
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public class XMLSchemaGrammar implements Grammar {

	public XMLSchemaGrammar() {
		this( new TREXPatternPool() );
	}
	
	public XMLSchemaGrammar( TREXPatternPool pool ) {
		this.pool = pool;
	}
	
	/** pool object which was used to construct this grammar. */
	protected final TREXPatternPool pool;
	public final ExpressionPool getPool() {
		return pool;
	}
	
	public Expression topLevel;
	public final Expression getTopLevel() {
		return topLevel;
	}

	/** map from namespace URI to loaded XMLSchemaSchema object. */
	public final Map schemata = new java.util.HashMap();
	
	/** gets XMLSchemaSchema object that has the given target namespace.
	 * 
	 * @return null if no schema is associated with that namespace.
	 */
	public XMLSchemaSchema getByNamespace( String targetNamesapce ) {
		return (XMLSchemaSchema)schemata.get(targetNamesapce);
	}
	
}