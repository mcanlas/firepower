package com.htmlism.mos6502

package object dsl {
  def asmDoc(f: AsmDocumentContext => Unit): AsmDocument = {
    val ctx: AsmDocumentContext =
      new AsmDocumentContext

    f(ctx)

    ctx.toDoc
  }

  def group[A](s: String)(f: DefinitionGroupContext => A)(implicit ctx: AsmDocumentContext): A = {
    val g: DefinitionGroupContext =
      new DefinitionGroupContext

    val ret =
      f(g)

    ctx
      .push(g.toGroup(s))

    ret
  }

  def enum[A](implicit ctx: AsmDocumentContext, ev: EnumAsm[A]): Unit = {
    val (_, xs) =
      ev.labels
        .foldLeft(0 -> List.empty[(String, Int)]) {
          case ((next, acc), s) =>
            (next + 1) -> (acc :+ (s -> next))
        }

    val grp =
      DefinitionGroup(
        ev.comment,
        xs
          .map {
            case (s, n) =>
              Definition(s, n)
          }
      )

    ctx
      .push(grp)
  }

  def bitField[A](implicit ctx: AsmDocumentContext, ev: BitField[A]): Unit = {
    val (_, xs) =
      ev.labels
        .foldLeft(1 -> List.empty[(String, Int)]) {
          case ((next, acc), s) =>
            (next << 1) -> (acc :+ (s -> next))
        }

    val grp =
      DefinitionGroup(
        ev.comment,
        xs
          .map {
            case (s, n) =>
              Definition(s, n)
          }
      )

    ctx
      .push(grp)
  }

  def define[A <: Address: Operand](name: String, x: A)(implicit ctx: DefinitionGroupContext): Definition[A] = {
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

  implicit class AddressOps(n: Int) {
    def z: ZeroAddress =
      ZeroAddress(n)

    def addr: GlobalAddress =
      GlobalAddress(n)
  }
}
