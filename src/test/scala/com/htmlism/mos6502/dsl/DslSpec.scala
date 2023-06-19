package com.htmlism.mos6502.dsl

import cats.data.NonEmptyList
import org.scalatest.flatspec._
import org.scalatest.matchers._

class DslSpec extends AnyFlatSpec with should.Matchers:

  "the dsl" should "compile" in:
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

  "enum" should "compile" in:
    val doc =
      asmDoc { implicit ctx =>
        enumAsm[Triforce]
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

  "bit field" should "compile" in:
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

  "label" should "compile" in:
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

sealed trait Triforce

case object Courage extends Triforce
case object Wisdom  extends Triforce
case object Power   extends Triforce

object Triforce:
  given enumTriforce: EnumAsm[Triforce] =
    new EnumAsm[Triforce]:
      def comment: String =
        "foo as enum"

      def all: NonEmptyList[Triforce] =
        NonEmptyList.of(Courage, Wisdom, Power)

      def label(x: Triforce): String =
        x.toString.toLowerCase

      def comment(x: Triforce): String =
        x.toString

sealed trait TestDirection

case object Up    extends TestDirection
case object Down  extends TestDirection
case object Left  extends TestDirection
case object Right extends TestDirection

object TestDirection:
  given bitFieldDirection: BitField[TestDirection] =
    new BitField[TestDirection]:
      def definitionGroupComment: String =
        "foo as bit field"

      def all: NonEmptyList[TestDirection] =
        NonEmptyList.of(Up, Down, Left, Right)

      def label(x: TestDirection): String =
        x.toString.toLowerCase
