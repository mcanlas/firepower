package com.htmlism.mos6502

package object dsl {
  def asmDoc(f: AsmDocumentContext => Unit): AsmDocument = {
    val ctx: AsmDocumentContext =
      new AsmDocumentContext

    f(ctx)

    ctx
      .toDoc
  }

  def group(s: String)(f: DefinitionGroupContext => Unit)(implicit ctx: AsmDocumentContext): Unit = {
    val g: DefinitionGroupContext =
      new DefinitionGroupContext

    f(g)

    ctx
      .push(g.toGroup(s))
  }

  def define[A : Operand](name: String, x: A)(implicit ctx: DefinitionGroupContext): Unit =
    ctx
      .push(Definition(name, x))

  implicit class AddressOps(n: Int) {
    def z: ZeroAddress =
      ZeroAddress(n)

    def addr: GlobalAddress =
      GlobalAddress(n)
  }
}
