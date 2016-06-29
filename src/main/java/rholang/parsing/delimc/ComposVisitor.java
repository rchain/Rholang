package rholang.parsing.delimc;
import rholang.parsing.delimc.Absyn.*;
/** BNFC-Generated Composition Visitor
*/

public class ComposVisitor<A> implements
  rholang.parsing.delimc.Absyn.Expr.Visitor<rholang.parsing.delimc.Absyn.Expr,A>,
  rholang.parsing.delimc.Absyn.Tuple.Visitor<rholang.parsing.delimc.Absyn.Tuple,A>,
  rholang.parsing.delimc.Absyn.Value.Visitor<rholang.parsing.delimc.Absyn.Value,A>,
  rholang.parsing.delimc.Absyn.Type.Visitor<rholang.parsing.delimc.Absyn.Type,A>,
  rholang.parsing.delimc.Absyn.TType.Visitor<rholang.parsing.delimc.Absyn.TType,A>
{
/* Expr */
    public Expr visit(rholang.parsing.delimc.Absyn.EVar p, A arg)
    {
      String var_ = p.var_;
      Type type_ = p.type_.accept(this, arg);
      return new rholang.parsing.delimc.Absyn.EVar(var_, type_);
    }    public Expr visit(rholang.parsing.delimc.Absyn.EVal p, A arg)
    {
      Value value_ = p.value_.accept(this, arg);
      return new rholang.parsing.delimc.Absyn.EVal(value_);
    }    public Expr visit(rholang.parsing.delimc.Absyn.EAbs p, A arg)
    {
      String var_ = p.var_;
      Type type_1 = p.type_1.accept(this, arg);
      Expr expr_ = p.expr_.accept(this, arg);
      Type type_2 = p.type_2.accept(this, arg);
      return new rholang.parsing.delimc.Absyn.EAbs(var_, type_1, expr_, type_2);
    }    public Expr visit(rholang.parsing.delimc.Absyn.EApp p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      Type type_ = p.type_.accept(this, arg);
      return new rholang.parsing.delimc.Absyn.EApp(expr_1, expr_2, type_);
    }    public Expr visit(rholang.parsing.delimc.Absyn.EReturn p, A arg)
    {
      Expr expr_ = p.expr_.accept(this, arg);
      Type type_ = p.type_.accept(this, arg);
      return new rholang.parsing.delimc.Absyn.EReturn(expr_, type_);
    }    public Expr visit(rholang.parsing.delimc.Absyn.EBind p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      Type type_ = p.type_.accept(this, arg);
      return new rholang.parsing.delimc.Absyn.EBind(expr_1, expr_2, type_);
    }    public Expr visit(rholang.parsing.delimc.Absyn.ENewPrompt p, A arg)
    {
      return new rholang.parsing.delimc.Absyn.ENewPrompt();
    }    public Expr visit(rholang.parsing.delimc.Absyn.EPushPrompt p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      Type type_ = p.type_.accept(this, arg);
      return new rholang.parsing.delimc.Absyn.EPushPrompt(expr_1, expr_2, type_);
    }    public Expr visit(rholang.parsing.delimc.Absyn.EWithSubCont p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      Type type_ = p.type_.accept(this, arg);
      return new rholang.parsing.delimc.Absyn.EWithSubCont(expr_1, expr_2, type_);
    }    public Expr visit(rholang.parsing.delimc.Absyn.EPushSubCont p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      Type type_ = p.type_.accept(this, arg);
      return new rholang.parsing.delimc.Absyn.EPushSubCont(expr_1, expr_2, type_);
    }    public Expr visit(rholang.parsing.delimc.Absyn.ETuple p, A arg)
    {
      Tuple tuple_ = p.tuple_.accept(this, arg);
      Type type_ = p.type_.accept(this, arg);
      return new rholang.parsing.delimc.Absyn.ETuple(tuple_, type_);
    }
/* Tuple */
    public Tuple visit(rholang.parsing.delimc.Absyn.Tuple2 p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      return new rholang.parsing.delimc.Absyn.Tuple2(expr_1, expr_2);
    }    public Tuple visit(rholang.parsing.delimc.Absyn.Tuple3 p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      Expr expr_3 = p.expr_3.accept(this, arg);
      return new rholang.parsing.delimc.Absyn.Tuple3(expr_1, expr_2, expr_3);
    }
/* Value */
    public Value visit(rholang.parsing.delimc.Absyn.VInt p, A arg)
    {
      Integer integer_ = p.integer_;
      return new rholang.parsing.delimc.Absyn.VInt(integer_);
    }    public Value visit(rholang.parsing.delimc.Absyn.VString p, A arg)
    {
      String string_ = p.string_;
      return new rholang.parsing.delimc.Absyn.VString(string_);
    }
/* Type */
    public Type visit(rholang.parsing.delimc.Absyn.TSimple p, A arg)
    {
      String simpletype_ = p.simpletype_;
      return new rholang.parsing.delimc.Absyn.TSimple(simpletype_);
    }    public Type visit(rholang.parsing.delimc.Absyn.TTuple p, A arg)
    {
      TType ttype_ = p.ttype_.accept(this, arg);
      return new rholang.parsing.delimc.Absyn.TTuple(ttype_);
    }    public Type visit(rholang.parsing.delimc.Absyn.TMonad p, A arg)
    {
      Type type_1 = p.type_1.accept(this, arg);
      Type type_2 = p.type_2.accept(this, arg);
      return new rholang.parsing.delimc.Absyn.TMonad(type_1, type_2);
    }    public Type visit(rholang.parsing.delimc.Absyn.TFun p, A arg)
    {
      Type type_1 = p.type_1.accept(this, arg);
      Type type_2 = p.type_2.accept(this, arg);
      return new rholang.parsing.delimc.Absyn.TFun(type_1, type_2);
    }
/* TType */
    public TType visit(rholang.parsing.delimc.Absyn.TType2 p, A arg)
    {
      Type type_1 = p.type_1.accept(this, arg);
      Type type_2 = p.type_2.accept(this, arg);
      return new rholang.parsing.delimc.Absyn.TType2(type_1, type_2);
    }    public TType visit(rholang.parsing.delimc.Absyn.TType3 p, A arg)
    {
      Type type_1 = p.type_1.accept(this, arg);
      Type type_2 = p.type_2.accept(this, arg);
      Type type_3 = p.type_3.accept(this, arg);
      return new rholang.parsing.delimc.Absyn.TType3(type_1, type_2, type_3);
    }
}