package parsing.delimc;
import parsing.delimc.Absyn.*;
/*** BNFC-Generated Visitor Design Pattern Skeleton. ***/
/* This implements the common visitor design pattern.
   Tests show it to be slightly less efficient than the
   instanceof method, but easier to use. 
   Replace the R and A parameters with the desired return
   and context types.*/

public class VisitSkel
{
  public class ExprVisitor<R,A> implements Expr.Visitor<R,A>
  {
    public R visit(parsing.delimc.Absyn.EVar p, A arg)
    { /* Code For EVar Goes Here */
      //p.ident_;
      return null;
    }    public R visit(parsing.delimc.Absyn.EVal p, A arg)
    { /* Code For EVal Goes Here */
      p.value_.accept(new ValueVisitor<R,A>(), arg);
      return null;
    }    public R visit(parsing.delimc.Absyn.EAbs p, A arg)
    { /* Code For EAbs Goes Here */
      //p.ident_;
      p.expr_.accept(new ExprVisitor<R,A>(), arg);
      return null;
    }    public R visit(parsing.delimc.Absyn.EApp p, A arg)
    { /* Code For EApp Goes Here */
      p.expr_1.accept(new ExprVisitor<R,A>(), arg);
      p.expr_2.accept(new ExprVisitor<R,A>(), arg);
      return null;
    }    public R visit(parsing.delimc.Absyn.EReturn p, A arg)
    { /* Code For EReturn Goes Here */
      p.expr_.accept(new ExprVisitor<R,A>(), arg);
      return null;
    }    public R visit(parsing.delimc.Absyn.EBind p, A arg)
    { /* Code For EBind Goes Here */
      p.expr_1.accept(new ExprVisitor<R,A>(), arg);
      p.expr_2.accept(new ExprVisitor<R,A>(), arg);
      return null;
    }    public R visit(parsing.delimc.Absyn.ENewPrompt p, A arg)
    { /* Code For ENewPrompt Goes Here */
      return null;
    }    public R visit(parsing.delimc.Absyn.EPushPrompt p, A arg)
    { /* Code For EPushPrompt Goes Here */
      p.expr_1.accept(new ExprVisitor<R,A>(), arg);
      p.expr_2.accept(new ExprVisitor<R,A>(), arg);
      return null;
    }    public R visit(parsing.delimc.Absyn.EWithSubCont p, A arg)
    { /* Code For EWithSubCont Goes Here */
      p.expr_1.accept(new ExprVisitor<R,A>(), arg);
      p.expr_2.accept(new ExprVisitor<R,A>(), arg);
      return null;
    }    public R visit(parsing.delimc.Absyn.EPushSubCont p, A arg)
    { /* Code For EPushSubCont Goes Here */
      p.expr_1.accept(new ExprVisitor<R,A>(), arg);
      p.expr_2.accept(new ExprVisitor<R,A>(), arg);
      return null;
    }    public R visit(parsing.delimc.Absyn.ETuple p, A arg)
    { /* Code For ETuple Goes Here */
      p.expr_1.accept(new ExprVisitor<R,A>(), arg);
      p.expr_2.accept(new ExprVisitor<R,A>(), arg);
      return null;
    }
  }
  public class ValueVisitor<R,A> implements Value.Visitor<R,A>
  {
    public R visit(parsing.delimc.Absyn.VInt p, A arg)
    { /* Code For VInt Goes Here */
      //p.integer_;
      return null;
    }    public R visit(parsing.delimc.Absyn.VString p, A arg)
    { /* Code For VString Goes Here */
      //p.string_;
      return null;
    }
  }
}