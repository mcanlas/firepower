package com.htmlism.firepower.core

sealed trait AsmBlock

object AsmBlock:
  case class CommentBlock(xs: List[String]) extends AsmBlock

  object CommentBlock:
    def fromMultiline(s: String): CommentBlock =
      CommentBlock(s.split("\\n").toList)

  case class DefinesBlock(xs: List[(String, Int)]) extends AsmBlock

  case class NamedCodeBlock(name: String, comment: Option[String], intents: List[AsmBlock.Intent]) extends AsmBlock

  case class AnonymousCodeBlock(intents: List[AsmBlock.Intent]) extends AsmBlock

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

  def toLines(xs: AsmBlock): List[String] =
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
          intents.map(Intent.toLines)

        interFlatMap(headerParagraph :: intentParagraphs)(List(""), identity)

      case AnonymousCodeBlock(intents) =>
        interFlatMap(intents)(List(""), Intent.toLines)

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

    def toLines(x: Intent): List[String] =
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
              oComment match
                case Some(c) =>
                  String.format(s"%-${maximumLength}s", code) + " " + toComment(c)

                case None =>
                  code

            case Instruction.One(code, operand, oComment) =>
              val leftSlug =
                code + " " + operand

              oComment match
                case Some(c) =>
                  String.format(s"%-${maximumLength}s", leftSlug) + " " + toComment(c)

                case None =>
                  leftSlug
          }
          .map(withIndent)

      comment ++ instructions
