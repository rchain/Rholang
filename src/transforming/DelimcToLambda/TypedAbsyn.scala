package transforming.DelimcToLambda

import parsing.delimc.{Absyn => Untyped}

/**
  * Created by weeeeeew on 2016-06-09.
  */
object TypedAbsyn {
  case class Ident[A](ident : String)

  sealed trait CC[A]

  sealed trait Expr[A] extends Untyped.Expr
  case class EAbs[A, B](arg: Ident[A], expr : Expr[B]) extends Untyped.EAbs(arg.ident, expr) with Expr[A => B]
  case class EApp[A, B](abs: Expr[A => B], expr: Expr[A]) extends Untyped.EApp(abs, expr) with Expr[B]
  case class EVar[A](ident: Ident[A]) extends Untyped.EVar(ident.ident) with Expr[A]
  case class EVal[A](value: Value[A]) extends Untyped.EVal(newValue(value)) with Expr[A]
  case class EReturn[A](expr: Expr[CC[A]]) extends Untyped.EReturn(expr) with Expr[CC[A]]
  case class EBind[A, B](expr1: Expr[CC[A]], expr2: Expr[A => CC[B]]) extends Untyped.EBind(expr1, expr2) with Expr[CC[B]]
  case class ETuple[A, B](expr1: Expr[A], expr2: Expr[B]) extends Untyped.ETuple(expr1, expr2) with Expr[(A,B)]

  sealed trait Value[A] extends Untyped.Value
  case class VInt(integer: Integer) extends Untyped.VInt(integer) with Value[Integer]
  case class VString(string: String) extends Untyped.VString(string) with Value[String]

  def newValue[A](value: A) = value match {
    case i : Integer => new Untyped.VInt(i)
    case s : String  => new Untyped.VString(s)
  }


  def typeCheck(expr: Untyped.Expr) : Expr = expr match {
    case e : Untyped.EVal => EVal(typeCheck(e.value_))
    case e : Untyped.EAbs => ??? // EAbs(Ident(e.ident_), typeCheck(e.expr_))
    case e : Untyped.EApp => ??? // EApp(typeCheck(e.expr_1), typeCheck(e.expr_2))

  }

  def typeCheck[A](value: Untyped.Value) : Value[A] = value match {
    case i : Untyped.VInt    => ??? // VInt(i.integer_)
    case s : Untyped.VString => ??? // VString(s.string_)
  }


}
