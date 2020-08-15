package com.htmlism.mos6502.dsl

import cats.data.NonEmptyList
import org.scalatest.flatspec._
import org.scalatest.matchers._

class DslSpec extends AnyFlatSpec with should.Matchers {

  "the dsl" should "compile" in {
    val doc =
      asmDoc { implicit ctx =>
        group("snake things") { implicit g =>
          (define("snakeBodyStart", 0x12.z), define("snakeDirection", 0x02.z), define("snakeLength", 0x03.z))
        }

        group("ASCII values of keys controlling the snake") { implicit g =>
          (define("ASCII_w", 0x77.z), define("ASCII_a", 0x61.z), define("ASCII_s", 0x73.z), define("ASCII_d", 0x64.z))
        }

        group("System variables") { implicit g =>
          (define("sysRandom", 0xfe.z), define("sysLastKey", 0xff.z))
        }

        group("constants test") { implicit g =>
          (constant("margin", 16), constant("secret", 42))
        }

        ()
      }

    doc shouldEqual AsmDocument(
      List(
        DefinitionGroup(
          "snake things",
          List(
            Definition("snakeBodyStart", 0x12.z),
            Definition("snakeDirection", 0x02.z),
            Definition("snakeLength", 0x03.z)
          )
        ),
        DefinitionGroup(
          "ASCII values of keys controlling the snake",
          List(
            Definition("ASCII_w", 0x77.z),
            Definition("ASCII_a", 0x61.z),
            Definition("ASCII_s", 0x73.z),
            Definition("ASCII_d", 0x64.z)
          )
        ),
        DefinitionGroup(
          "System variables",
          List(
            Definition("sysRandom", 0xfe.z),
            Definition("sysLastKey", 0xff.z)
          )
        ),
        DefinitionGroup(
          "constants test",
          List(
            Definition("margin", 16),
            Definition("secret", 42)
          )
        )
      )
    )
  }

  "enum" should "compile" in {
    val doc =
      asmDoc { implicit ctx =>
        enum[Foo]
      }

    doc shouldEqual AsmDocument(
      List(
        DefinitionGroup(
          "foo as enum",
          List(
            Definition("courage", 0),
            Definition("wisdom", 1),
            Definition("power", 2)
          )
        )
      )
    )
  }

  "bit field" should "compile" in {
    val doc =
      asmDoc { implicit ctx =>
        bitField[Foo]
      }

    doc shouldEqual AsmDocument(
      List(
        DefinitionGroup(
          "foo as bit field",
          List(
            Definition("up", 0x01),
            Definition("down", 0x02),
            Definition("left", 0x04),
            Definition("right", 0x08)
          )
        )
      )
    )
  }
}

class Foo

object Foo {
  implicit val enumFoo: EnumAsm[Foo] =
    new EnumAsm[Foo] {
      def comment: String =
        "foo as enum"

      def labels: NonEmptyList[String] =
        NonEmptyList.of("courage", "wisdom", "power")

      def label(x: Foo): String =
        "fooNotEnum"

      def comment(x: Foo): String =
        "Foo not an enum"
    }

  implicit val bitFieldFoo: BitField[Foo] =
    new BitField[Foo] {
      def comment: String =
        "foo as bit field"

      def labels: NonEmptyList[String] =
        NonEmptyList.of("up", "down", "left", "right")

      def label(x: Foo): String =
        "fooNotBitField"

      def comment(x: Foo): String =
        "Foo not a bit field"
    }
}
