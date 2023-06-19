package com.htmlism.mos6502.dsl
package syntax

trait AsmDocSyntax:
  def asm(f: AssemblyContext => Unit)(using ctx: AsmDocumentContext): Unit =
    val asmCtx: AssemblyContext =
      new AssemblyContext

    f(asmCtx)

    ctx
      .addJumpRegistry(asmCtx.getJumps)

    ctx
      .push(asmCtx.toFragment)

  def group[A](s: String)(f: DefinitionGroupContext => A)(using ctx: AsmDocumentContext): Unit =
    val g: DefinitionGroupContext =
      new DefinitionGroupContext

    f(g)

    ctx
      .push(g.toGroup(s))

  def enumAsm[A: EnumAsm: Mapping](using ctx: AsmDocumentContext): Unit =
    val _ = implicitly[EnumAsm[A]] // TODO unused

    mapping

  def bitField[A: BitField: Mapping](using ctx: AsmDocumentContext): Unit =
    val _ = implicitly[BitField[A]] // TODO unused

    mapping

  def mapping[A](using ctx: AsmDocumentContext, ev: Mapping[A]): Unit =
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
