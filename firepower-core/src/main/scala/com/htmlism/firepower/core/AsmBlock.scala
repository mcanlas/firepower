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
            String.format(s"define %-${maximumLength}s $v", k)
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
    case class Instruction(code: String, comment: Option[String])

    def toLines(x: Intent): List[String] =
      x.label.map(toComment).map(withIndent).toList ++ x
        .instructions
        .map(i => i.code + i.comment.map(toComment).map(" " + _).getOrElse(""))
        .map(withIndent)
