package com.sun.tranquilo.grammar.xmlschema;

import com.sun.tranquilo.grammar.Grammar;
import com.sun.tranquilo.grammar.Expression;
import com.sun.tranquilo.grammar.ExpressionPool;
import com.sun.tranquilo.grammar.trex.TREXPatternPool;
import java.util.Map;

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
}
