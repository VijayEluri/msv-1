/*
 * @(#)$Id$
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package batch.verifier;

import javax.xml.parsers.*;
import java.util.Iterator;
import java.io.*;
import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.InputSource;
import junit.framework.*;
import com.sun.msv.verifier.*;
import com.sun.msv.verifier.regexp.REDocumentDeclaration;
import com.sun.msv.reader.GrammarReaderController;
import com.sun.msv.reader.dtd.DTDReader;
import com.sun.msv.reader.util.GrammarLoader;
import com.sun.msv.grammar.Grammar;

/**
 * validates instances by schemata.
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public class BatchVerifyTester extends batch.BatchTester
{
	public static void main( String[] av ) throws Exception {
		new BatchVerifyTester().run(av);
	}

	protected void usage() {
		System.out.println(
			"usage "+this.getClass().getName()+" (relax|trex|xsd|dtd|rng) <test case directory>\n"+
			"  tests the validation engine by using schema files and test instances\n"+
			"  in the specified directory.");
	}
	
	/** gets a TestSuite that loads and verifies all test instances in the test directory. */
	protected void populateSuite( TestSuite suite, String[] schemas ) {
		// each schema will have its own suite.
		if( schemas!=null )
			for( int i=0; i<schemas.length; i++ )
				suite.addTest( new SchemaVerifySuite(this,schemas[i]).suite() );
	}
}
