//------------------------------------------------------------------------------
// File: BaseDefStmt.scala
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

package es.weso.shexlc.parse.ast.stmt

import es.weso.shexlc.parse.ast.Position
import es.weso.shexlc.parse.ast.expr.{Expression, LiteralIRIValueExpr}
import es.weso.shexlc.parse.ast.visitor.ASTGenericWalker
import org.antlr.v4.runtime.misc.Interval

/**
  * The base definition statement is the unique association of an expression
  * to the base value of the schema. An
  * schema must have one unique base, this is checked by the es.weso.shexlc
  * .semantic analyzer. Also the expression must conform to an
  * iri but that it is also check later in the es.weso.shexlc.semantic. This
  * way it allows future addition of more expressions that
  * can be associated to a base value.
  *
  * @author Guillermo Facundo Colunga.
  * @param expression that is assigned to the Base Definition Statement.
  */
class BaseDefStmt(
  position: Position,
  tokenRange: Interval,
  content: String,
  val expression: Expression
) extends DefinitionStmt(position, tokenRange, content) {

  // Override default methods to indicate that this is a Base Definition
  // Statement.
  override def isBaseDefStmt: Boolean     = true
  override def asBaseDefStmt: BaseDefStmt = this

  /**
    * Gets the position object that points to the source file.
    *
    * @return a position object containing the position in the source file.
    */
  override def getPosition: Position = position

  /**
    * Gets the range of tokens from the source on which the node was generated.
    *
    * @return the range of tokens from the source on which the node was
    *         generated.
    */
  override def getRange: Interval = tokenRange

  /**
    * Gets the content of the node as a String, for example for a node that
    * contains the assignment of a and 3 the content
    * would be 'a = 3'.
    *
    * @return the content of the node as a String.
    */
  override def getContent: String = content

  /**
    * Accept method for visitor support.
    *
    * @param visitor the visitor implementation.
    * @param param   is the parameter passed to the visitor (of type A).
    * @tparam TP is the type the user parameter passed to the visitor.
    * @tparam TR is the type of the return value of the visitor.
    * @return the result of the visit (of type TR).
    */
  override def accept[TP, TR](visitor: ASTGenericWalker[TP, TR], param: TP): TR = {
    visitor.visit(this, param)
  }
}

object BaseDefStmt {

  val DEFAULT_BASE = "<es.weso.shexlc.internal://base>"

  val DEFAULT_LABEL = "base"

  val defaultBaseDefStmt = new BaseDefStmt(
    Position.HOME,
    new Interval(0, 0),
    "base = <es.weso.shexlc" + ".internal://base>",
    new LiteralIRIValueExpr(
      Position.HOME,
      new Interval(0, 0),
      "<es.weso.shexlc.internal://base>",
      DEFAULT_BASE
    )
  )
}
