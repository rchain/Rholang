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

trait RholangASTToTerm 
extends FoldVisitor[Option[Location[Either[String,String]]], Option[Location[Either[String,String]]]] {
  def zipr : StrTermNavigation with StrTermMutation with StrTermZipperComposition
  def theCtxtVar : String

  type A = Option[Location[Either[String,String]]]
  type R = Option[Location[Either[String,String]]]

  /* Contr */
  override def visit( p : DContr, arg : A ) : R
/* Proc */
  override def visit(  p : PNil, arg : A ) : R
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
