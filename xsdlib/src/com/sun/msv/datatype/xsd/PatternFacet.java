/*
 * @(#)$Id$
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package com.sun.msv.datatype.xsd;

import org.apache.xerces.utils.regex.RegularExpression;
import org.apache.xerces.utils.regex.ParseException;
import java.util.Vector;
import org.relaxng.datatype.ValidationContext;
import org.relaxng.datatype.DatatypeException;

/**
 * "pattern" facet validator
 * 
 * "pattern" is a constraint facet which is applied against lexical space.
 * See http://www.w3.org/TR/xmlschema-2/#dt-pattern for the spec
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public final class PatternFacet extends DataTypeWithLexicalConstraintFacet {
	
	/**
	 * actual object that performs regular expression validation.
	 * one of the item has to match
	 */
	final public RegularExpression[] exps;
	
	/**
	 * string representations of the above RegularExpressions.
	 * this representation is usually human friendly than
	 * the one generated by RegularExpression.toString method.
	 */
	final public String[] patterns;

	
	
	/**
	 * @param regularExpressions
	 *		Vector of XMLSchema-compiliant regular expression
	 *		(see http://www.w3.org/TR/xmlschema-2/#dt-regex )
	 *		There patterns are considered as an 'OR' set.
	 */
	public PatternFacet( String nsUri, String typeName, XSDatatypeImpl baseType, TypeIncubator facets )
		throws DatatypeException {
		super( nsUri, typeName, baseType, FACET_PATTERN, facets );
		
		
		// TODO : am I supposed to implement my own regexp validator?
		// at this time, I use Xerces' one.
		
		Vector regExps = facets.getVector(FACET_PATTERN);
		
		exps = new RegularExpression[regExps.size()];
		patterns = new String[regExps.size()];
		try {
			for(int i=0;i<regExps.size();i++) {
				exps[i] = new RegularExpression((String)regExps.elementAt(i),"X");
				patterns[i] = (String)regExps.elementAt(i);
			}
		} catch( ParseException pe ) {
			// in case regularExpression is not a correct pattern
			throw new DatatypeException( localize( ERR_PARSE_ERROR,
				pe.getMessage() ) );
		}
		
		// loosened facet check is almost impossible for pattern facet.
		// ignore it for now.
	}
	
	protected void diagnoseByFacet(String content, ValidationContext context) throws DatatypeException {
		if( checkLexicalConstraint(content) )	return;
		
		if( exps.length==1 )
			throw new DatatypeException( DatatypeException.UNKNOWN,
				localize(ERR_PATTERN_1,patterns[0]) );
		else
			throw new DatatypeException( DatatypeException.UNKNOWN,
				localize(ERR_PATTERN_MANY) );
	}
	
	protected final boolean checkLexicalConstraint( String literal ) {
		// makes sure that at least one of the patterns is satisfied.
		for( int i=0; i<exps.length; i++ )
			if(exps[i].matches(literal))
				return true;
		// otherwise fail
		return false;
	}
}
