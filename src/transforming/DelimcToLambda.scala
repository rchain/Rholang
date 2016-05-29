package transforming

import parsing.delimc.Absyn.{EPushPrompt, _}

/**
  * Created by weeeeeew on 25/05/16.
  */
object DelimcToLambda {
  type VMState = (Either[Expr,Value],DContext,Sequence,Prompt)

  def translate(expr: Expr): Expr = reduce(Right(expr), new DCHole(), new SEmpty(), new PInt(0))._1

  def reduce(expr: Either[Expr,Value], dContext: DContext, sequence: Sequence, prompt: Prompt): VMState = {
    expr match {
      case e : EPushPrompt =>
        (e.expr_1, fillHole(dContext, new DCPushPrompt(new DCEmpty(), e.expr_2)), sequence, prompt)
      case e : EWithSubCont =>
        (e.expr_1, fillHole(dContext, new DCWithSubCont(new DCEmpty(), e.expr_2)), sequence, prompt)
      case e : DCWi =>

    }
  }

  def fillHole(oldContext: DContext, fillingContext: DContext): DContext =
    oldContext match {
      case dc : DCEmpty => fillingContext
      case dc : DCExpr  => new DCExpr(fillHole(dc.dcontext_, fillingContext), dc.expr_)
      case other        => oldContext
    }

  def fillHole(dContext: DContext, value: Value): Expr =
    dContext match {
      case dc : DCEmpty       => value // should never happen?
      case dc : DCExpr        => new EApp(fillHole(dc.dcontext_, value), dc.expr_)
      case dc : DCValue       => new EApp(dc.value_, fillHole(dc.dcontext_, value))
      case dc : DCPushPrompt  => new EPushPrompt(fillHole(dc.dcontext_, value), dc.expr_)
      case dc : DCPushSubCont => new EPushSubCont(fillHole(dc.dcontext_, value), dc.expr_)
      case dc : DCWithSubCont => new EWithSubCont(fillHole(dc.dcontext_, value), dc.expr_)
      case dc : DCWithSubContPrompt => new EWithSubCont(dc.prompt_, fillHole(dc.dcontext_, value))
    }
}
