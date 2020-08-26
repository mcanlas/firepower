package com.htmlism.mos6502.dsl
package syntax

trait DefinitionGroupSyntax {
  def define[A <: Address: DefinitionValue](name: String, x: A)(implicit ctx: DefinitionGroupContext): Definition[A] = {
    val definition =
      Definition(name, x)

    ctx
      .push(Definition(name, x))

    definition
  }

  def constant(name: String, x: Int)(implicit ctx: DefinitionGroupContext): Definition[Int] = {
    val definition =
      Definition(name, x)

    ctx
      .push(Definition(name, x))

    definition
  }
}
