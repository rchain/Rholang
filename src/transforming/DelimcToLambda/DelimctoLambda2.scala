package transforming.DelimcToLambda

import parsing.delimc.{Absyn => DC}
import parsing.lambda.{Absyn => L}
import transforming.NameGen._
import transforming.DelimcToLambda.ConstructorHelpers._

/**
  * Created by weeeeeew on 2016-06-09.
  */
object DelimcToLambda {
  def translate(id: String) : String = "dc_" + id

  trait CC[A]

  def translate[A](expr: DC.Expr) : CC[A] = expr match {
    case e: DC.EVal    => ???
    case e: DC.EVar    => ???
    case e: DC.EReturn => ???
    case e: DC.EBind   => ???
  }
}
