package com.htmlism.mos6502.dsl

import cats.implicits._
import org.scalatest.flatspec._
import org.scalatest.matchers._

import com.htmlism.mos6502.model._

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
      ("LDA", "#white".some, "write White to screen (0)".some),
      ("STA", "$0200".some, "".some),
      ("LDA", "#green".some, "write Green to screen (1)".some),
      ("STA", "$0201".some, "".some),
      ("LDA", "#orange".some, "write Orange to screen (2)".some),
      ("STA", "$0202".some, "".some)
    )

    doc.printOut()
  }

  "define style dsl" should "compile" in {
    val doc =
      asmDoc { implicit ctx =>
        enum[Color]

        asm { implicit a =>
          val scr =
            IndexedAddressCollection[Color](0x0200, "screen")

          scr.write(0, Color.White)
          scr.write(1, Color.Green)
          scr.write(2, Color.Orange)
        }
      }

    println(
      doc.toAsm
    )
  }

  "loop demo" should "compile" in {
    val doc =
      asmDoc { implicit ctx =>
        asm { implicit a =>
          registers.X.upTo("incrementing", 2, 5) { implicit a =>
            a.push(INY)
          }
        }
      }

    println(
      doc.toAsm
    )
  }

  def withAssemblyContext(f: AssemblyContext => Unit): AssemblyContext = {
    val ctx: AssemblyContext =
      new AssemblyContext

    f(ctx)

    ctx
  }
}
