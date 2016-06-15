package parsing.delimc;
import parsing.delimc.Absyn.*;
/** BNFC-Generated Composition Visitor
*/

public class ComposVisitor<A> implements
  parsing.delimc.Absyn.Expr.Visitor<parsing.delimc.Absyn.Expr,A>,
  parsing.delimc.Absyn.Value.Visitor<parsing.delimc.Absyn.Value,A>,
  parsing.delimc.Absyn.Type.Visitor<parsing.delimc.Absyn.Type,A>,
  parsing.delimc.Absyn.MType.Visitor<parsing.delimc.Absyn.MType,A>,
  parsing.delimc.Absyn.FType.Visitor<parsing.delimc.Absyn.FType,A>,
  parsing.delimc.Absyn.TType.Visitor<parsing.delimc.Absyn.TType,A>
{
/* Expr */
    public Expr visit(parsing.delimc.Absyn.EVar p, A arg)
    {
      String var_ = p.var_;
      Type type_ = p.type_.accept(this, arg);
      return new parsing.delimc.Absyn.EVar(var_, type_);
    }    public Expr visit(parsing.delimc.Absyn.EVal p, A arg)
    {
      Value value_ = p.value_.accept(this, arg);
      return new parsing.delimc.Absyn.EVal(value_);
    }    public Expr visit(parsing.delimc.Absyn.EAbs p, A arg)
    {
      String var_ = p.var_;
      Type type_ = p.type_.accept(this, arg);
      Expr expr_ = p.expr_.accept(this, arg);
      FType ftype_ = p.ftype_.accept(this, arg);
      return new parsing.delimc.Absyn.EAbs(var_, type_, expr_, ftype_);
    }    public Expr visit(parsing.delimc.Absyn.EApp p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      Type type_ = p.type_.accept(this, arg);
      return new parsing.delimc.Absyn.EApp(expr_1, expr_2, type_);
    }    public Expr visit(parsing.delimc.Absyn.EReturn p, A arg)
    {
      Expr expr_ = p.expr_.accept(this, arg);
      MType mtype_ = p.mtype_.accept(this, arg);
      return new parsing.delimc.Absyn.EReturn(expr_, mtype_);
    }    public Expr visit(parsing.delimc.Absyn.EBind p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      MType mtype_ = p.mtype_.accept(this, arg);
      return new parsing.delimc.Absyn.EBind(expr_1, expr_2, mtype_);
    }    public Expr visit(parsing.delimc.Absyn.ENewPrompt p, A arg)
    {
      return new parsing.delimc.Absyn.ENewPrompt();
    }    public Expr visit(parsing.delimc.Absyn.EPushPrompt p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      MType mtype_ = p.mtype_.accept(this, arg);
      return new parsing.delimc.Absyn.EPushPrompt(expr_1, expr_2, mtype_);
    }    public Expr visit(parsing.delimc.Absyn.EWithSubCont p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      MType mtype_ = p.mtype_.accept(this, arg);
      return new parsing.delimc.Absyn.EWithSubCont(expr_1, expr_2, mtype_);
    }    public Expr visit(parsing.delimc.Absyn.EPushSubCont p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      MType mtype_ = p.mtype_.accept(this, arg);
      return new parsing.delimc.Absyn.EPushSubCont(expr_1, expr_2, mtype_);
    }    public Expr visit(parsing.delimc.Absyn.ETuple p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      TType ttype_ = p.ttype_.accept(this, arg);
      return new parsing.delimc.Absyn.ETuple(expr_1, expr_2, ttype_);
    }
/* Value */
    public Value visit(parsing.delimc.Absyn.VInt p, A arg)
    {
      Integer integer_ = p.integer_;
      return new parsing.delimc.Absyn.VInt(integer_);
    }    public Value visit(parsing.delimc.Absyn.VString p, A arg)
    {
      String string_ = p.string_;
      return new parsing.delimc.Absyn.VString(string_);
    }
/* Type */
    public Type visit(parsing.delimc.Absyn.TSimple p, A arg)
    {
      String simpletype_ = p.simpletype_;
      return new parsing.delimc.Absyn.TSimple(simpletype_);
    }    public Type visit(parsing.delimc.Absyn.TMonad p, A arg)
    {
      MType mtype_ = p.mtype_.accept(this, arg);
      return new parsing.delimc.Absyn.TMonad(mtype_);
    }    public Type visit(parsing.delimc.Absyn.TTuple p, A arg)
    {
      TType ttype_ = p.ttype_.accept(this, arg);
      return new parsing.delimc.Absyn.TTuple(ttype_);
    }    public Type visit(parsing.delimc.Absyn.TFun p, A arg)
    {
      FType ftype_ = p.ftype_.accept(this, arg);
      return new parsing.delimc.Absyn.TFun(ftype_);
    }
/* MType */
    public MType visit(parsing.delimc.Absyn.CCType p, A arg)
    {
      Type type_1 = p.type_1.accept(this, arg);
      Type type_2 = p.type_2.accept(this, arg);
      return new parsing.delimc.Absyn.CCType(type_1, type_2);
    }
/* FType */
    public FType visit(parsing.delimc.Absyn.FunType p, A arg)
    {
      Type type_1 = p.type_1.accept(this, arg);
      Type type_2 = p.type_2.accept(this, arg);
      return new parsing.delimc.Absyn.FunType(type_1, type_2);
    }
/* TType */
    public TType visit(parsing.delimc.Absyn.TupleType p, A arg)
    {
      Type type_1 = p.type_1.accept(this, arg);
      Type type_2 = p.type_2.accept(this, arg);
      return new parsing.delimc.Absyn.TupleType(type_1, type_2);
    }
}