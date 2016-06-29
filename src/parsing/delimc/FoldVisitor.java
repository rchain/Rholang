package parsing.delimc;

import parsing.delimc.Absyn.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/** BNFC-Generated Fold Visitor */
public abstract class FoldVisitor<R,A> implements AllVisitor<R,A> {
    public abstract R leaf(A arg);
    public abstract R combine(R x, R y, A arg);

/* Expr */
    public R visit(parsing.delimc.Absyn.EVar p, A arg) {
      R r = leaf(arg);
      r = combine(p.type_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(parsing.delimc.Absyn.EVal p, A arg) {
      R r = leaf(arg);
      r = combine(p.value_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(parsing.delimc.Absyn.EAbs p, A arg) {
      R r = leaf(arg);
      r = combine(p.type_1.accept(this, arg), r, arg);
      r = combine(p.expr_.accept(this, arg), r, arg);
      r = combine(p.type_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(parsing.delimc.Absyn.EApp p, A arg) {
      R r = leaf(arg);
      r = combine(p.expr_1.accept(this, arg), r, arg);
      r = combine(p.expr_2.accept(this, arg), r, arg);
      r = combine(p.type_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(parsing.delimc.Absyn.EReturn p, A arg) {
      R r = leaf(arg);
      r = combine(p.expr_.accept(this, arg), r, arg);
      r = combine(p.type_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(parsing.delimc.Absyn.EBind p, A arg) {
      R r = leaf(arg);
      r = combine(p.expr_1.accept(this, arg), r, arg);
      r = combine(p.expr_2.accept(this, arg), r, arg);
      r = combine(p.type_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(parsing.delimc.Absyn.ENewPrompt p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(parsing.delimc.Absyn.EPushPrompt p, A arg) {
      R r = leaf(arg);
      r = combine(p.expr_1.accept(this, arg), r, arg);
      r = combine(p.expr_2.accept(this, arg), r, arg);
      r = combine(p.type_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(parsing.delimc.Absyn.EWithSubCont p, A arg) {
      R r = leaf(arg);
      r = combine(p.expr_1.accept(this, arg), r, arg);
      r = combine(p.expr_2.accept(this, arg), r, arg);
      r = combine(p.type_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(parsing.delimc.Absyn.EPushSubCont p, A arg) {
      R r = leaf(arg);
      r = combine(p.expr_1.accept(this, arg), r, arg);
      r = combine(p.expr_2.accept(this, arg), r, arg);
      r = combine(p.type_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(parsing.delimc.Absyn.ETuple p, A arg) {
      R r = leaf(arg);
      r = combine(p.tuple_.accept(this, arg), r, arg);
      r = combine(p.type_.accept(this, arg), r, arg);
      return r;
    }

/* Tuple */
    public R visit(parsing.delimc.Absyn.Tuple2 p, A arg) {
      R r = leaf(arg);
      r = combine(p.expr_1.accept(this, arg), r, arg);
      r = combine(p.expr_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(parsing.delimc.Absyn.Tuple3 p, A arg) {
      R r = leaf(arg);
      r = combine(p.expr_1.accept(this, arg), r, arg);
      r = combine(p.expr_2.accept(this, arg), r, arg);
      r = combine(p.expr_3.accept(this, arg), r, arg);
      return r;
    }

/* Value */
    public R visit(parsing.delimc.Absyn.VInt p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(parsing.delimc.Absyn.VString p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* Type */
    public R visit(parsing.delimc.Absyn.TSimple p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(parsing.delimc.Absyn.TTuple p, A arg) {
      R r = leaf(arg);
      r = combine(p.ttype_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(parsing.delimc.Absyn.TMonad p, A arg) {
      R r = leaf(arg);
      r = combine(p.type_1.accept(this, arg), r, arg);
      r = combine(p.type_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(parsing.delimc.Absyn.TFun p, A arg) {
      R r = leaf(arg);
      r = combine(p.type_1.accept(this, arg), r, arg);
      r = combine(p.type_2.accept(this, arg), r, arg);
      return r;
    }

/* TType */
    public R visit(parsing.delimc.Absyn.TType2 p, A arg) {
      R r = leaf(arg);
      r = combine(p.type_1.accept(this, arg), r, arg);
      r = combine(p.type_2.accept(this, arg), r, arg);
      return r;
    }
    public R visit(parsing.delimc.Absyn.TType3 p, A arg) {
      R r = leaf(arg);
      r = combine(p.type_1.accept(this, arg), r, arg);
      r = combine(p.type_2.accept(this, arg), r, arg);
      r = combine(p.type_3.accept(this, arg), r, arg);
      return r;
    }


}
