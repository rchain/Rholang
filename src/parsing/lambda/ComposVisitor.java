package parsing.lambda;
import parsing.lambda.Absyn.*;
/** BNFC-Generated Composition Visitor
*/

public class ComposVisitor<A> implements
  parsing.lambda.Absyn.Expr.Visitor<parsing.lambda.Absyn.Expr,A>,
  parsing.lambda.Absyn.Tuple.Visitor<parsing.lambda.Absyn.Tuple,A>,
  parsing.lambda.Absyn.Value.Visitor<parsing.lambda.Absyn.Value,A>,
  parsing.lambda.Absyn.Type.Visitor<parsing.lambda.Absyn.Type,A>,
  parsing.lambda.Absyn.TType.Visitor<parsing.lambda.Absyn.TType,A>
{
/* Expr */
    public Expr visit(parsing.lambda.Absyn.EVar p, A arg)
    {
      String var_ = p.var_;
      Type type_ = p.type_.accept(this, arg);
      return new parsing.lambda.Absyn.EVar(var_, type_);
    }    public Expr visit(parsing.lambda.Absyn.EVal p, A arg)
    {
      Value value_ = p.value_.accept(this, arg);
      return new parsing.lambda.Absyn.EVal(value_);
    }    public Expr visit(parsing.lambda.Absyn.EAbs p, A arg)
    {
      String var_ = p.var_;
      Type type_1 = p.type_1.accept(this, arg);
      Expr expr_ = p.expr_.accept(this, arg);
      Type type_2 = p.type_2.accept(this, arg);
      return new parsing.lambda.Absyn.EAbs(var_, type_1, expr_, type_2);
    }    public Expr visit(parsing.lambda.Absyn.EApp p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      Type type_ = p.type_.accept(this, arg);
      return new parsing.lambda.Absyn.EApp(expr_1, expr_2, type_);
    }    public Expr visit(parsing.lambda.Absyn.ETuple p, A arg)
    {
      Tuple tuple_ = p.tuple_.accept(this, arg);
      Type type_ = p.type_.accept(this, arg);
      return new parsing.lambda.Absyn.ETuple(tuple_, type_);
    }
/* Tuple */
    public Tuple visit(parsing.lambda.Absyn.Tuple2 p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      return new parsing.lambda.Absyn.Tuple2(expr_1, expr_2);
    }    public Tuple visit(parsing.lambda.Absyn.Tuple3 p, A arg)
    {
      Expr expr_1 = p.expr_1.accept(this, arg);
      Expr expr_2 = p.expr_2.accept(this, arg);
      Expr expr_3 = p.expr_3.accept(this, arg);
      return new parsing.lambda.Absyn.Tuple3(expr_1, expr_2, expr_3);
    }
/* Value */
    public Value visit(parsing.lambda.Absyn.VInt p, A arg)
    {
      Integer integer_ = p.integer_;
      return new parsing.lambda.Absyn.VInt(integer_);
    }    public Value visit(parsing.lambda.Absyn.VString p, A arg)
    {
      String string_ = p.string_;
      return new parsing.lambda.Absyn.VString(string_);
    }
/* Type */
    public Type visit(parsing.lambda.Absyn.TSimple p, A arg)
    {
      String simpletype_ = p.simpletype_;
      return new parsing.lambda.Absyn.TSimple(simpletype_);
    }    public Type visit(parsing.lambda.Absyn.TTuple p, A arg)
    {
      TType ttype_ = p.ttype_.accept(this, arg);
      return new parsing.lambda.Absyn.TTuple(ttype_);
    }    public Type visit(parsing.lambda.Absyn.TFun p, A arg)
    {
      Type type_1 = p.type_1.accept(this, arg);
      Type type_2 = p.type_2.accept(this, arg);
      return new parsing.lambda.Absyn.TFun(type_1, type_2);
    }
/* TType */
    public TType visit(parsing.lambda.Absyn.TType2 p, A arg)
    {
      Type type_1 = p.type_1.accept(this, arg);
      Type type_2 = p.type_2.accept(this, arg);
      return new parsing.lambda.Absyn.TType2(type_1, type_2);
    }    public TType visit(parsing.lambda.Absyn.TType3 p, A arg)
    {
      Type type_1 = p.type_1.accept(this, arg);
      Type type_2 = p.type_2.accept(this, arg);
      Type type_3 = p.type_3.accept(this, arg);
      return new parsing.lambda.Absyn.TType3(type_1, type_2, type_3);
    }
}