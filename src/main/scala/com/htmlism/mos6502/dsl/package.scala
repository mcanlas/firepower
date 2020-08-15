package com.htmlism.mos6502

package object dsl {
  def asmDoc(f: AsmDocumentContext => Unit): AsmDocument = {
    val ctx: AsmDocumentContext =
      new AsmDocumentContext

    f(ctx)

    ctx
      .toDoc
  }

  def group(f: DefineGroupContext => Unit)(implicit ctx: AsmDocumentContext): Unit = {
    val g: DefineGroupContext =
      new DefineGroupContext

    f(g)

    ctx
      .push(g.toGroup)
  }

  def define[A : Operand](name: String, x: A)(implicit ctx: DefineGroupContext): Unit =
    ctx
      .push(Definition(name, x))

  implicit class AddressOps(n: Int) {
    def z: ZeroAddress =
      ZeroAddress(n)

    def addr: GlobalAddress =
      GlobalAddress(n)
  }
}
