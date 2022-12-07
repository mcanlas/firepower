package com.htmlism.firepower.core

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
  // TODO needs descriptive field
  case class DefinesBlock(xs: List[(String, Int)]) extends AsmBlock

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
      if (n < 16 * 16)
        String.format("%1$02x", n)
      else if (n < 16 * 16 * 16)
        String.format("%1$03x", n)
      else
        String.format("%1$04x", n)

    "$" + hex.toUpperCase

  def toLines(opts: AssemblerOptions.InstructionCase)(xs: AsmBlock): List[String] =
    xs match
      case CommentBlock(ys) =>
        ys.map(toComment)

      case DefinesBlock(kvs) =>
        val maximumLength =
          kvs
            .map(_._1.length)
            .max

        kvs
          .map { case (k, v) =>
            String.format(s"define %-${maximumLength}s ${toHex(v)}", k)
          }

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
    sealed trait Instruction:
      def length: Int

    object Instruction:
      case class One(code: String, operand: String, comment: Option[String]) extends Instruction:
        def length: Int =
          operand.length + 4
      case class Zero(code: String, comment: Option[String])                 extends Instruction:
        def length: Int =
          0

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
          .map {
            case Instruction.Zero(code, oComment) =>
              val codeUpperLower =
                instruction(code, opts)

              oComment match
                case Some(c) =>
                  String.format(s"%-${maximumLength}s", codeUpperLower) + " " + toComment(c)

                case None =>
                  codeUpperLower

            case Instruction.One(code, operand, oComment) =>
              val leftSlug =
                instruction(code, opts) + " " + operand

              oComment match
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
