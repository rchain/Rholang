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
      //p.var_;
      p.type_.accept(new TypeVisitor<R,A>(), arg);
      return null;
    }    public R visit(parsing.delimc.Absyn.EVal p, A arg)
    { /* Code For EVal Goes Here */
      p.value_.accept(new ValueVisitor<R,A>(), arg);
      return null;
    }    public R visit(parsing.delimc.Absyn.EAbs p, A arg)
    { /* Code For EAbs Goes Here */
      //p.var_;
      p.type_.accept(new TypeVisitor<R,A>(), arg);
      p.expr_.accept(new ExprVisitor<R,A>(), arg);
      p.ftype_.accept(new FTypeVisitor<R,A>(), arg);
      return null;
    }    public R visit(parsing.delimc.Absyn.EApp p, A arg)
    { /* Code For EApp Goes Here */
      p.expr_1.accept(new ExprVisitor<R,A>(), arg);
      p.expr_2.accept(new ExprVisitor<R,A>(), arg);
      p.type_.accept(new TypeVisitor<R,A>(), arg);
      return null;
    }    public R visit(parsing.delimc.Absyn.EReturn p, A arg)
    { /* Code For EReturn Goes Here */
      p.expr_.accept(new ExprVisitor<R,A>(), arg);
      p.mtype_.accept(new MTypeVisitor<R,A>(), arg);
      return null;
    }    public R visit(parsing.delimc.Absyn.EBind p, A arg)
    { /* Code For EBind Goes Here */
      p.expr_1.accept(new ExprVisitor<R,A>(), arg);
      p.expr_2.accept(new ExprVisitor<R,A>(), arg);
      p.mtype_.accept(new MTypeVisitor<R,A>(), arg);
      return null;
    }    public R visit(parsing.delimc.Absyn.ENewPrompt p, A arg)
    { /* Code For ENewPrompt Goes Here */
      return null;
    }    public R visit(parsing.delimc.Absyn.EPushPrompt p, A arg)
    { /* Code For EPushPrompt Goes Here */
      p.expr_1.accept(new ExprVisitor<R,A>(), arg);
      p.expr_2.accept(new ExprVisitor<R,A>(), arg);
      p.mtype_.accept(new MTypeVisitor<R,A>(), arg);
      return null;
    }    public R visit(parsing.delimc.Absyn.EWithSubCont p, A arg)
    { /* Code For EWithSubCont Goes Here */
      p.expr_1.accept(new ExprVisitor<R,A>(), arg);
      p.expr_2.accept(new ExprVisitor<R,A>(), arg);
      p.mtype_.accept(new MTypeVisitor<R,A>(), arg);
      return null;
    }    public R visit(parsing.delimc.Absyn.EPushSubCont p, A arg)
    { /* Code For EPushSubCont Goes Here */
      p.expr_1.accept(new ExprVisitor<R,A>(), arg);
      p.expr_2.accept(new ExprVisitor<R,A>(), arg);
      p.mtype_.accept(new MTypeVisitor<R,A>(), arg);
      return null;
    }    public R visit(parsing.delimc.Absyn.ETuple p, A arg)
    { /* Code For ETuple Goes Here */
      p.expr_1.accept(new ExprVisitor<R,A>(), arg);
      p.expr_2.accept(new ExprVisitor<R,A>(), arg);
      p.ttype_.accept(new TTypeVisitor<R,A>(), arg);
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
  public class TypeVisitor<R,A> implements Type.Visitor<R,A>
  {
    public R visit(parsing.delimc.Absyn.TSimple p, A arg)
    { /* Code For TSimple Goes Here */
      //p.simpletype_;
      return null;
    }        public R visit(parsing.delimc.Absyn.TMonad p, A arg)
    { /* Code For TMonad Goes Here */
      p.mtype_.accept(new MTypeVisitor<R,A>(), arg);
      return null;
    }    public R visit(parsing.delimc.Absyn.TTuple p, A arg)
    { /* Code For TTuple Goes Here */
      p.ttype_.accept(new TTypeVisitor<R,A>(), arg);
      return null;
    }        public R visit(parsing.delimc.Absyn.TFun p, A arg)
    { /* Code For TFun Goes Here */
      p.ftype_.accept(new FTypeVisitor<R,A>(), arg);
      return null;
    }    
  }
  public class MTypeVisitor<R,A> implements MType.Visitor<R,A>
  {
    public R visit(parsing.delimc.Absyn.CCType p, A arg)
    { /* Code For CCType Goes Here */
      p.type_1.accept(new TypeVisitor<R,A>(), arg);
      p.type_2.accept(new TypeVisitor<R,A>(), arg);
      return null;
    }
  }
  public class FTypeVisitor<R,A> implements FType.Visitor<R,A>
  {
    public R visit(parsing.delimc.Absyn.FunType p, A arg)
    { /* Code For FunType Goes Here */
      p.type_1.accept(new TypeVisitor<R,A>(), arg);
      p.type_2.accept(new TypeVisitor<R,A>(), arg);
      return null;
    }
  }
  public class TTypeVisitor<R,A> implements TType.Visitor<R,A>
  {
    public R visit(parsing.delimc.Absyn.TupleType p, A arg)
    { /* Code For TupleType Goes Here */
      p.type_1.accept(new TypeVisitor<R,A>(), arg);
      p.type_2.accept(new TypeVisitor<R,A>(), arg);
      return null;
    }
  }
}