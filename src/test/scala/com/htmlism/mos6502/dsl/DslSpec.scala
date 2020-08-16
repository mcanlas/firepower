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
        enum[Triforce]
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
        bitField[Direction]
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

  "mapping" should "compile" in {
    val doc =
      asmDoc { implicit ctx =>
        mapping[Direction]
      }

    doc shouldEqual AsmDocument(
      List(
        DefinitionGroup(
          "foo as a mapping",
          List(
            Definition("up", 0x77),
            Definition("down", 0x61),
            Definition("left", 0x73),
            Definition("right", 0x64)
          )
        )
      )
    )
  }
}

sealed trait Triforce

case object Courage extends Triforce
case object Wisdom  extends Triforce
case object Power   extends Triforce

object Triforce {
  implicit val enumTriforce: EnumAsm[Triforce] =
    new EnumAsm[Triforce] {
      def comment: String =
        "foo as enum"

      def all: NonEmptyList[Triforce] =
        NonEmptyList.of(Courage, Wisdom, Power)

      def label(x: Triforce): String =
        x.toString.toLowerCase

      def comment(x: Triforce): String =
        x.toString
    }
}

sealed trait Direction

case object Up    extends Direction
case object Down  extends Direction
case object Left  extends Direction
case object Right extends Direction

object Direction {
  implicit val bitFieldDirection: BitField[Direction] =
    new BitField[Direction] {
      def comment: String =
        "foo as bit field"

      def all: NonEmptyList[Direction] =
        NonEmptyList.of(Up, Down, Left, Right)

      def label(x: Direction): String =
        x.toString.toLowerCase

      def comment(x: Direction): String =
        x.toString
    }

  implicit val mappingDirection: Mapping[Direction] =
    new Mapping[Direction] {
      def comment: String =
        "foo as a mapping"

      def all: NonEmptyList[Direction] =
        NonEmptyList.of(Up, Down, Left, Right)

      def value(x: Direction): Int =
        x match {
          case Up    => 0x77
          case Down  => 0x61
          case Left  => 0x73
          case Right => 0x64
        }

      def label(x: Direction): String =
        x.toString.toLowerCase

      def comment(x: Direction): String =
        x.toString
    }
}
