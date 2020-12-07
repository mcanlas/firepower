package com.htmlism.mos6502.dsl
package syntax

trait AsmDocSyntax {
  def asm(f: AssemblyContext => Unit)(implicit ctx: AsmDocumentContext): Unit = {
    val asmCtx: AssemblyContext =
      new AssemblyContext

    f(asmCtx)

    ctx
      .addJumpRegistry(asmCtx.getJumps)

    ctx
      .push(asmCtx.toFragment)
  }

  def group[A](s: String)(f: DefinitionGroupContext => A)(implicit ctx: AsmDocumentContext): Unit = {
    val g: DefinitionGroupContext =
      new DefinitionGroupContext

    f(g)

    ctx
      .push(g.toGroup(s))
  }

  def enum[A: EnumAsm: Mapping](implicit ctx: AsmDocumentContext): Unit =
    mapping

  def bitField[A: BitField: Mapping](implicit ctx: AsmDocumentContext): Unit =
    mapping

  def mapping[A](implicit ctx: AsmDocumentContext, ev: Mapping[A]): Unit = {
    val xs =
      ev.all
        .map(x => ev.label(x) -> ev.value(x))
        .toList

    val grp =
      DefinitionGroup(
        ev.definitionGroupComment,
        xs
          .map { case (s, n) =>
            Definition(s, n)
          }
      )

    ctx
      .push(grp)
  }
}
