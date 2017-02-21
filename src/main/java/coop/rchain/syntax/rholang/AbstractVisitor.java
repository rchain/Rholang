package coop.rchain.syntax.rholang;
import coop.rchain.syntax.rholang.Absyn.*;
/** BNFC-Generated Abstract Visitor */
public class AbstractVisitor<R,A> implements AllVisitor<R,A> {
/* Contr */
    public R visit(coop.rchain.syntax.rholang.Absyn.DContr p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(coop.rchain.syntax.rholang.Absyn.Contr p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Proc */
    public R visit(coop.rchain.syntax.rholang.Absyn.PNil p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.PValue p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.PVar p, A arg) { return visitDefault(p, arg); }

    public R visit(coop.rchain.syntax.rholang.Absyn.PDrop p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.PInject p, A arg) { return visitDefault(p, arg); }

    public R visit(coop.rchain.syntax.rholang.Absyn.PLift p, A arg) { return visitDefault(p, arg); }

    public R visit(coop.rchain.syntax.rholang.Absyn.PFoldL p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.PFoldR p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.PInput p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.PChoice p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.PMatch p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.PNew p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.PConstr p, A arg) { return visitDefault(p, arg); }

    public R visit(coop.rchain.syntax.rholang.Absyn.PPar p, A arg) { return visitDefault(p, arg); }

    public R visitDefault(coop.rchain.syntax.rholang.Absyn.Proc p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Chan */
    public R visit(coop.rchain.syntax.rholang.Absyn.CVar p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.CQuote p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(coop.rchain.syntax.rholang.Absyn.Chan p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Bind */
    public R visit(coop.rchain.syntax.rholang.Absyn.InputBind p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.CondInputBind p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(coop.rchain.syntax.rholang.Absyn.Bind p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* PMBranch */
    public R visit(coop.rchain.syntax.rholang.Absyn.PatternMatch p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(coop.rchain.syntax.rholang.Absyn.PMBranch p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* CBranch */
    public R visit(coop.rchain.syntax.rholang.Absyn.Choice p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(coop.rchain.syntax.rholang.Absyn.CBranch p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Value */
    public R visit(coop.rchain.syntax.rholang.Absyn.VQuant p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.VEnt p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(coop.rchain.syntax.rholang.Absyn.Value p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Quantity */
    public R visit(coop.rchain.syntax.rholang.Absyn.QInt p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.QDouble p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(coop.rchain.syntax.rholang.Absyn.Quantity p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Entity */
    public R visit(coop.rchain.syntax.rholang.Absyn.EChar p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.EStruct p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.ECollect p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(coop.rchain.syntax.rholang.Absyn.Entity p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Struct */
    public R visit(coop.rchain.syntax.rholang.Absyn.StructConstr p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(coop.rchain.syntax.rholang.Absyn.Struct p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Collect */
    public R visit(coop.rchain.syntax.rholang.Absyn.CString p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(coop.rchain.syntax.rholang.Absyn.Collect p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* VarPattern */
    public R visit(coop.rchain.syntax.rholang.Absyn.VarPtVar p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.VarPtWild p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(coop.rchain.syntax.rholang.Absyn.VarPattern p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* PPattern */
    public R visit(coop.rchain.syntax.rholang.Absyn.PPtVar p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.PPtNil p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.PPtVal p, A arg) { return visitDefault(p, arg); }

    public R visit(coop.rchain.syntax.rholang.Absyn.PPtDrop p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.PPtInject p, A arg) { return visitDefault(p, arg); }

    public R visit(coop.rchain.syntax.rholang.Absyn.PPtOutput p, A arg) { return visitDefault(p, arg); }

    public R visit(coop.rchain.syntax.rholang.Absyn.PPtInput p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.PPtMatch p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.PPtNew p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.PPtConstr p, A arg) { return visitDefault(p, arg); }

    public R visit(coop.rchain.syntax.rholang.Absyn.PPtPar p, A arg) { return visitDefault(p, arg); }

    public R visitDefault(coop.rchain.syntax.rholang.Absyn.PPattern p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* CPattern */
    public R visit(coop.rchain.syntax.rholang.Absyn.CPtVar p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.CPtQuote p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(coop.rchain.syntax.rholang.Absyn.CPattern p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* PatternBind */
    public R visit(coop.rchain.syntax.rholang.Absyn.PtBind p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(coop.rchain.syntax.rholang.Absyn.PatternBind p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* PatternPatternMatch */
    public R visit(coop.rchain.syntax.rholang.Absyn.PtBranch p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(coop.rchain.syntax.rholang.Absyn.PatternPatternMatch p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* ValPattern */
    public R visit(coop.rchain.syntax.rholang.Absyn.VPtStruct p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.VPtInt p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.VPtDbl p, A arg) { return visitDefault(p, arg); }
    public R visit(coop.rchain.syntax.rholang.Absyn.VPtStr p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(coop.rchain.syntax.rholang.Absyn.ValPattern p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }

}
