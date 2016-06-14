package parsing.lambda;
import parsing.lambda.Absyn.*;
/** BNFC-Generated Abstract Visitor */
public class AbstractVisitor<R,A> implements AllVisitor<R,A> {
/* Expr */
    public R visit(parsing.lambda.Absyn.EVar p, A arg) { return visitDefault(p, arg); }
    public R visit(parsing.lambda.Absyn.EVal p, A arg) { return visitDefault(p, arg); }
    public R visit(parsing.lambda.Absyn.EAbs p, A arg) { return visitDefault(p, arg); }
    public R visit(parsing.lambda.Absyn.EApp p, A arg) { return visitDefault(p, arg); }
    public R visit(parsing.lambda.Absyn.ETuple p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(parsing.lambda.Absyn.Expr p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Value */
    public R visit(parsing.lambda.Absyn.VInt p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(parsing.lambda.Absyn.Value p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }

}
