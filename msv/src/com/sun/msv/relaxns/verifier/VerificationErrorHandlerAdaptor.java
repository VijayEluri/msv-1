package com.sun.tranquilo.relaxns.verifier;

import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.Locator;
import org.xml.sax.helpers.LocatorImpl;
import com.sun.tranquilo.verifier.VerificationErrorHandler;
import com.sun.tranquilo.verifier.ValidityViolation;

/** wraps VerificationErrorHandler by ISORELAX ErrorHandler interface. */
public class VerificationErrorHandlerAdaptor implements ErrorHandler
{
	private final VerificationErrorHandler core;
	
	public VerificationErrorHandlerAdaptor( VerificationErrorHandler core ) {
		this.core = core;
	}
	
	public void fatalError( SAXParseException spe ) throws SAXException {
		error(spe);
	}
	
	public void error( SAXParseException spe ) throws SAXException {
		core.onError( new ValidityViolation( getLocator(spe), spe.getMessage() ) );
	}
	
	public void warning( SAXParseException spe ) throws SAXException {
		core.onWarning( new ValidityViolation( getLocator(spe), spe.getMessage() ) );
	}
			
	protected Locator getLocator( SAXParseException spe ) {
		LocatorImpl loc = new LocatorImpl();
		loc.setColumnNumber( spe.getColumnNumber() );
		loc.setLineNumber( spe.getLineNumber() );
		loc.setSystemId( spe.getSystemId() );
		loc.setPublicId( spe.getPublicId() );
		
		return loc;
	}
}