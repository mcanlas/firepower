package com.htmlism.mos6502

package object dsl extends syntax.DefinitionGroupSyntax with syntax.AsmDocSyntax with syntax.AsmSyntax {
  def asmDoc(f: AsmDocumentContext => Unit): AsmDocument = {
    val ctx: AsmDocumentContext =
      new AsmDocumentContext

    f(ctx)

    ctx.toDoc
  }

  implicit class AddressOps(n: Int) {
    def z: ZeroAddress =
      ZeroAddress(n)

    def addr: GlobalAddress =
      GlobalAddress(n)
  }
}
