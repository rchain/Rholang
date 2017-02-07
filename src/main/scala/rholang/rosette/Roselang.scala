// -*- mode: Scala;-*- 
// Filename:    Roselang.scala 
// Authors:     luciusmeredith                                                    
// Creation:    Thu Feb  2 14:34:16 2017 
// Copyright:   See site license 
// Description: 
// ------------------------------------------------------------------------

import coop.rchain.lib.term._
import coop.rchain.lib.zipper._
import coop.rchain.lib.navigation.{ Right => NRight, Left => NLeft,_ }

import rholang.parsing.rholang2._
import rholang.parsing.rholang2.Absyn._

trait StrTermNavigation extends TermNavigation[String,String,String]
trait StrTermMutation extends TermMutation [String,String,String]
trait StrTermZipperComposition extends TermZipperComposition[String,String,String]
trait StrTermSubstitution extends TermSubstitution[String,String,String]

object VisitorTypes {
  type A = Option[Location[Either[String,String]]]
  type R = Option[Location[Either[String,String]]]    
}

object StrTermCtorAbbrevs {
  type StrTermCtxt = TermCtxt[String,String,String] with Factual
  def V( v : String ) : StrTermCtxt = StrTermCtxtLf( Right( v ) )
  def G( v : String ) : StrTermCtxt = StrTermCtxtLf( Left( v ) )
  def B( v : String )( terms : StrTermCtxt* ) = StrTermCtxtBr( v, terms.toList )
}

object StrZipAbbrevs {  
  def L( term : StrTermCtorAbbrevs.StrTermCtxt, ctxt : Context[Either[String,String]] ) : Location[Either[String,String]] = Location( term, ctxt )   
  def HV( cv : String ) : Location[Either[String,String]] = 
    L( StrTermCtorAbbrevs.V( cv ), Top[Either[String,String]]() )
}

trait StrFoldCtxtVisitor
extends FoldVisitor[VisitorTypes.R,VisitorTypes.A] {
  def zipr : StrTermNavigation with StrTermMutation with StrTermZipperComposition
  def theCtxtVar : String

  def wrap( context : VisitorTypes.R ) : VisitorTypes.R = {
    context
  }
  def leaf( context : VisitorTypes.R ) : VisitorTypes.R = {
    wrap( context )
  }    
  override def combine(
    x : VisitorTypes.R, 
    y : VisitorTypes.R, 
    context : VisitorTypes.R
  ) : VisitorTypes.R = {
    /*
     println(
      (
	"/* ------------------------------------------------------- */\n"
	+ "/* method: " + "combine" + " */\n"
	+ "/* x: " + x + " */\n"
	+ "/* y: " + y + " */\n"
	+ "/* context: " + context + " */\n"
	+ "/* ------------------------------------------------------- */\n"
      )
    )
    */
    
    val rslt =
      for( 
	xLoc@Location( xTerm : StrTermCtorAbbrevs.StrTermCtxt, xCtxt ) <- x;
	yLoc@Location( yTerm : StrTermCtorAbbrevs.StrTermCtxt, yCtxt ) <- y
      ) yield {
	/*
	 println(
	  (
	    "/* ------------------------------------------------------- */\n"
	    + "/* method: " + "combine" + " continued" + " */\n"
	    + "/* xLoc: " + xLoc + " */\n"
	    + "/* yLoc: " + yLoc + " */\n"
	    + "/* ------------------------------------------------------- */\n"
	  )
	)
	*/
	yLoc match {
	  case Location( StrTermCtxtLf( Right( v ) ), Top( ) ) => xLoc
	  case Location( _, Top( ) ) => {
	    xCtxt match {
	      case Top() => {
		val loc = zipr.up( zipr.insertDown( yLoc, xTerm ) )
		/*
		 * println(
		  (
		    "/* ------------------------------------------------------- */\n"
		    + "/* method: " + "combine" + " continued" + " */\n"
		    + "/* loc: " + loc + " */\n"
		    + "/* ------------------------------------------------------- */\n"
		  )
		)
		*/
		    
		loc
	      }
	      case _ => {
		val loc = zipr.update( yLoc, xTerm )
		/*
		 println(
		  (
		    "/* ------------------------------------------------------- */\n"
		    + "/* method: " + "combine" + " continued" + " */\n"
		    + "/* loc: " + loc + " */\n"
		    + "/* ------------------------------------------------------- */\n"
		  )
		)
		*/
		
		loc
	      }
	    }	    
	  }
	  case _ => {
	    xLoc match {	      
	      case Location( StrTermCtxtLf( Right( v ) ), Top() ) => {
		val loc = zipr.update( yLoc, xTerm )
		/*
		 println(
		  (
		    "/* ------------------------------------------------------- */\n"
		    + "/* method: " + "combine" + " continued" + " */\n"
		    + "/* loc: " + loc + " */\n"
		    + "/* ------------------------------------------------------- */\n"
		  )
		)
		*/
		  		
		if ( ( v ).equals( theCtxtVar ) ) {		
		  loc
		}
		else {
		  zipr.up( loc )
		}		
	      }
	      case Location( _, Top() ) => {
		val loc = zipr.up( zipr.update( yLoc, xTerm ) )
		/*
		 println(
		  (
		    "/* ------------------------------------------------------- */\n"
		    + "/* method: " + "combine" + " continued" + " */\n"
		    + "/* loc: " + loc + " */\n"
		    + "/* ------------------------------------------------------- */\n"
		  )
		)
		*/
		
		loc
	      }
	      case Location(_, LabeledTreeContext(lbl: String, left, nrCtxt, right)) => {
		Location[Either[String,String]](
		  xTerm,
		  zipr.compose( yCtxt, xCtxt )
		)
	      }
	    }
	  }
	}
			
      }

    /*
     println(
      (
	"/* ------------------------------------------------------- */\n"
	+ "/* method: " + "combine" + " continued" + " */\n"
	+ "/* rslt: " + rslt + " */\n"
	+ "/* ------------------------------------------------------- */\n"
      )
    )
    */

    rslt
  }
}

trait RholangASTToTerm 
extends StrFoldCtxtVisitor {
  import VisitorTypes._
  import StrZipAbbrevs._
  import StrTermCtorAbbrevs._

  def H() = HV( theCtxtVar )

  /* Contr */
  override def visit( p : DContr, arg : A ) : R
  /* Proc */
  override def visit(  p : PNil, arg : A ) : R = {
    combine( arg, Some( L( G( "#niv" ), Top() ) ), Some( H() ) )
  }
  override def visit(  p : PValue, arg : A ) : R
  override def visit(  p : PVar, arg : A ) : R
  override def visit(  p : PDrop, arg : A ) : R
  override def visit(  p : PInject, arg : A ) : R
  override def visit(  p : PLift, arg : A ) : R
  override def visit(  p : PInput, arg : A ) : R
  override def visit(  p : PChoice, arg : A ) : R
  override def visit(  p : PMatch, arg : A ) : R
  override def visit(  p : PNew, arg : A ) : R
  override def visit(  p : PConstr, arg : A ) : R
  override def visit(  p : PPar, arg : A ) : R

  /* Chan */
  override def visit(  p : CVar, arg : A ) : R
  override def visit(  p : CQuote, arg : A ) : R
  /* Bind */
  override def visit(  p : InputBind, arg : A ) : R
  /* PMBranch */
  override def visit(  p : PatternMatch, arg : A
 ) : R
  /* CBranch */
  override def visit(  p : Choice, arg : A ) : R
  /* Value */
  override def visit(  p : VQuant, arg : A ) : R
  override def visit(  p : VEnt, arg : A ) : R
  /* Quantity */
  override def visit(  p : QInt, arg : A ) : R
  override def visit(  p : QDouble, arg : A ) : R
  /* Entity */
  override def visit(  p : EChar, arg : A ) : R
  override def visit(  p : EStruct, arg : A ) : R
  override def visit(  p : ECollect, arg : A ) : R
  /* Struct */
  override def visit(  p : StructConstr, arg : A ) : R
  /* Collect */
  override def visit(  p : CString, arg : A ) : R
  /* VarPattern */
  override def visit(  p : VarPtVar, arg : A ) : R
  override def visit(  p : VarPtWild, arg : A ) : R
  /* PPattern */
  override def visit(  p : PPtVar, arg : A ) : R
  override def visit(  p : PPtNil, arg : A ) : R
  override def visit(  p : PPtVal, arg : A ) : R

  override def visit(  p : PPtDrop, arg : A ) : R
  override def visit(  p : PPtInject, arg : A ) : R

  override def visit(  p : PPtOutput, arg : A ) : R

  override def visit(  p : PPtInput, arg : A ) : R
  override def visit(  p : PPtMatch, arg : A ) : R
  override def visit(  p : PPtNew, arg : A ) : R
  override def visit(  p : PPtConstr, arg : A ) : R
  override def visit(  p : PPtPar, arg : A ) : R

  /* CPattern */
  override def visit(  p : CPtVar, arg : A ) : R
  override def visit(  p : CPtQuote, arg : A ) : R
  /* PatternBind */
  override def visit(  p : PtBind, arg : A ) : R
  /* PatternPatternMatch */
  override def visit(  p : PtBranch, arg : A ) : R
  /* ValPattern */
  override def visit(  p : VPtStruct, arg : A ) : R
}
