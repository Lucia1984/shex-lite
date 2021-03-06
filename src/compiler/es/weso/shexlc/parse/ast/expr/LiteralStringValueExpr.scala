//------------------------------------------------------------------------------
// File: LiteralStringValueExpr.scala
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

package es.weso.shexlc.parse.ast.expr

import es.weso.shexlc.parse.ast.Position
import es.weso.shexlc.parse.ast.visitor.ASTGenericWalker
import org.antlr.v4.runtime.misc.Interval

/**
  * A Literal String Value Expression is a literal that contains an String
  * value. It is used to store Strings that appear
  * in the source code.
  *
  * @author Guillermo Facundo Colunga
  */
class LiteralStringValueExpr(
  position: Position,
  tokenRange: Interval,
  content: String,
  val value: String
) extends LiteralExpr(position, tokenRange, content)
    with ConstraintValidValueSetExpr {

  // Override default methods to indicate that this is a Literal String Value
  // Expression.
  override def isLiteralStringValueExpr: Boolean = true

  override def asLiteralStringValueExpr: LiteralStringValueExpr = this

  /**
    * Gets the position object that points to the source file.
    *
    * @return a position object containing the position in the source file.
    */
  override def getPosition: Position = position

  /**
    * Gets the content of the node as a String, for example for a node that
    * contains the assignment of a and 3 the content would be 'a = 3'.
    *
    * @return the content of the node as a String.
    */
  override def getRange: Interval = tokenRange

  /**
    * Gets the content of the node as a String, for example for a node that
    * contains the assignment of a and 3 the content would be 'a = 3'.
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
