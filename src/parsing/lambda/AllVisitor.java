package parsing.lambda;

import parsing.lambda.Absyn.*;

/** BNFC-Generated All Visitor */
public interface AllVisitor<R,A> extends
  parsing.lambda.Absyn.Expr.Visitor<R,A>,
  parsing.lambda.Absyn.Value.Visitor<R,A>
{}
