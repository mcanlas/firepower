package com.htmlism.mos6502.dsl

import org.scalatest.flatspec._
import org.scalatest.matchers._

class DslSpec extends AnyFlatSpec with should.Matchers {

  "the dsl" should "compile" in {
    val doc =
      asmDoc { implicit ctx =>
        group { implicit g =>
          define("snakeBodyStart", 0x12.z)
          define("snakeDirection", 0x02.z)
          define("snakeLength", 0x03.z)
        }

        group { implicit g =>
          define("ASCII_w", 0x77)
          define("ASCII_a", 0x61)
          define("ASCII_s", 0x73)
          define("ASCII_d", 0x64)
        }
      }

    doc shouldEqual AsmDocument(List(
      DefineGroup(List(
        Definition("snakeBodyStart", 0x12.z),
        Definition("snakeDirection", 0x02.z),
        Definition("snakeLength", 0x03.z)
      )),
      DefineGroup(List(
        Definition("ASCII_w", 0x77),
        Definition("ASCII_a", 0x61),
        Definition("ASCII_s", 0x73),
        Definition("ASCII_d", 0x64)
      ))
    ))
  }
}
