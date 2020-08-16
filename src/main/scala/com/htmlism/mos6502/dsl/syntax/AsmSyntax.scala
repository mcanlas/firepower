package com.htmlism.mos6502.dsl
package syntax

trait AsmSyntax {
  def label(s: String)(implicit ctx: AssemblyContext): Unit =
    ctx
      .push(s)
}
