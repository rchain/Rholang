package rholang.transforming.DelimcToLambda

import rholang.parsing.lambda.{Absyn => Untyped}


/**
  * Created by weeeeeew on 15/06/16.
  */
object TypedLambda {
  //Type mappings
  // def typeName[Integer] : String           = "Integer"
  // def typeName[Char]    : String           = "Char"
  def typeName[X]: String = "String"


  //Typed expressions
  sealed trait Expr[A] extends Untyped.Expr

  case class EAbs[A,B](varName: String, varType: Type[A], expr: Expr[B], absType: Type[A => B])
    extends Untyped.EAbs(varName, varType, expr, absType) with Expr[A => B]

  case class EApp[A,B](expr1: Expr[A => B], expr2: Expr[A], typ: Type[B])
    extends Untyped.EApp(expr1, expr2, typ) with Expr[B]

  case class EVar[A](name: String, typ: Type[A])
    extends Untyped.EVar(name, typ) with Expr[A]

  case class EVal[A](value: Value[A])
    extends Untyped.EVal(value) with Expr[A]

  case class ETuple[A](tuple: Tuple[A], tupType: Type[A])
    extends Untyped.ETuple(tuple, tupType) with Expr[A]


  //Typed tuples
  sealed trait Tuple[A] extends Untyped.Tuple

  case class Tuple2[A,B](exprA: Expr[A], exprB: Expr[B])
    extends Untyped.Tuple2(exprA, exprB) with Tuple[(A,B)]

  case class Tuple3[A,B,C](exprA: Expr[A], exprB: Expr[B], exprC: Expr[C])
    extends Untyped.Tuple3(exprA, exprB, exprC) with Tuple[(A,B,C)]


  //Typed values
  sealed trait Value[A] extends Untyped.Value

  case class VInt(integer: Integer)
    extends Untyped.VInt(integer) with Value[Integer]

  case class VString(string: String)
    extends Untyped.VString(string) with Value[String]


  //Typed types
  sealed trait Type[A] extends Untyped.Type

  case class TSimple[A]()
    extends Untyped.TSimple(typeName[A]) with Type[A]

  case class TTuple[A](tType: TType[A])
    extends Untyped.TTuple(tType) with Type[A]

  case class TFun[A,B](tFrom: Type[A], tTo: Type[B])
    extends Untyped.TFun(tFrom, tTo) with Type[A => B]


  //Typed tuple types
  sealed trait TType[A] extends Untyped.TType

  case class TType2[A,B](tA: Type[A], tB: Type[B])
    extends Untyped.TType2(tA, tB) with TType[(A,B)]

  case class TType3[A,B,C](tA: Type[A], tB: Type[B], tC: Type[C])
    extends Untyped.TType3(tA, tB, tC) with TType[(A,B,C)]
}
