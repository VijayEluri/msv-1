package com.sun.tranquilo.verifier.regexp;

import com.sun.tranquilo.grammar.*;
import java.util.Collection;

/**
 * Collects "elements of concern".
 * 
 * "Elements of concern" are ElementExps that are possibly applicable to
 * the next element. These gathered element declarations are then tested against
 * next XML element.
 */
public class ElementsOfConcernCollector implements ExpressionVisitor
{
	private Collection result;
	
	protected ElementsOfConcernCollector() {}
	
	public final void collect( Expression exp, Collection result )
	{
		this.result = result;
		exp.visit(this);
	}
	
	public final Object onAttribute( AttributeExp exp )	{ return null; }
	
	public final Object onChoice( ChoiceExp exp )
	{
		exp.exp1.visit(this);
		exp.exp2.visit(this);
		return null;
	}
	
	public final Object onElement( ElementExp exp )
	{
		// found.
		result.add( exp );
		return null;
	}
	
	public final Object onOneOrMore( OneOrMoreExp exp )
	{
		exp.exp.visit(this);
		return null;
	}
	
	public final Object onMixed( MixedExp exp )
	{
		exp.exp.visit(this);
		return null;
	}
	
	public final Object onEpsilon()		{ return null; }
	public final Object onNullSet()		{ return null; }
	public final Object onAnyString()	{ return null; }
	
	public final Object onRef( ReferenceExp exp )
	{
		return exp.exp.visit(this);
	}
	
	public final Object onSequence( SequenceExp exp )
	{
		exp.exp1.visit(this);
		if(exp.exp1.isEpsilonReducible())
			exp.exp2.visit(this);
		return null;
	}
	
	public final Object onTypedString( TypedStringExp exp )	{ return null; }
}