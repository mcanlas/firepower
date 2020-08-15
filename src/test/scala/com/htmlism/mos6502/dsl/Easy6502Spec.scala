package com.htmlism.mos6502.dsl

import cats.implicits._
import org.scalatest._
import flatspec._
import matchers._

class Easy6502Spec extends AnyFlatSpec with should.Matchers {

  "the three pixel demo" should "have the right instructions" in {
    val doc =
      withAssemblyContext { implicit ctx =>
        val scr =
          IndexedAddressCollection[Color](0x0200, "screen")

        scr.write(0, Color.White)
        scr.write(1, Color.Green)
        scr.write(2, Color.Orange)
      }

    doc.triplets shouldEqual List(
      ("LDA", "#$01".some, "write White to screen (0)".some),
      ("STA", "$0200".some, "".some),
      ("LDA", "#$05".some, "write Green to screen (1)".some),
      ("STA", "$0201".some, "".some),
      ("LDA", "#$08".some, "write Orange to screen (2)".some),
      ("STA", "$0202".some, "".some)
    )

    doc.printOut()
  }

  def withAssemblyContext(f: AssemblyContext => Unit): AssemblyContext = {
    val ctx: AssemblyContext =
      new AssemblyContext

    f(ctx)

    ctx
  }
}
