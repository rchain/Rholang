// -*- mode: Scala;-*- 
// Filename:    Roselang.scala 
// Authors:     luciusmeredith                                                    
// Creation:    Thu Feb  2 14:34:16 2017 
// Copyright:   See site license 
// Description: 
// ------------------------------------------------------------------------

import coop.rchain.lib.term._
import coop.rchain.lib.zipper._

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
  def T() : Context[Either[String,String]] = Top()
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

  def theTupleSpaceVar : String
  def TS() = V( theTupleSpaceVar )
  def H() = HV( theCtxtVar )

  /* The signature of the basic compilation action is 
   * 
   *      def visit[T]( p : T, arg : A ) : R
   * 
   * Where T is the type of expression being compiled and arg is the
   * context into which the result of the compilation will be placed.
   * For example, the compilation of the Nil process is the rosette
   * expression #niv (no intrinsic value). This expression will be
   * placed into the context represented by arg.
   * 
   * The compilation process is made completely regular
   * by certain embeddings. This means we have a single data type for
   * both the context, A and the result R. This regularity ends up
   * being forced by how the visitor pattern works together with
   * certain coherence requirements on all the bodies of the visit
   * method definitions. The embeddings are as follows: 
   * 
   *   1. every expression e lifts to a tree, * t = unit( e ),
   *      which is just e regarded as a tree; 
   *   2. every tree t lifts to a location, l = L( t, T() );
   *   3. every context c can be lifted to a location l = L( V( "*H*" ), c )
   *   4. the composition of context with tree can be uniquely lifted
   *      to a composition of locations of the form
   *      l1 = L( V( "*H*" ), c )
   *      l2 = L( t, T() )
   *      (See the combine method above.)
   * 
   *  So every visit body will be of the form:
   * 
   *     combine( 
   *       arg,
   *       ( context( p ) /: p.parts )( 
   *          { 
   *             ( acc, e ) => {
   *                combine( acc, visit( e, L( V( "*H*" ), T() ) ) ) 
   *             }
   *          }
   *       )
   *     )
   * 
   *  where p.parts stands in for accessing the components of the
   *  expression p. 
   * 
   *  This folds over the parts accumulating the results of compiling
   *  the sub-expressions of p, and then placing them into the right
   *  piece of the compilation p, and then finally places the result
   *  of the fold into the context supplied by arg. Of course, p is
   *  not generally a collection of its parts and so the access of
   *  of the parts of p will be specific to the structure of the
   *  expression, p. Likewise, the combination of the results of the
   *  compilation of the components of p will be more specific than a
   *  fold. However, this gives the general intuition behind how this
   *  algorithm works. Furthermore, it works generally for any CFL.
   * 
   *  This method favors regularity and ease of reasoning over
   *  efficiency. However, it is vastly more efficient than the
   *  parser combinators method provided out of the box by Scala as
   *  testing on a parser for prolog revealed in production.
   */

  /* Contr */
  override def visit( p : DContr, arg : A ) : R

  /* Proc */
  def visit( p : Proc, arg : A ) : R

  override def visit(  p : PNil, arg : A ) : R = {    
    combine( arg, Some( L( G( "#niv" ), T() ) ), Some( H() ) )
  }
  override def visit(  p : PValue, arg : A ) : R
  override def visit(  p : PVar, arg : A ) : R
  override def visit(  p : PDrop, arg : A ) : R
  override def visit(  p : PInject, arg : A ) : R
  override def visit(  p : PLift, arg : A ) : R
  override def visit(  p : PInput, arg : A ) : R = {
    import scala.collection.JavaConverters._

    val optLetLoc : Option[Location[Either[String,String]]] =
      Some( L( B( "let" )( V( theCtxtVar ) ), T() ) )

    val letBindings =
      ( optLetLoc /: p.listbind_.asScala.map( visit( _, Some( H() ) ) ) )(
        {
          ( acc, e ) => {
            for( nLoc <- combine( acc, e, Some( H() ) ) ) yield {
              val Location( StrTermCtxtBr( op, subterms ), ctxt ) = nLoc
              L( B( op )( ( subterms ++ List( V( theCtxtVar ) ) ):_* ), ctxt )
            }
          }
        }
      )

    val proc = visit( p.proc_, Some( L( V( theCtxtVar ), T() ) ) )

    combine(
      arg, 
      combine( letBindings, proc, Some( H() ) ), Some( H() ) 
    )
  }
  override def visit(  p : PChoice, arg : A ) : R
  override def visit(  p : PMatch, arg : A ) : R
  override def visit(  p : PNew, arg : A ) : R
  override def visit(  p : PConstr, arg : A ) : R
  override def visit(  p : PPar, arg : A ) : R

  /* Chan */
  def visit(  p : Chan, arg : A ) : R
  override def visit(  p : CVar, arg : A ) : R
  override def visit(  p : CQuote, arg : A ) : R
  /* Bind */
  def visit( b : Bind, arg : A ) : R
  override def visit(  p : InputBind, arg : A ) : R = {
    combine( 
      arg, 
      (for(
        Location( chanTerm : StrTermCtxt, _ ) <- visit( p.chan_, Some( H() ) );
        Location( ptrnTerm : StrTermCtxt, _ ) <- visit( p.cpattern_, Some( H() ) )
      ) yield { L( B( "consume" )( TS(), chanTerm, ptrnTerm ), Top() ) }),
      Some( H() )
    )
  }
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
  /* Pattern */
  def visit( p : CPattern, arg : A ) : R
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
