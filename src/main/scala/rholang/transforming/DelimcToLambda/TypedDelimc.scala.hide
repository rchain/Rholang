package rholang.transforming.DelimcToLambda


import rholang.parsing.delimc.{Absyn => Untyped}

/**
  * Created by weeeeeew on 2016-06-09.
  */
object TypedDelimc {
  //Type mappings
  // def typeName[X]: String           = "Integer"
  // def typeName[X]: String           = "Char"
  def typeName[X]: java.lang.String = "String"

  trait CC[A]
  trait SubCont[A,B]
  type Prompt[A] = Int

  sealed trait TypedExpr[A] extends Untyped.TypedExpr

  case class ETyped[A](expr: Expr[A], typ: Type[A])
    extends Untyped.ETyped(expr, typ) with TypedExpr[A]


  //Typed expressions
  sealed trait Expr[A] extends Untyped.Expr

  case class EAbs[A,B](varName: String, varType: Type[A], expr: TypedExpr[B])
    extends Untyped.EAbs(varName, varType, expr) with Expr[A => B]

  case class EApp[A,B,F](expr1: TypedExpr[F], expr2: TypedExpr[A])
    (implicit p: F =:= (A => B))
    extends Untyped.EApp(expr1, expr2) with Expr[B]

  case class EVar[A](name: String)
    extends Untyped.EVar(name) with Expr[A]

  case class EVal[A](value: Value[A])
    extends Untyped.EVal(value) with Expr[A]

  case class ETuple[A](tuple: Tuple[A])
    extends Untyped.ETuple(tuple) with Expr[A]

  case class EReturn[A](expr: TypedExpr[A])
    extends Untyped.EReturn(expr) with Expr[CC[A]]

  case class EBind[A,B,F,CCA](expr1: TypedExpr[CCA], expr2: TypedExpr[F])
    (implicit p1: CCA =:= CC[A], p2: F =:= (A => CC[B]))
    extends Untyped.EBind(expr1, expr2) with Expr[CC[B]]

  case class ENewPrompt()
    extends Untyped.ENewPrompt with Expr[CC[Prompt[A]] forSome {type A}]

  case class EPushPrompt[PA,CCA,A](expr1: TypedExpr[PA], expr2: TypedExpr[CCA])
    (implicit p1: PA =:= Prompt[A], p2: CCA =:= CC[A])
    extends Untyped.EPushPrompt(expr1, expr2) with Expr[CC[A]]

  case class EWithSubCont[PB,F,A,B](expr1: TypedExpr[PB], expr2: TypedExpr[F])
    (implicit p1: PB =:= Prompt[B], p2: F =:= (SubCont[A,B] => CC[B]))
    extends Untyped.EWithSubCont(expr1, expr2) with Expr[CC[A]]

  case class EPushSubCont[SCAB,CCA,A,B](expr1: TypedExpr[SCAB], expr2: TypedExpr[CCA])
    (implicit p1: SCAB =:= SubCont[A,B], p2: CCA =:= CC[A])
    extends Untyped.EPushSubCont(expr1, expr2) with Expr[CC[B]]

  //Typed tuples
  sealed trait Tuple[+A] extends Untyped.Tuple

  case class Tuple2[A,B](exprA: TypedExpr[A], exprB: TypedExpr[B])
    extends Untyped.Tuple2(exprA, exprB) with Tuple[(A,B)]

  case class Tuple3[A,B,C](exprA: TypedExpr[A], exprB: TypedExpr[B], exprC: TypedExpr[C])
    extends Untyped.Tuple3(exprA, exprB, exprC) with Tuple[(A,B,C)]


  //Typed values
  sealed trait Value[A] extends Untyped.Value

  case class VInt(integer: Integer)
    extends Untyped.VInt(integer) with Value[Integer]

  case class VString(string: String)
    extends Untyped.VString(string) with Value[String]

  //Typed types
  sealed trait Type[+A] extends Untyped.Type

  case class TSimple[A]()
    extends Untyped.TSimple(typeName[A]) with Type[A]

  case class TTuple[A](tType: TType[A])
    extends Untyped.TTuple(tType) with Type[A]

  case class TFun[A,B](tFrom: Type[A], tTo: Type[B])
    extends Untyped.TFun(tFrom, tTo) with Type[A => B]

  case class TMonad[A,B](type1: Type[A], type2: Type[B])
    extends Untyped.TMonad(type1, type2) with Type[CC[B]]


  //Typed tuple types
  sealed trait TType[A] extends Untyped.TType

  case class TType2[A,B](tA: Type[A], tB: Type[B])
    extends Untyped.TType2(tA, tB) with TType[(A,B)]

  case class TType3[A,B,C](tA: Type[A], tB: Type[B], tC: Type[C])
    extends Untyped.TType3(tA, tB, tC) with TType[(A,B,C)]


  def typeCheck(expr: Untyped.TypedExpr): TypedExpr[A] forSome {type A} = expr match {
    case e: Untyped.ETyped => ETyped(typeCheck(e.expr_), extractType(e.type_))
  }

  def typeCheck(expr: Untyped.Expr): Expr[A] forSome {type A} = expr match {
    case e: Untyped.EVar         => EVar(e.var_)
    case e: Untyped.EVal         => EVal(inferType(e.value_))
    case e: Untyped.EAbs         => EAbs(e.var_, extractType(e.type_), typeCheck(e.typedexpr_))
    case e: Untyped.EApp         => EApp(typeCheck(e.typedexpr_1), typeCheck(e.typedexpr_2))
    case e: Untyped.ETuple       => ETuple(typeCheck(e.tuple_))
    case e: Untyped.EReturn      => EReturn(typeCheck(e.typedexpr_))
    case e: Untyped.EBind        => EBind(typeCheck(e.typedexpr_1), typeCheck(e.typedexpr_2))
    case e: Untyped.ENewPrompt   => ENewPrompt()
    case e: Untyped.EPushPrompt  => EPushPrompt(typeCheck(e.typedexpr_1), typeCheck(e.typedexpr_2)) // Test this constructor
    case e: Untyped.EWithSubCont => EWithSubCont(typeCheck(e.typedexpr_1), typeCheck(e.typedexpr_2)) // and this one
    case e: Untyped.EPushSubCont => EPushSubCont(typeCheck(e.typedexpr_1), typeCheck(e.typedexpr_2)) // and this one
  }

  private def inferType(value: Untyped.Value): Value[A] forSome {type A} = value match {
    case v: Untyped.VInt    => VInt(v.integer_)
    case v: Untyped.VString => VString(v.string_)
  }

  private def typeCheck(tuple: Untyped.Tuple): Tuple[A] forSome {type A} = tuple match {
    case t: Untyped.Tuple2 => Tuple2(typeCheck(t.typedexpr_1), typeCheck(t.typedexpr_2))
    case t: Untyped.Tuple3 => Tuple3(typeCheck(t.typedexpr_1), typeCheck(t.typedexpr_2), typeCheck(t.typedexpr_3))
  }

  private def extractType(typ: Untyped.Type): Type[A] forSome {type A} = typ match {
    case t: Untyped.TSimple => extractType(t)
    case t: Untyped.TTuple  => TTuple(extractType(t.ttype_))
    case t: Untyped.TMonad  => TMonad(extractType(t.type_1), extractType(t.type_2))
    case t: Untyped.TFun    => TFun(extractType(t.type_1), extractType(t.type_2))
  }

  private def extractType(ttype: Untyped.TType): TType[A] forSome {type A} = ttype match {
    case t: Untyped.TType2 => TType2(extractType(t.type_1), extractType(t.type_2))
    case t: Untyped.TType3 => TType3(extractType(t.type_1), extractType(t.type_2), extractType(t.type_3))
  }

  private def extractType(typ: Untyped.TSimple): TSimple[A] forSome {type A} = typ.simpletype_ match {
    case "Integer" => TSimple[Integer]()
    case "String"  => TSimple[String]()
    case "Char"    => TSimple[Char]()
  }

  /*
  def typeCheck(expr: Untyped.Expr): Expr[A] forSome {type A} = expr match {
    case e: Untyped.EVar         => EVar(e.var_, extractType(e.type_))
    case e: Untyped.EVal         => EVal(inferType(e.value_))
    case e: Untyped.EAbs         => EAbs(e.var_, extractType(e.type_1), typeCheck(e.expr_), extractType(e.type_2))
    case e: Untyped.EApp         => EApp(typeCheck(e.expr_1), typeCheck(e.expr_2), extractType(e.type_))
    case e: Untyped.ETuple       => ETuple(typeCheck(e.tuple_), extractType(e.type_)) // Why does this work without impl proof?
    case e: Untyped.EReturn      => EReturn(typeCheck(e.expr_), extractType(e.type_))
    case e: Untyped.EBind        => EBind(typeCheck(e.expr_1), typeCheck(e.expr_2), extractType(e.type_))
    case e: Untyped.ENewPrompt   => ENewPrompt()
    case e: Untyped.EPushPrompt  => EPushPrompt(typeCheck(e.expr_1), typeCheck(e.expr_2), extractType(e.type_)) // Test this constructor
    case e: Untyped.EWithSubCont => EWithSubCont(typeCheck(e.expr_1), typeCheck(e.expr_2), extractType(e.type_)) // and this one
    case e: Untyped.EPushSubCont => EPushSubCont(typeCheck(e.expr_1), typeCheck(e.expr_2), extractType(e.type_)) // and this one
  }

  private def typeCheck(tuple: Untyped.Tuple): Tuple[A] forSome {type A} = tuple match {
    case t: Untyped.Tuple2 => Tuple2(typeCheck(t.expr_1), typeCheck(t.expr_2))
    case t: Untyped.Tuple3 => Tuple3(typeCheck(t.expr_1), typeCheck(t.expr_2), typeCheck(t.expr_3))
  }


  private def inferType(value: Untyped.Value): Value[A] forSome {type A} = value match {
    case v: Untyped.VInt    => VInt(v.integer_)
    case v: Untyped.VString => VString(v.string_)
  }




  */
}
