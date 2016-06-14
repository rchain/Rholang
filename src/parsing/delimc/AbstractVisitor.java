package parsing.delimc;
import parsing.delimc.Absyn.*;
/** BNFC-Generated Abstract Visitor */
public class AbstractVisitor<R,A> implements AllVisitor<R,A> {
/* Expr */
    public R visit(parsing.delimc.Absyn.EVar p, A arg) { return visitDefault(p, arg); }
    public R visit(parsing.delimc.Absyn.EVal p, A arg) { return visitDefault(p, arg); }
    public R visit(parsing.delimc.Absyn.EAbs p, A arg) { return visitDefault(p, arg); }
    public R visit(parsing.delimc.Absyn.EApp p, A arg) { return visitDefault(p, arg); }
    public R visit(parsing.delimc.Absyn.EReturn p, A arg) { return visitDefault(p, arg); }
    public R visit(parsing.delimc.Absyn.EBind p, A arg) { return visitDefault(p, arg); }
    public R visit(parsing.delimc.Absyn.ENewPrompt p, A arg) { return visitDefault(p, arg); }
    public R visit(parsing.delimc.Absyn.EPushPrompt p, A arg) { return visitDefault(p, arg); }
    public R visit(parsing.delimc.Absyn.EWithSubCont p, A arg) { return visitDefault(p, arg); }
    public R visit(parsing.delimc.Absyn.EPushSubCont p, A arg) { return visitDefault(p, arg); }
    public R visit(parsing.delimc.Absyn.ETuple p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(parsing.delimc.Absyn.Expr p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Value */
    public R visit(parsing.delimc.Absyn.VInt p, A arg) { return visitDefault(p, arg); }
    public R visit(parsing.delimc.Absyn.VString p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(parsing.delimc.Absyn.Value p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }

}
