package com.htmlism.mos6502.dsl
package syntax

import com.htmlism.mos6502.model._

trait AsmSyntax {
  def label(s: String)(implicit ctx: AssemblyContext): Unit =
    ctx
      .label(s)

  def sub(s: String)(f: AssemblyContext => Unit): Subroutine = {
    val ctx: AssemblyContext =
      new AssemblyContext

    f(ctx)

    Subroutine(s, ctx.toFragment)
  }

  def jump(s: Subroutine)(implicit ctx: AssemblyContext): Unit = {
    ctx
      .addJump(s)

    ctx
      .branch(JSR, s.name)
  }
}
