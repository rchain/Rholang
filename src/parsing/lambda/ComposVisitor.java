package parsing.lambda;
import parsing.lambda.Absyn.*;
/** BNFC-Generated Composition Visitor
*/

public class ComposVisitor<A> implements
  parsing.lambda.Absyn.Expr.Visitor<parsing.lambda.Absyn.Expr,A>,
  parsing.lambda.Absyn.Value.Visitor<parsing.lambda.Absyn.Value,A>
{
/* Expr */
    public Expr visit(parsing.lambda.Absyn.EVar p, A arg)
    {
      String ident_ = p.ident_;
      return new parsing.lambda.Absyn.EVar(ident_);
    }    public Expr visit(parsing.lambda.Absyn.EVal p, A arg)
    {
      Value value_ = p.value_.accept(this, arg);
      return new parsing.lambda.Absyn.EVal(value_);
    }    public Expr visit(parsing.lambda.Absyn.EAbs p, A arg)
    {
      String ident_ = p.ident_;
      Expr expr_ = p.expr_.accept(this, arg);
      return new parsing.lambda.Absyn.EAbs(ident_, expr_);
    }    public Expr visit(parsing.lambda.Absyn.EApp p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      return new parsing.lambda.Absyn.EApp(expr_1, expr_2);
    }    public Expr visit(parsing.lambda.Absyn.ETuple p, A arg)
    {
      ListExpr listexpr_ = new ListExpr();
      for (Expr x : p.listexpr_)
      {
        listexpr_.add(x.accept(this,arg));
      }
      return new parsing.lambda.Absyn.ETuple(listexpr_);
    }
/* Value */
    public Value visit(parsing.lambda.Absyn.VInt p, A arg)
    {
      Integer integer_ = p.integer_;
      return new parsing.lambda.Absyn.VInt(integer_);
    }
}