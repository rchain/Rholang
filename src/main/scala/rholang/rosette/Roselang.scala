// -*- mode: Scala;-*- 
// Filename:    Roselang.scala 
// Authors:     luciusmeredith                                                    
// Creation:    Thu Feb  2 14:34:16 2017 
// Copyright:   See site license 
// Description: 
// ------------------------------------------------------------------------

package coop.rchain.rho2rose

import coop.rchain.lib.term._
import coop.rchain.lib.zipper._

import coop.rchain.syntax.rholang._
import coop.rchain.syntax.rholang.Absyn._

trait StrTermNavigation extends TermNavigation[String,Either[String,String],String]
trait StrTermMutation extends TermMutation [String,Either[String,String],String]
trait StrTermZipperComposition extends TermZipperComposition[String,Either[String,String],String]
trait StrTermSubstitution extends TermSubstitution[String,Either[String,String],String]

object StrTermCtorAbbrevs {
  type StrTermCtxt = TermCtxt[String,Either[String,String],String] with Factual
  def V( v : String ) : StrTermCtxt = StrTermPtdCtxtLf( Right( Left( v ) ) )
  def K( v : String ) : StrTermCtxt = StrTermPtdCtxtLf( Right( Right( v ) ) )
  def G( v : String ) : StrTermCtxt = StrTermPtdCtxtLf( Left( v ) )
  def B( v : String )( terms : StrTermCtxt* ) = StrTermPtdCtxtBr( v, terms.toList )
}

object StrZipAbbrevs {
  type ValOrVar = Either[String,Either[String,String]]
  type LocVorV = Location[ValOrVar]
  def L( term : StrTermCtorAbbrevs.StrTermCtxt, ctxt : Context[ValOrVar] ) : LocVorV = Location( term, ctxt )   
  def HV( cv : String ) : LocVorV = 
    Location( StrTermCtorAbbrevs.K( cv ), Top[ValOrVar]() )
  def T() : Context[ValOrVar] = Top()
}

object VisitorTypes {
  type A = Option[StrZipAbbrevs.LocVorV]
  type R = Option[StrZipAbbrevs.LocVorV]    
}

object S2SImplicits {
  implicit def asLoc( 
    term : StrTermCtorAbbrevs.StrTermCtxt
  ) : StrZipAbbrevs.LocVorV = StrZipAbbrevs.L( term, StrZipAbbrevs.T() )
  implicit def asR( 
    loc : StrZipAbbrevs.LocVorV
  ) : VisitorTypes.R = Some( loc )
  implicit def asR(
    term : StrTermCtorAbbrevs.StrTermCtxt
  ) : VisitorTypes.R = asR( asLoc( term ) )
}

object CompilerExceptions {
  trait CompilerException
  trait SyntaxException
  trait SemanticsException

  case class UnexpectedContractType(
    c : Contr
  ) extends Exception( s"$c found in unexpected context" )
      with CompilerException with SyntaxException
  case class NoComprehensionBindings(
    p : PInput 
  ) extends Exception( s"$p has no bindings" )
      with CompilerException with SyntaxException
  case class UnexpectedBindingType(
    b : Bind
  ) extends Exception( s"$b found in unexpected context" )
      with CompilerException with SyntaxException
  case class UnexpectedBranchType(
    b : CBranch
  ) extends Exception( s"$b found in unexpected context" )
      with CompilerException with SyntaxException
  case class FailedQuotation(
    b : AnyRef
  ) extends Exception( s"$b found in unexpected context" )
      with CompilerException with SyntaxException
  case class UnexpectedCombination(
    xLoc : StrZipAbbrevs.LocVorV,
    yLoc : StrZipAbbrevs.LocVorV
  ) extends Exception( s"attempting to combine: $xLoc with $yLoc" )
      with CompilerException
}

object ComprehensionOps {
  val _map = "map"
  val _unit = "unit"
  val _mult = "mult"
  val _join = "flatMap"
  val _filter = "filter"
}

object RosetteOps {
  val _abs = "proc"
  val _defActor = "defActor"
  val _method = "method"
  val _produce = "produce"
  val _block = "block"
  val _quote = "Q"
  val _rx = "RX"
  val _run = "run"
  val _compile = "compile"
}

trait StrFoldCtxtVisitor
extends FoldVisitor[VisitorTypes.R,VisitorTypes.A] {
  def zipr : StrTermNavigation with StrTermMutation with StrTermZipperComposition
  def theCtxtVar : String

  def wrap( context : VisitorTypes.R ) : VisitorTypes.R = context
  def leaf( context : VisitorTypes.R ) : VisitorTypes.R = wrap( context )    

  override def combine(
    y : VisitorTypes.R,
    x : VisitorTypes.R,
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
	  case Location( StrTermPtdCtxtLf( Right( Left( v ) ) ), Top( ) ) => {
            throw new CompilerExceptions.UnexpectedCombination( xLoc, yLoc )
          }
          case Location( StrTermPtdCtxtLf( Right( Right( v ) ) ), Top( ) ) => xLoc
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
              case Location( StrTermPtdCtxtLf( Right( Left( v ) ) ), Top() ) => {
                throw new CompilerExceptions.UnexpectedCombination( xLoc, yLoc )
              }
	      case Location( StrTermPtdCtxtLf( Right( Right( v ) ) ), Top() ) => {
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
		Location[StrZipAbbrevs.ValOrVar](
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
  import S2SImplicits._
  import CompilerExceptions._
  import ComprehensionOps._
  import RosetteOps._

  def theTupleSpaceVar : String
  def TS() = V( theTupleSpaceVar )
  def CH() = V( theCtxtVar )
  def H() = HV( theCtxtVar )
  def Here() = Some( HV( theCtxtVar ) )
  def Fresh() = {
    val uuidComponents = java.util.UUID.randomUUID.toString.split( "-" )
    uuidComponents( uuidComponents.length - 1 )
  }

  def isTopLevel( r : R ) : Boolean = {
    r match {
      case Some( Location( StrTermPtdCtxtLf( Left( v ) ), Top() ) ) if v.equals( theCtxtVar ) => {
        true
      }
      case _ => false
    }
  }

  /* TODO : rewrite this to be amenable to tail recursion elimination */
  def doQuote( rexpr : R ) : R = {
    for( Location( expr : StrTermCtxt, _ ) <- rexpr )
    yield {
      expr match {
        case leaf : StrTermPtdCtxtLf => {
          L( B( _quote )( leaf ), Top() )
        }
        case StrTermPtdCtxtBr( op, subterms ) => {
          val qterms = subterms.map( 
            { 
              ( term ) => { 
                (for( Location( qterm : StrTermCtxt, _ ) <- doQuote( term ) )
                yield { qterm }).getOrElse( throw new FailedQuotation( term ) )
              }
            }
          )
          L( B( _rx )( (List( B( _quote )( V( op ) ) ) ++ qterms):_* ), Top() )
        }
      }
    }
  }

  def combine( ctxt1 : A, ctxt2 : R ) : R =
    combine( ctxt1, ctxt2, Here() )

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
  def visit( p : Contr, arg : A ) : R = {
    p match {
      case dcontr : DContr => visit( dcontr, arg )
      case _ => throw new UnexpectedContractType( p )
    }
  }
  override def visit( p : DContr, arg : A ) : R = {
    import scala.collection.JavaConverters._
    /*
     * [| contract <Name>( <formals> ) = { <body> } |]( t )
     * =
     * (defActor <Name>Contract 
     *   (method (<Name> [| formals |]( t )) [| body |]))
     */
    combine(
      arg,
      (for( Location( pTerm : StrTermCtxt, _ ) <- visitDispatch( p.proc_, Here() ) )
      yield {
        val formals =
          ( List[StrTermCtxt]() /: p.listcpattern_.asScala.toList )(
            {
              ( acc, e ) => {
                visitDispatch( e, Here() ) match {
                  case Some( Location( frml : StrTermCtxt, _ ) ) => {
                    acc ++ List( frml )
                  }
                  case None => {
                    acc
                  }
                }
              }
            }
          )
        val methodTerm =
          B( _method )( B( p.name_ )( formals:_* ), pTerm )
        val actorTerm =
          B( _defActor )( G( s"""${p.name_}Contract""" ), methodTerm )

        L( actorTerm, Top() )
      })
    )
  }

  /* Proc */
  def visitDispatch( p : Proc, arg : A ) : R = {
    p match {
      case pNil : PNil => visit( pNil, arg )
      case pVal : PValue => visit( pVal, arg )
      case pDrop : PDrop => visit( pDrop, arg )
      case pInject : PInject => visit( pInject, arg )
      case pLift : PLift => visit( pLift, arg )
      case pFoldL : PFoldL => visit( pFoldL, arg )
      case pFoldR : PFoldR => visit( pFoldR, arg )
      case pInput : PInput => visit( pInput, arg )
      case pChoice : PChoice => visit( pChoice, arg )
      case pMatch : PMatch => visit( pMatch, arg )
      case pNew : PNew => visit( pNew, arg )
      case pConstr : PConstr => visit( pConstr, arg )
      case pPar : PPar => visit( pPar, arg )
    }
  }

  override def visit(  p : PNil, arg : A ) : R = {    
    combine( arg, Some( L( G( "#niv" ), T() ) ) )
  }
  override def visit(  p : PValue, arg : A ) : R
  override def visit(  p : PVar, arg : A ) : R
  override def visit(  p : PDrop, arg : A ) : R = {
    /*
     *  Note that there are at least two different approaches to the
     *  lift/drop semantics. One is to compile the actuals supplied to
     *  lift to the target language and then let the target language
     *  supply the execution semantics for the drop. Dual to this one
     *  is to defer the compilation of the actuals to lift and then
     *  compile at drop time. This is more portable, though
     *  potentially less efficient. Another technique that comes into
     *  play is to calculate the hash of (each of) the actuals to lift
     *  and store the code at a name that is a function of the
     *  hash. Then send the hashes. Then at a drop the hash is used to
     *  recover the code. This technique can be composed with either
     *  of the other two techniques.
     */
    combine( arg, 
      ( p.chan_ match {
        case quote : CQuote => {
          visitDispatch( quote.proc_, Here() )
        }
        case v : CVar => {
          Some( L( B( _run )( B( _compile )( V( v.var_ ) ) ), Top() ) )
        }
      } )
    )    
  }
  override def visit(  p : PInject, arg : A ) : R
  override def visit(  p : PLift, arg : A ) : R = {
    import scala.collection.JavaConverters._
    /*
     *  [| x!( P1, ..., PN ) |]( t )
     *  =
     *  ( produce t [| x ]( t ) `(,[| P1 |]( t )) ... `(,[| PN |]( t )) )
     */
    val actls =
      ( List[StrTermCtxt]() /: p.listproc_.asScala.toList )(
        {
          ( acc, e ) => {
            visitDispatch( e, Here() ) match {
              case Some( Location( pTerm : StrTermCtxt, _ ) ) => {
                doQuote( pTerm ) match {
                  case Some( Location( qTerm : StrTermCtxt, _ ) ) =>
                    acc ++ List( qTerm )
                  case None => acc
                }                
              }
              case None => acc
            }
          }
        }
      )        

    combine(
      arg,
      Some( L( B( _produce )( (List( TS ) ++ actls):_* ), Top() ) )
    )
  }
  override def visit(  p : PInput, arg : A ) : R = {
    import scala.collection.JavaConverters._

    def forToMap( binding : Bind, proc : Proc ) : R = {
      binding match {
        case inBind : InputBind => {
          for(
            // [[ chan ]] is chanTerm
            Location( chanTerm : StrTermCtxt, _ ) <- visitDispatch( inBind.chan_, Here() );
            // [[ ptrn ]] is ptrnTerm
            Location( ptrnTerm : StrTermCtxt, _ ) <- visitDispatch( inBind.cpattern_, Here() );
            // [[ P ]] is bodyTerm
            Location( bodyTerm : StrTermCtxt, _ ) <- visitDispatch( proc, Here() )
          ) yield {
            // ( map [[ chan ]] proc [[ ptrn ]] [[ P ]] )
            L( B( _map )( chanTerm, B( _abs )( ptrnTerm, bodyTerm ) ), T() )
          }
        }
        case cndInBind : CondInputBind => {
          for(
            // [[ chan ]] is chanTerm
            Location( chanTerm : StrTermCtxt, _ ) <- visitDispatch( cndInBind.chan_, Here() );
            // [[ ptrn ]] is ptrnTerm
            Location( ptrnTerm : StrTermCtxt, _ ) <- visitDispatch( cndInBind.cpattern_, Here() );
            Location( filterTerm : StrTermCtxt, _ ) <- visitDispatch( cndInBind.proc_, Here() );
            // [[ P ]] is bodyTerm
            Location( bodyTerm : StrTermCtxt, _ ) <- visitDispatch( proc, Here() )
          ) yield {
            // ( map [[ chan ]] proc [[ ptrn ]] [[ P ]] )
            L( 
              B( _filter )(
                B( _map )( chanTerm, B( _abs )( ptrnTerm, bodyTerm ) ),
                filterTerm
              ), 
              T()
            )
          }
        }
        case _ => {
          throw new UnexpectedBindingType( binding )
        }
      }
    }

    combine( 
      arg,
      p.listbind_.asScala.toList match {
        case Nil => {
          throw new NoComprehensionBindings( p )
        }
        case binding :: Nil => {
          /*
           *  [[ for( ptrn <- chan )P ]]
           *  =
           *  ( map [[ chan ]] proc [[ ptrn ]] [[ P ]] )
           */
          forToMap( binding, p.proc_ )
        }
        case bindings => {
          /*
           *  [[ for( ptrn <- chan; bindings )P ]]
           *  =
           *  ( flatMap [[ chan ]] proc [[ ptrn ]] [[ for( bindings )P ]] )
           */

          // Reversing the bindings allows for a tail recursion that
          // can be optimized by hand to a fold. This means the
          // compiler doesn't blow the stack on a deep set of bindings
          // in exchange for performing the reverse operation.
          val lastBinding :: rbindings = bindings.reverse

          ( forToMap( lastBinding, p.proc_ ) /: rbindings )(
            {
              ( acc, e ) => {
                e match {
                  case inBind : InputBind => {
                    combine(
                      acc,
                      for(
                        // [[ chan ]] is chanTerm
                        Location( chanTerm : StrTermCtxt, _ ) <- visitDispatch( inBind.chan_, Here() );
                        // [[ ptrn ]] is ptrnTerm
                        Location( ptrnTerm : StrTermCtxt, _ ) <- visitDispatch( inBind.cpattern_, Here() );
                        Location( rbindingsTerm : StrTermCtxt, _ ) <- acc
                      ) yield {
                        // ( flatMap [[ chan ]] proc [[ ptrn ]] [[ for( bindings )P ]] )
                        L( B( _join )( chanTerm, B( _abs )( ptrnTerm, rbindingsTerm ) ), T() )
                      }
                    )
                  }
                  case cndInBind : CondInputBind => {
                    for(
                      // [[ chan ]] is chanTerm
                      Location( chanTerm : StrTermCtxt, _ ) <- visitDispatch( cndInBind.chan_, Here() );
                      // [[ ptrn ]] is ptrnTerm
                      Location( ptrnTerm : StrTermCtxt, _ ) <- visitDispatch( cndInBind.cpattern_, Here() );
                      Location( filterTerm : StrTermCtxt, _ ) <- visitDispatch( cndInBind.proc_, Here() );
                      Location( rbindingsTerm : StrTermCtxt, _ ) <- acc
                    ) yield {
                      // ( map [[ chan ]] proc [[ ptrn ]] [[ P ]] )
                      L(
                        B( _filter )(
                          B( _map )( chanTerm, B( _abs )( ptrnTerm, rbindingsTerm ) ),
                          filterTerm
                        ),
                        T()
                      )
                    }
                  }
                  case _ => {
                    throw new UnexpectedBindingType( e )
                  }
                }
              }
            }
          )
        }
      }
    )
  }

  override def visit(  p : PNew, arg : A ) : R = {
    import scala.collection.JavaConverters._
    val newVars = p.listvar_.asScala.toList
    combine( 
      arg,
      (for( Location( pTerm : StrTermCtxt, _ ) <- visitDispatch( p.proc_, Here() ) )
      yield {
        val newBindings = newVars.map( { ( v ) => { B( v )( V( Fresh() ) ) } } )
        L( B( "let" )( (newBindings ++ List( pTerm )):_* ), Top() )
      })
    )
  }
  override def visit(  p : PChoice, arg : A ) : R = {
    import scala.collection.JavaConverters._

    def cBranchToParPair( b : CBranch ) = {
      b match {
        case branch : Choice => {
          // bverity = 1, babsurdity = 0
          val ( bverity, babsurdity ) = 
            ( 
              new PValue( new VQuant( new QInt( 1 ) ) ), 
              new PValue( new VQuant( new QInt( 0 ) ) ) 
            )

          // bmsg <- bchan
          val bmsgVStr = Fresh()
          val ( bmsg, bchan ) = 
            ( new CPtVar( new VarPtVar( bmsgVStr ) ), new CVar( Fresh() ) )
          val bbind = new InputBind( bmsg, bchan )

          // lmsg <- lchan
          val lmsgVStr = Fresh()
          val ( lmsg, lchan ) = 
            ( new CPtVar( new VarPtVar( lmsgVStr ) ), new CVar( Fresh() ) )
          val lbind = new InputBind( lmsg, lchan )

          // case 1 => P_i
          val bvericase =
            new PatternMatch( new PPtVal( new VPtInt( 1 ) ), branch.proc_ )

          val balertActls = new ListProc()
          balertActls.add( babsurdity )

          // case 0 => lchan!( 0 )
          val babsucase =
            new PatternMatch( 
              new PPtVal( new VPtInt( 0 ) ), 
              new PLift( lchan, balertActls ) 
            )
         
          val bcases = new ListPMBranch()
          bcases.add( bvericase )
          bcases.add( babsucase )

          val bsatActls = new ListProc()
          bsatActls.add( new PValue( new VQuant( new QInt( 1 ) ) ) )

          val blocks = new ListBind()
          blocks.add( bbind )
          blocks.add( lbind )

          val bmatch = new PMatch( new PVar( lmsgVStr ), bcases )

          val bpair = 
            new PPar(
              // for( binding_i ){ bchan!( 1 ) }
              new PInput( branch.listbind_, new PLift( bchan, bsatActls ) ),
              // for( bmsg <- bchan; lmsg <- lchan ){
              //   match lmsg with
              //    case 1 => P_i
              //    case 0 => lchan!( 0 )
              // }          
              new PInput( blocks, bmatch )
            )

          ( bchan, bpair )
        }
        case _ => {
          throw new UnexpectedBranchType( b )
        }
      }
    }

    p.listcbranch_.asScala.toList match {
      // select {} = Nil
      case Nil => {
        combine( arg, Some( L( G( "#niv" ), T() ) ) )
      }
      // select { case bindings => P } = for( bindings )P
      case ( branch : Choice ) :: Nil => {
        visit( new PInput( branch.listbind_, branch.proc_ ), arg )
      }
      /*
       * select { case bindings1 => P1; ...; case bindingsN => PN }
       * =
       * new b1, ..., bN, lock in 
       *   lock!( true )
       *   | for( bindings1 ){ b1!( true ) } 
       *   | for( b <- b1; l <- lock ){ 
       *      match l with 
       *       case true => P1; 
       *       case false => lock!( false )
       *     }
       *    ...
       *   | for( bindingsN ){ bN!( true ) } 
       *   | for( b <- b1; l <- lock ){ 
       *      match l with 
       *       case true => P1; 
       *       case false => lock!( false )
       *     }
       */
      case branches => {
        val ( bvars, bpar :: rbpars ) = branches.map( cBranchToParPair ).unzip
        val bigbpar = ( bpar /: rbpars )( { ( acc, e ) => { new PPar( acc, e ) } } )
        val bnewVars = new ListVar()
        bvars.map( { ( bvar ) => { bnewVars.add( bvar.var_ ) } } )

        visit( new PNew( bnewVars, bigbpar ), arg )
      }
    }    
  }

  override def visit(  p : PMatch, arg : A ) : R
  //override def visit(  p : PNew, arg : A ) : R
  override def visit(  p : PConstr, arg : A ) : R = {
    import scala.collection.JavaConverters._
    /*
     *  [| <Name>( P1, ..., PN ) |]( t )
     *  =
     *  ( <Name> [| P1 |]( t ) ... [| PN |]( t ) )
     */
    val actls =
      ( List[StrTermCtxt]() /: p.listproc_.asScala.toList )(
        {
          ( acc, e ) => {
            visitDispatch( e, Here() ) match {
              case Some( Location( pTerm : StrTermCtxt, _ ) ) => acc ++ List( pTerm )
              case None => acc
            }
          }
        }
      )        

    combine(
      arg,
      Some( L( B( p.name_ )( (List( TS ) ++ actls):_* ), Top() ) )
    )
  }
  override def visit(  p : PPar, arg : A ) : R = {
    /*
     * [| P1 | P2 |]( t ) 
     * =
     * ( block [| P1 |]( t ) [| P2 |]( t ) )
     */
    combine(
      arg,
      for( 
        Location( pTerm1 : StrTermCtxt, _ ) <- visitDispatch( p.proc_1, Here() );
        Location( pTerm2 : StrTermCtxt, _ ) <- visitDispatch( p.proc_2, Here() )
      ) yield {
        L( B( _block )( pTerm1, pTerm2 ), Top() )
      }
    )
  }

  /* Chan */
  def visitDispatch(  p : Chan, arg : A ) : R = {
    p match {
      case cVar : CVar => visit( cVar, arg )
      case cQuote : CVar => visit( cQuote, arg )
    }
  }
  override def visit(  p : CVar, arg : A ) : R = {
    combine( arg, Some( L( V( p.var_ ), T() ) ) )
  }
  override def visit(  p : CQuote, arg : A ) : R
  /* Bind */
  def visit( b : Bind, arg : A ) : R
  override def visit(  p : InputBind, arg : A ) : R = {
    arg match {
      // [[ P ]] is proc
      case Some( Location( proc : StrTermCtxt, Top() ) ) => {
        for(
          // [[ chan ]] is chanTerm
          Location( chanTerm : StrTermCtxt, _ ) <- visitDispatch( p.chan_, Here() );
          // [[ ptrn ]] is ptrnTerm
          Location( ptrnTerm : StrTermCtxt, _ ) <- visitDispatch( p.cpattern_, Here() )
        ) yield {
          // ( map [[ chan ]] proc [[ ptrn ]] [[ P ]] )
          L( B( _map )( chanTerm, B( _abs )( ptrnTerm, proc ) ), T() )
        }
      }
      case _ => { // this is a little too optimistic or forgiving
        // should ensure that arg is a reasonable context to be really safe
        for(
          // [[ chan ]] is chanTerm
          Location( chanTerm : StrTermCtxt, _ ) <- visitDispatch( p.chan_, Here() );
          // [[ ptrn ]] is ptrnTerm
          Location( ptrnTerm : StrTermCtxt, _ ) <- visitDispatch( p.cpattern_, Here() );
          Location( rbindingsTerm : StrTermCtxt, _ ) <- arg
        ) yield {
          // ( flatMap [[ chan ]] proc [[ ptrn ]] [[ for( bindings )P ]] )
          L( B( _join )( chanTerm, B( _abs )( ptrnTerm, rbindingsTerm ) ), T() )
        }
      }
    }
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
  override def visit(  p : QInt, arg : A ) : R = {
    combine(
      arg,
      L( G( s"""${p.integer_}"""), Top() )
    )
  }
  override def visit(  p : QDouble, arg : A ) : R = {
    combine(
      arg,
      L( G( s"""${p.double_}"""), Top() )
    )
  }
  /* Entity */
  override def visit(  p : EChar, arg : A ) : R = {
    combine(
      arg,
      L( G( s"""'${p.char_}'"""), Top() )
    )
  }
  override def visit( p : QTrue, arg : A) : R = {
    combine(
      arg,
      L( G( s"""#t"""), Top() )
    )
  }
  override def visit( p : QFalse, arg : A) : R = {
    combine(
      arg,
      L( G( s"""#f"""), Top() )
    )
  }
  override def visit(  p : EStruct, arg : A ) : R
  override def visit(  p : ECollect, arg : A ) : R
  /* Struct */
  override def visit(  p : StructConstr, arg : A ) : R
  /* Collect */
  override def visit(  p : CString, arg : A ) : R = {
    combine(
      arg,
      L( G( s""""${p.string_}""""), Top() )
    )
  }
  /* Pattern */
  def visitDispatch( p : CPattern, arg : A ) : R = {
    p match {
      case cPtVar : CPtVar => visit( cPtVar, arg )
      case cPtQuote : CPtQuote => visit( cPtQuote, arg )
      case cValPtrn : CValPtrn => visit( cValPtrn, arg )
    }
  }
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
