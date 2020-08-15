package com.htmlism.mos6502

package object dsl {
  def asmDoc(f: AsmDocumentContext => Unit): AsmDocument = {
    val ctx: AsmDocumentContext =
      new AsmDocumentContext

    f(ctx)

    ctx
      .toDoc
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

  def define[A <: Address : Operand](name: String, x: A)(implicit ctx: DefinitionGroupContext): Definition[A] = {
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
