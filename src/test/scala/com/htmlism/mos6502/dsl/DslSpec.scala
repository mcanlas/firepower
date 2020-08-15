package com.htmlism.mos6502.dsl

import org.scalatest.flatspec._
import org.scalatest.matchers._

class DslSpec extends AnyFlatSpec with should.Matchers {

  "the dsl" should "compile" in {
    val doc =
      asmDoc { implicit ctx =>
        group("snake things") { implicit g =>
          define("snakeBodyStart", 0x12.z)
          define("snakeDirection", 0x02.z)
          define("snakeLength", 0x03.z)
        }

        group("ASCII values of keys controlling the snake") { implicit g =>
          define("ASCII_w", 0x77.z)
          define("ASCII_a", 0x61.z)
          define("ASCII_s", 0x73.z)
          define("ASCII_d", 0x64.z)
        }

        group("System variables") { implicit g =>
          define("sysRandom", 0xfe.z)
          define("sysLastKey", 0xff.z)
        }
      }

    doc shouldEqual AsmDocument(List(
      DefinitionGroup("snake things", List(
        Definition("snakeBodyStart", 0x12.z),
        Definition("snakeDirection", 0x02.z),
        Definition("snakeLength", 0x03.z)
      )),
      DefinitionGroup("ASCII values of keys controlling the snake", List(
        Definition("ASCII_w", 0x77.z),
        Definition("ASCII_a", 0x61.z),
        Definition("ASCII_s", 0x73.z),
        Definition("ASCII_d", 0x64.z)
      )),
      DefinitionGroup("System variables", List(
        Definition("sysRandom", 0xfe.z),
        Definition("sysLastKey", 0xff.z)
      ))
    ))
  }
}
