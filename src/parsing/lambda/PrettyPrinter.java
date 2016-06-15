package parsing.lambda;
import parsing.lambda.Absyn.*;

public class PrettyPrinter
{
  //For certain applications increasing the initial size of the buffer may improve performance.
  private static final int INITIAL_BUFFER_SIZE = 128;
  private static final int INDENT_WIDTH = 2;
  //You may wish to change the parentheses used in precedence.
  private static final String _L_PAREN = new String("(");
  private static final String _R_PAREN = new String(")");
  //You may wish to change render
  private static void render(String s)
  {
    if (s.equals("{"))
    {
       buf_.append("\n");
       indent();
       buf_.append(s);
       _n_ = _n_ + INDENT_WIDTH;
       buf_.append("\n");
       indent();
    }
    else if (s.equals("(") || s.equals("["))
       buf_.append(s);
    else if (s.equals(")") || s.equals("]"))
    {
       backup();
       buf_.append(s);
       buf_.append(" ");
    }
    else if (s.equals("}"))
    {
       int t;
       _n_ = _n_ - INDENT_WIDTH;
       for(t=0; t<INDENT_WIDTH; t++) {
         backup();
       }
       buf_.append(s);
       buf_.append("\n");
       indent();
    }
    else if (s.equals(","))
    {
       backup();
       buf_.append(s);
       buf_.append(" ");
    }
    else if (s.equals(";"))
    {
       backup();
       buf_.append(s);
       buf_.append("\n");
       indent();
    }
    else if (s.equals("")) return;
    else
    {
       buf_.append(s);
       buf_.append(" ");
    }
  }


  //  print and show methods are defined for each category.
  public static String print(parsing.lambda.Absyn.Expr foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(parsing.lambda.Absyn.Expr foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(parsing.lambda.Absyn.ListExpr foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(parsing.lambda.Absyn.ListExpr foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String print(parsing.lambda.Absyn.Value foo)
  {
    pp(foo, 0);
    trim();
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  public static String show(parsing.lambda.Absyn.Value foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_.delete(0,buf_.length());
    return temp;
  }
  /***   You shouldn't need to change anything beyond this point.   ***/

  private static void pp(parsing.lambda.Absyn.Expr foo, int _i_)
  {
    if (foo instanceof parsing.lambda.Absyn.EVar)
    {
       parsing.lambda.Absyn.EVar _evar = (parsing.lambda.Absyn.EVar) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_evar.ident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof parsing.lambda.Absyn.EVal)
    {
       parsing.lambda.Absyn.EVal _eval = (parsing.lambda.Absyn.EVal) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_eval.value_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof parsing.lambda.Absyn.EAbs)
    {
       parsing.lambda.Absyn.EAbs _eabs = (parsing.lambda.Absyn.EAbs) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("(");
       pp(_eabs.ident_, 0);
       pp(_eabs.expr_, 0);
       render(")");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof parsing.lambda.Absyn.EApp)
    {
       parsing.lambda.Absyn.EApp _eapp = (parsing.lambda.Absyn.EApp) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_eapp.expr_1, 0);
       pp(_eapp.expr_2, 0);
       render("apply");
       if (_i_ > 0) render(_R_PAREN);
    }
    else     if (foo instanceof parsing.lambda.Absyn.ETuple)
    {
       parsing.lambda.Absyn.ETuple _etuple = (parsing.lambda.Absyn.ETuple) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("(");
       pp(_etuple.listexpr_, 0);
       render(")");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(parsing.lambda.Absyn.ListExpr foo, int _i_)
  {
     for (java.util.Iterator<Expr> it = foo.iterator(); it.hasNext();)
     {
       pp(it.next(), _i_);
       if (it.hasNext()) {
         render(",");
       } else {
         render("");
       }
     }  }

  private static void pp(parsing.lambda.Absyn.Value foo, int _i_)
  {
    if (foo instanceof parsing.lambda.Absyn.VInt)
    {
       parsing.lambda.Absyn.VInt _vint = (parsing.lambda.Absyn.VInt) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_vint.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }


  private static void sh(parsing.lambda.Absyn.Expr foo)
  {
    if (foo instanceof parsing.lambda.Absyn.EVar)
    {
       parsing.lambda.Absyn.EVar _evar = (parsing.lambda.Absyn.EVar) foo;
       render("(");
       render("EVar");
       sh(_evar.ident_);
       render(")");
    }
    if (foo instanceof parsing.lambda.Absyn.EVal)
    {
       parsing.lambda.Absyn.EVal _eval = (parsing.lambda.Absyn.EVal) foo;
       render("(");
       render("EVal");
       sh(_eval.value_);
       render(")");
    }
    if (foo instanceof parsing.lambda.Absyn.EAbs)
    {
       parsing.lambda.Absyn.EAbs _eabs = (parsing.lambda.Absyn.EAbs) foo;
       render("(");
       render("EAbs");
       sh(_eabs.ident_);
       sh(_eabs.expr_);
       render(")");
    }
    if (foo instanceof parsing.lambda.Absyn.EApp)
    {
       parsing.lambda.Absyn.EApp _eapp = (parsing.lambda.Absyn.EApp) foo;
       render("(");
       render("EApp");
       sh(_eapp.expr_1);
       sh(_eapp.expr_2);
       render(")");
    }
    if (foo instanceof parsing.lambda.Absyn.ETuple)
    {
       parsing.lambda.Absyn.ETuple _etuple = (parsing.lambda.Absyn.ETuple) foo;
       render("(");
       render("ETuple");
       render("[");
       sh(_etuple.listexpr_);
       render("]");
       render(")");
    }
  }

  private static void sh(parsing.lambda.Absyn.ListExpr foo)
  {
     for (java.util.Iterator<Expr> it = foo.iterator(); it.hasNext();)
     {
       sh(it.next());
       if (it.hasNext())
         render(",");
     }
  }

  private static void sh(parsing.lambda.Absyn.Value foo)
  {
    if (foo instanceof parsing.lambda.Absyn.VInt)
    {
       parsing.lambda.Absyn.VInt _vint = (parsing.lambda.Absyn.VInt) foo;
       render("(");
       render("VInt");
       sh(_vint.integer_);
       render(")");
    }
  }


  private static void pp(Integer n, int _i_) { buf_.append(n); buf_.append(" "); }
  private static void pp(Double d, int _i_) { buf_.append(d); buf_.append(" "); }
  private static void pp(String s, int _i_) { buf_.append(s); buf_.append(" "); }
  private static void pp(Character c, int _i_) { buf_.append("'" + c.toString() + "'"); buf_.append(" "); }
  private static void sh(Integer n) { render(n.toString()); }
  private static void sh(Double d) { render(d.toString()); }
  private static void sh(Character c) { render(c.toString()); }
  private static void sh(String s) { printQuoted(s); }
  private static void printQuoted(String s) { render("\"" + s + "\""); }
  private static void indent()
  {
    int n = _n_;
    while (n > 0)
    {
      buf_.append(" ");
      n--;
    }
  }
  private static void backup()
  {
     if (buf_.charAt(buf_.length() - 1) == ' ') {
      buf_.setLength(buf_.length() - 1);
    }
  }
  private static void trim()
  {
     while (buf_.length() > 0 && buf_.charAt(0) == ' ')
        buf_.deleteCharAt(0); 
    while (buf_.length() > 0 && buf_.charAt(buf_.length()-1) == ' ')
        buf_.deleteCharAt(buf_.length()-1);
  }
  private static int _n_ = 0;
  private static StringBuilder buf_ = new StringBuilder(INITIAL_BUFFER_SIZE);
}
