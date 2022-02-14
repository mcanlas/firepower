package com.htmlism.mos6502.dsl

import scala.collection.immutable.ListSet
import scala.collection.mutable.ListBuffer

import cats.implicits._

case class AsmDocument(xs: List[TopLevelAsmDocumentFragment]):
  def toAsm: String =
    xs
      .map(_.toAsm)
      .mkString("\n\n")

class AsmDocumentContext:
  private val xs: ListBuffer[TopLevelAsmDocumentFragment] =
    ListBuffer()

  private var jumps: ListSet[Subroutine] =
    ListSet()

  def push(x: TopLevelAsmDocumentFragment): Unit =
    xs.append(x)

  def addJumpRegistry(ys: ListSet[Subroutine]): Unit =
    jumps = jumps ++ ys

  def toDoc: AsmDocument =
    val asmFragmentsAndSubroutines =
      xs.toList ::: jumps.toList ++ jumps.flatMap(_.jumpRegistry).toList

    AsmDocument(asmFragmentsAndSubroutines)

sealed trait TopLevelAsmDocumentFragment:
  def toAsm: String

case class AsmFragment(xs: List[Statement]) extends TopLevelAsmDocumentFragment:
  def toAsm: String =
    xs.map(_.toAsm).mkString("\n")

case class Subroutine(name: String, fragment: AsmFragment, jumpRegistry: ListSet[Subroutine])
    extends TopLevelAsmDocumentFragment:
  def toAsm: String =
    name + ":" + "\n" + fragment.toAsm

case class DefinitionGroup(comment: String, xs: List[Definition[_]]) extends TopLevelAsmDocumentFragment:
  def toAsm: String =
    val groupCommentLine =
      "; " + comment

    val definitionLines =
      xs
        .map { d =>
          d.comment match
            case Some(c) =>
              f"define ${d.name}%-20s${d.value} ; $c"

            case None =>
              f"define ${d.name}%-20s${d.value}"
        }

    (groupCommentLine :: definitionLines)
      .mkString("\n")

class DefinitionGroupContext:
  private val xs: ListBuffer[Definition[_]] =
    ListBuffer()

  def push[A](x: A)(implicit ev: NamedResource[A]): Unit =
    ev
      .toDefinitions(x)
      .foreach(xs.append)

  def toGroup(s: String): DefinitionGroup =
    DefinitionGroup(s, xs.toList)

/**
  * @param comment
  *   Typically used by resources to describe their type safety
  */
case class Definition[A](name: String, x: A, comment: Option[String])(implicit ev: DefinitionValue[A]):
  lazy val value: String =
    ev
      .value(x)

object Definition:
  implicit def namedResourceForDefinition[A]: NamedResource[Definition[A]] =
    new NamedResource[Definition[A]] {
      def toDefinitions(x: Definition[A]): List[Definition[_]] =
        List(x)
    }

  def apply[A: DefinitionValue](name: String, x: A): Definition[A] =
    Definition(name, x, None)

  def apply[A: DefinitionValue](name: String, x: A, comment: String): Definition[A] =
    Definition(name, x, comment.some)

class AsmBlockContext:
  private val xs: ListBuffer[Statement] =
    ListBuffer()

  def push(x: Statement): Unit =
    xs.append(x)
