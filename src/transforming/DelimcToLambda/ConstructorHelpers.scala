package transforming.DelimcToLambda

import parsing.lambda.Absyn.{EAbs, EApp, EVar, Expr}

/**
  * Created by weeeeeew on 2016-06-09.
  */
object ConstructorHelpers {
  // Helper functions to reduce syntactic overhead
  def lVar(id : String) : Expr = new EVar(id)

  def lAbs(id   : String, expr : Expr)   : Expr = new EAbs(id,   expr)
  def lAbs(id_1 : String, id_2 : String) : Expr =    lAbs(id_1, lVar(id_2))

  def lApp(expr_1 : Expr,   expr_2 : Expr)   : Expr = new EApp(expr_1,     expr_2)
  def lApp(id     : String, expr   : Expr)   : Expr =     lApp(lVar(id),   expr)
  def lApp(expr   : Expr,   id     : String) : Expr =     lApp(expr,       lVar(id))
  def lApp(id_1   : String, id_2   : String) : Expr =     lApp(lVar(id_1), lVar(id_2))
}
