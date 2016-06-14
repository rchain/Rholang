package parsing.delimc;
import parsing.delimc.Absyn.*;
/** BNFC-Generated Composition Visitor
*/

public class ComposVisitor<A> implements
  parsing.delimc.Absyn.Expr.Visitor<parsing.delimc.Absyn.Expr,A>,
  parsing.delimc.Absyn.Value.Visitor<parsing.delimc.Absyn.Value,A>
{
/* Expr */
    public Expr visit(parsing.delimc.Absyn.EVar p, A arg)
    {
      String ident_ = p.ident_;
      return new parsing.delimc.Absyn.EVar(ident_);
    }    public Expr visit(parsing.delimc.Absyn.EVal p, A arg)
    {
      Value value_ = p.value_.accept(this, arg);
      return new parsing.delimc.Absyn.EVal(value_);
    }    public Expr visit(parsing.delimc.Absyn.EAbs p, A arg)
    {
      String ident_ = p.ident_;
      Expr expr_ = p.expr_.accept(this, arg);
      return new parsing.delimc.Absyn.EAbs(ident_, expr_);
    }    public Expr visit(parsing.delimc.Absyn.EApp p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      return new parsing.delimc.Absyn.EApp(expr_1, expr_2);
    }    public Expr visit(parsing.delimc.Absyn.EReturn p, A arg)
    {
      Expr expr_ = p.expr_.accept(this, arg);
      return new parsing.delimc.Absyn.EReturn(expr_);
    }    public Expr visit(parsing.delimc.Absyn.EBind p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      return new parsing.delimc.Absyn.EBind(expr_1, expr_2);
    }    public Expr visit(parsing.delimc.Absyn.ENewPrompt p, A arg)
    {
      return new parsing.delimc.Absyn.ENewPrompt();
    }    public Expr visit(parsing.delimc.Absyn.EPushPrompt p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      return new parsing.delimc.Absyn.EPushPrompt(expr_1, expr_2);
    }    public Expr visit(parsing.delimc.Absyn.EWithSubCont p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      return new parsing.delimc.Absyn.EWithSubCont(expr_1, expr_2);
    }    public Expr visit(parsing.delimc.Absyn.EPushSubCont p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      return new parsing.delimc.Absyn.EPushSubCont(expr_1, expr_2);
    }    public Expr visit(parsing.delimc.Absyn.ETuple p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      return new parsing.delimc.Absyn.ETuple(expr_1, expr_2);
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
}