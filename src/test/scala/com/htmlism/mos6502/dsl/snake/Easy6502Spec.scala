package com.htmlism.mos6502.dsl
package snake

import cats.syntax.all._
import org.scalatest.flatspec._
import org.scalatest.matchers._

import com.htmlism.mos6502.model._

class Easy6502Spec extends AnyFlatSpec with should.Matchers:

  "the three pixel demo" should "have the right instructions" in:
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

  "define style dsl" should "compile" in:
    val doc =
      asmDoc { implicit ctx =>
        enumAsm[Color]

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

  "loop demo" should "compile" in:
    val doc =
      asmDoc { implicit ctx =>
        asm { implicit a =>
          registers.X.loop("incrementing", 2 upTo 5) { implicit a =>
            a.push(INY)
          }
        }
      }

    println(
      doc.toAsm
    )

  "snake" should "compile" in:
    val sysRandom  = VolatileDevice[Int]("sysRandom", 0xfe.z)
    val sysLastKey = VolatileDevice[AsciiValue]("sysLastKey", 0xff.z)

    val appleLocation  = ReadWriteLocation[Int]("appleLocation", 0x00.z)
    val snakeDirection = ReadWriteLocation[Direction]("snakeDirection", 0x02.z)
    val snakeLength    = ReadWriteLocation[Int]("snakeLength", 0x03.z)

    val initSnake =
      sub("initSnake") { implicit a =>
        snakeDirection.write(Right)
        snakeLength.write(4) // 2 * 2
      }

    val generateApplePosition =
      sub("generateApplePosition") { implicit a =>
        registers.X.incr
      }

    val init =
      sub("init") { implicit a =>
        jump(initSnake)
        jump(generateApplePosition)
      }

    val readKeys =
      sub("readKeys") { implicit a =>
        val _ = a
      }

    val checkCollision =
      sub("checkCollision") { implicit a =>
        val _ = a
      }

    val updateSnake =
      sub("updateSnake") { implicit a =>
        val _ = a
      }

    val drawApple =
      sub("drawApple") { implicit a =>
        val _ = a
      }

    val drawSnake =
      sub("drawSnake") { implicit a =>
        val _ = a
      }

    val spinWheels =
      sub("spinWheels") { implicit a =>
        val _ = a
      }

    val loop =
      sub("loop") { implicit a =>
        jump(readKeys)
        jump(checkCollision)
        jump(updateSnake)
        jump(drawApple)
        jump(drawSnake)
        jump(spinWheels)
      }

    val doc =
      asmDoc { implicit ctx =>
        group("6502js") { implicit g =>
          g.push(sysRandom)
          g.push(sysLastKey)
        }

        group("snake") { implicit g =>
          g.push(appleLocation)
          g.push(snakeDirection)
          g.push(snakeLength)
        }

        bitField[Direction]
        mapping[AsciiValue]

        asm { implicit a =>
          sysRandom.read
          sysLastKey.read
          appleLocation.read

          jump(init)
          jump(loop)
        }
      }

    println(
      doc.toAsm
    )

  def withAssemblyContext(f: AssemblyContext => Unit): AssemblyContext =
    val ctx: AssemblyContext =
      new AssemblyContext

    f(ctx)

    ctx
