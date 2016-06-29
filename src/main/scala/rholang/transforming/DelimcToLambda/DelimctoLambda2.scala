package rholang.transforming.DelimcToLambda

import rholang.parsing.delimc.{Absyn => DC}
// import rholang.parsing.lambda.{Absyn => L}
// import rholang.transforming.NameGen._
// import rholang.transforming.DelimcToLambda.ConstructorHelpers._

/**
  * Created by weeeeeew on 2016-06-09.
  */
object DelimcToLambda2 {
  def translate(id: String) : String = "dc_" + id

  trait CC[A]

  def translate[A](expr: DC.Expr) : CC[A] = expr match {
    case e: DC.EVal    => ???
    case e: DC.EVar    => ???
    case e: DC.EReturn => ???
    case e: DC.EBind   => ???
  }
}
