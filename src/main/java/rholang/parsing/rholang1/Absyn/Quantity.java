package rholang.parsing.rholang1.Absyn; // Java Package generated by the BNF Converter.

public abstract class Quantity implements java.io.Serializable {
  public abstract <R,A> R accept(Quantity.Visitor<R,A> v, A arg);
  public interface Visitor <R,A> {
    public R visit(rholang.parsing.rholang1.Absyn.QInt p, A arg);
    public R visit(rholang.parsing.rholang1.Absyn.QDouble p, A arg);

  }

}
