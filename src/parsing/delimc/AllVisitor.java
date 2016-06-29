package parsing.delimc;

import parsing.delimc.Absyn.*;

/** BNFC-Generated All Visitor */
public interface AllVisitor<R,A> extends
  parsing.delimc.Absyn.Expr.Visitor<R,A>,
  parsing.delimc.Absyn.Tuple.Visitor<R,A>,
  parsing.delimc.Absyn.Value.Visitor<R,A>,
  parsing.delimc.Absyn.Type.Visitor<R,A>,
  parsing.delimc.Absyn.TType.Visitor<R,A>
{}
