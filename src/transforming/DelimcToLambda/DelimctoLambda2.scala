package transforming.DelimcToLambda

import parsing.delimc.{Absyn => DC}
import parsing.lambda.{Absyn => L}
import transforming.NameGen._
import transforming.DelimcToLambda.ConstructorHelpers._

/**
  * Created by weeeeeew on 2016-06-09.
  */
object DelimcToLambda {
  def translate(id : String) : String = "dc_" + id

  def translate(expr: DC.Expr): NameGen[L.Expr] = expr match {
    case dce : DC.EVar        => NameGen[L.Expr](lVar(translate(dce.ident_)))

    case dce : DC.EAbs        => for { le <- translate(dce.expr_) }
      yield lAbs(translate(dce.ident_), le)

    case dce : DCApp        => for { le_1 <- translate(dce.expr_1); le_2 <- translate(dce.expr_2) }
      yield lApp(le_1, le_2)

    case dce : EReturn      => for { t <- translate(dce.expr_); k <- newName("k") }
      yield lAbs(k, lApp(k, t))

    case dce : EBind        => for { t_1 <- translate(dce.expr_1); t_2 <- translate(dce.expr_2); k <- newName("k"); v <- newName("v") }
      yield lAbs(k, lApp(t_1, lAbs(v, lApp(lApp(t_2, v), k))))

    case dce : ENewPrompt   => for { k <- newName("k"); gamma <- newName("gamma"); q <- newName("q") }
      yield lAbs(k, lAbs(gamma, lAbs(q, lApp(lApp(lApp(k, q), gamma), q+1)))) //TODO fix q+1

    case dce : EPushPrompt  => for { p <- translate(dce.expr_1); t <- translate(dce.expr_2); k <- newName("k"); gamma <- newName("gamma") }
      yield undefined

    case dce : EWithSubCont => undefined

    case dce : EPushSubCont => undefined

  }
}
