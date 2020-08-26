package com.htmlism.mos6502.dsl

import scala.collection.immutable.ListSet
import scala.collection.mutable.ListBuffer

case class AsmDocument(xs: List[TopLevelAsmDocumentFragment]) {
  def toAsm: String =
    xs
      .map(_.toAsm)
      .mkString("\n\n")
}

class AsmDocumentContext {
  private val xs: ListBuffer[TopLevelAsmDocumentFragment] =
    ListBuffer()

  private var jumps: ListSet[Subroutine] =
    ListSet()

  def push(x: TopLevelAsmDocumentFragment): Unit =
    xs.append(x)

  def addJumpRegistry(ys: ListSet[Subroutine]): Unit =
    jumps = jumps ++ ys

  def toDoc: AsmDocument = {
    val asmFragmentsAndSubroutines =
      xs.toList ::: jumps.toList ++ jumps.flatMap(_.jumpRegistry).toList

    AsmDocument(asmFragmentsAndSubroutines)
  }
}

sealed trait TopLevelAsmDocumentFragment {
  def toAsm: String
}

case class AsmFragment(xs: List[Statement]) extends TopLevelAsmDocumentFragment {
  def toAsm: String =
    xs.map(_.toAsm).mkString("\n")
}

case class Subroutine(name: String, fragment: AsmFragment, jumpRegistry: ListSet[Subroutine])
    extends TopLevelAsmDocumentFragment {
  def toAsm: String =
    name + ":" + "\n" + fragment.toAsm
}

case class DefinitionGroup(comment: String, xs: List[Definition[_]]) extends TopLevelAsmDocumentFragment {
  def toAsm: String = {
    val commentLine =
      "; " + comment

    val definitionLines =
      xs
        .map(d => f"define ${d.name}%-20s${d.value}")

    (commentLine :: definitionLines)
      .mkString("\n")
  }
}

class DefinitionGroupContext {
  private val xs: ListBuffer[Definition[_]] =
    ListBuffer()

  def push[A](x: A)(implicit ev: Definable[A]): Unit =
    ev
      .toDefinitions(x)
      .foreach(xs.append)

  def toGroup(s: String): DefinitionGroup =
    DefinitionGroup(s, xs.toList)
}

case class Definition[A: Operand](name: String, x: A) {
  lazy val value: String =
    implicitly[Operand[A]]
      .toDefinitionLiteral(x)
}

object Definition {
  implicit def definitionDefinable[A]: Definable[Definition[A]] =
    new Definable[Definition[A]] {
      def toDefinitions(x: Definition[A]): List[Definition[_]] =
        List(x)
    }
}

class AsmBlockContext {
  private val xs: ListBuffer[Statement] =
    ListBuffer()

  def push(x: Statement): Unit =
    xs.append(x)
}
