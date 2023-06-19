package com.htmlism.mos6502.dsl
package syntax

trait DefinitionGroupSyntax:
  def define[A <: Address: DefinitionValue](name: String, x: A)(using ctx: DefinitionGroupContext): Definition[A] =
    val definition =
      Definition(name, x)

    ctx
      .push(Definition(name, x))

    definition

  def constant(name: String, x: Int)(using ctx: DefinitionGroupContext): Definition[Int] =
    val definition =
      Definition(name, x)

    ctx
      .push(Definition(name, x))

    definition
