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

import com.sun.msv.grammar.NameClass;
import com.sun.msv.grammar.NameClassVisitor;
import com.sun.msv.util.StringPair;
import java.util.Set;


public class LaxDefaultNameClass implements NameClass {
	
	public LaxDefaultNameClass() {
		allowedNames.add( new StringPair(NAMESPACE_WILDCARD,LOCALNAME_WILDCARD) );
	}
	
	public Object visit( NameClassVisitor visitor ) {
		// LaxDefaultNameClass cannot be visited.
		// TODO: maybe we should do something else.
		throw new UnsupportedOperationException();
	}
	
	public boolean accepts( String namespaceURI, String localName ) {
		return !allowedNames.contains( new StringPair(namespaceURI,localName) );
	}
	
	/** set of StringPair.
	 * each item represents one allowed name.
	 * it also contains WILDCARD as entry.
	 */
	private final Set allowedNames = new java.util.HashSet();
	
	public void addAllowedName( String namespaceURI, String localName ) {
		allowedNames.add( new StringPair(namespaceURI,localName) );
		allowedNames.add( new StringPair(namespaceURI,LOCALNAME_WILDCARD) );
		allowedNames.add( new StringPair(NAMESPACE_WILDCARD,localName) );
	}
}