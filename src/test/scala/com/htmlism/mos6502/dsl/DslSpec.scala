package com.htmlism.mos6502.dsl

import cats.data.NonEmptyList
import org.scalatest.flatspec._
import org.scalatest.matchers._

class DslSpec extends AnyFlatSpec with should.Matchers {

  "the dsl" should "compile" in {
    val doc =
      asmDoc { implicit ctx =>
        group("constants test") { implicit g =>
          (constant("margin", 16), constant("secret", 42))
        }
      }

    doc shouldEqual AsmDocument(
      List(
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
        bitField[TestDirection]
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
        mapping[TestDirection]
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

  "label" should "compile" in {
    val doc =
      asmDoc { implicit ctx =>
        asm { implicit a =>
          label("init")
        }
      }

    doc shouldEqual AsmDocument(
      List(
        AsmFragment(
          List(
            Label("init")
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

sealed trait TestDirection

case object Up    extends TestDirection
case object Down  extends TestDirection
case object Left  extends TestDirection
case object Right extends TestDirection

object TestDirection {
  implicit val bitFieldDirection: BitField[TestDirection] =
    new BitField[TestDirection] {
      def definitionGroupComment: String =
        "foo as bit field"

      def all: NonEmptyList[TestDirection] =
        NonEmptyList.of(Up, Down, Left, Right)

      def label(x: TestDirection): String =
        x.toString.toLowerCase
    }

  implicit val mappingDirection: Mapping[TestDirection] =
    new Mapping[TestDirection] {
      def definitionGroupComment: String =
        "foo as a mapping"

      def all: NonEmptyList[TestDirection] =
        NonEmptyList.of(Up, Down, Left, Right)

      def value(x: TestDirection): Int =
        x match {
          case Up    => 0x77
          case Down  => 0x61
          case Left  => 0x73
          case Right => 0x64
        }

      def label(x: TestDirection): String =
        x.toString.toLowerCase

      def comment(x: TestDirection): String =
        x.toString
    }
}
