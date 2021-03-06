//------------------------------------------------------------------------------
// File: ParseCardinalityExpr.scala
//
// Short version for non-lawyers:
//
// The ShEx Lite Project is dual-licensed under GNU 3.0 and
// MIT terms.
//
// Longer version:
//
// Copyrights in the ShEx Lite project are retained by their contributors. No
// copyright assignment is required to contribute to the ShEx Lite project.
//
// Some files include explicit copyright notices and/or license notices.
// For full authorship information, see the version control history.
//
// Except as otherwise noted (below and/or in individual files), ShEx Lite is
// licensed under the GNU, Version 3.0 <LICENSE-GNU> or
// <https://choosealicense.com/licenses/gpl-3.0/> or the MIT license
// <LICENSE-MIT> or <http://opensource.org/licenses/MIT>, at your option.
// In case of incompatibility between project licenses, GNU/GPLv3 will be
// applied.
//
// The ShEx Lite Project includes packages written by third parties.
//------------------------------------------------------------------------------

package es.weso.shexlc.parse

import es.weso.shexlc.internal.CompilationContext
import es.weso.shexlc.parse.ast.expr.CardinalityExpr
import es.weso.shexlc.parse.ast.Position
import es.weso.shexlc.parse.generated.ShexLiteParser
import org.antlr.v4.runtime.misc.Interval

/**
  * This parser extracts a cardinality expression from the parser cardinality
  * context.
  *
  * @author Guillermo Facundo Colunga
  * @param ctx     of the parser.
  * @param visitor that will propagate any needed call.
  */
class ParseCardinalityExpr(
  ctx: ShexLiteParser.Cardinality_exprContext,
  visitor: ASTBuilderParser,
  ccontext: CompilationContext
) extends HasParseResult[CardinalityExpr] {

  override def getParseResult: CardinalityExpr = {

    val sourceName = ccontext.getInputContext.getSourceName
    val line       = ctx.start.getLine
    val column     = ctx.start.getCharPositionInLine
    val pos        = Position.pos(sourceName, line, column)
    val tokenRange = new Interval(ctx.start.getStartIndex, ctx.stop.getStopIndex)
    val content    = ccontext.getInputContext.getText(tokenRange)

    if (ctx.min == null) {
      // If there is no min value is because it is a built in cardinality.
      ctx.getText match {
        case "*" =>
          new CardinalityExpr(
            pos,
            tokenRange,
            content,
            CardinalityExpr.MinValue,
            CardinalityExpr.MaxValue
          )
        case "+" =>
          new CardinalityExpr(
            pos,
            tokenRange,
            content,
            CardinalityExpr.MinValue + 1,
            CardinalityExpr.MaxValue
          )
        case "?" => new CardinalityExpr(pos, tokenRange, content, 0, 1)
      }
    } else {
      // If it is not a built in cardinality thn we have to check which case
      // of the allowed ones is.
      if (ctx.max != null) {
        new CardinalityExpr(
          pos,
          tokenRange,
          content,
          Integer.parseInt(ctx.min.getText),
          Integer.parseInt(ctx.max.getText)
        )
      } else {
        if (ctx.getText.contains(",")) {
          new CardinalityExpr(
            pos,
            tokenRange,
            content,
            Integer.parseInt(ctx.min.getText),
            Int.MaxValue
          )
        } else {
          new CardinalityExpr(
            pos,
            tokenRange,
            content,
            Integer.parseInt(ctx.min.getText),
            Integer.parseInt(ctx.min.getText)
          )
        }
      }
    }
  }
}
