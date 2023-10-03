package com.htmlism.firepower.core

import cats.syntax.all.*

sealed trait AsmBlock

object AsmBlock:
  /**
    * Lines of text to render as a comment block
    */
  case class CommentBlock(xs: List[String]) extends AsmBlock

  // TODO factory method for automatic line wrapping given some width
  object CommentBlock:
    /**
      * Factory method for multi-line ASCII art
      */
    def fromMultiline(s: String): CommentBlock =
      CommentBlock(s.split("\\n").toList)

  /**
    * Renders as a table of aliases
    */
  case class DefinesBlock(comment: Option[String], xs: List[(String, Int)]) extends AsmBlock

  /**
    * Renders as a labeled subroutine
    */
  case class NamedCodeBlock(name: String, comment: Option[String], intents: List[AsmBlock.Intent]) extends AsmBlock

  /**
    * Renders as an unlabeled block of code
    */
  case class AnonymousCodeBlock(intents: List[AsmBlock.Intent]) extends AsmBlock

  /**
    * Like a fancy `mkString` at the `F` level. Defers the projection of `A => F[B]` so that a container type isn't
    * needed (in the cases where `A` may yield a different length of `F[_]` than the interlaced payload)
    *
    * Not known to exist in `cats` given that it needs both `Semigroup` and an awareness of head/tail/emptiness
    */
  def interFlatMap[A, B](xs: List[A])(x: List[B], f: A => List[B]): List[B] =
    xs match
      case head :: tail =>
        f(head) ::: tail.flatMap(a => x ::: f(a))

      case Nil =>
        Nil

  def toComment(s: String): String =
    "; " + s

  def withIndent(s: String): String =
    "  " + s

  def toHex(n: Int): String =
    val hex =
      if n < 16 * 16 then String.format("%1$02x", n)
      else if n < 16 * 16 * 16 then String.format("%1$03x", n)
      else String.format("%1$04x", n)

    "$" + hex.toUpperCase

  def toLines(opts: AssemblerOptions.InstructionCase)(xs: AsmBlock): List[String] =
    xs match
      case CommentBlock(ys) =>
        ys.map(toComment)

      case DefinesBlock(oComment, kvs) =>
        val maximumLength =
          kvs
            .map(_._1.length)
            .max

        val defines =
          kvs
            .map { case (k, v) =>
              String.format(s"define %-${maximumLength}s ${toHex(v)}", k)
            }

        oComment.map(toComment).toList ::: defines

      case NamedCodeBlock(label, oComment, intents) =>
        val headerParagraph =
          List(label + ":") ++ oComment.map(toComment).map(withIndent).toList

        val intentParagraphs =
          intents.map(Intent.toLines(opts))

        interFlatMap(headerParagraph :: intentParagraphs)(List(""), identity)

      case AnonymousCodeBlock(intents) =>
        interFlatMap(intents)(List(""), Intent.toLines(opts))

  case class Intent(label: Option[String], instructions: List[Intent.Instruction])

  object Intent:
    case class Instruction(code: String, operand: Option[String], comment: Option[String]):
      def length: Int =
        (code + " " + operand.getOrElse("")).length

    object Instruction:
      def one(code: String, operand: String, comment: Option[String] = None): Instruction =
        Instruction(code, operand.some, comment)

      def zero(code: String, comment: Option[String] = None): Instruction =
        Instruction(code, None, comment)

    def toLines(opts: AssemblerOptions.InstructionCase)(x: Intent): List[String] =
      val comment =
        x.label.map(toComment).map(withIndent).toList

      val maximumLength =
        x
          .instructions
          .map(_.length)
          .max

      val instructions =
        x
          .instructions
          .map { i =>
            val leftSlug =
              instruction(i.code, opts) + i.operand.map(" " + _).getOrElse("")

            i.comment match
              case Some(c) =>
                String.format(s"%-${maximumLength}s", leftSlug) + " " + toComment(c)

              case None =>
                leftSlug
          }
          .map(withIndent)

      comment ++ instructions

  def instruction(s: String, instructionCase: AssemblerOptions.InstructionCase): String =
    instructionCase match
      case AssemblerOptions.InstructionCase.Uppercase =>
        s.toUpperCase

      case AssemblerOptions.InstructionCase.Lowercase =>
        s.toLowerCase
