package com.htmlism.mos6502.dsl

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

  def push(x: TopLevelAsmDocumentFragment): Unit =
    xs.append(x)

  def attach(sub: Subroutine): Unit =
    xs.append(sub)

  def toDoc: AsmDocument =
    AsmDocument(xs.toList)
}

sealed trait TopLevelAsmDocumentFragment {
  def toAsm: String
}

case class AsmFragment(xs: List[Statement]) extends TopLevelAsmDocumentFragment {
  def toAsm: String =
    xs.map(_.toAsm).mkString("\n")
}

case class Subroutine(name: String, fragment: AsmFragment) extends TopLevelAsmDocumentFragment {
  def toAsm: String =
    name + ":" + "\n" + fragment.toAsm
}

case class DefinitionGroup(comment: String, xs: List[Definition[_]]) extends TopLevelAsmDocumentFragment {
  def toAsm: String = {
    val commentLine =
      "; " + comment

    val definitionLines =
      xs
        .map(d => f"define ${d.name}%-12s${d.value}")

    (commentLine :: definitionLines)
      .mkString("\n")
  }
}

class DefinitionGroupContext {
  private val xs: ListBuffer[Definition[_]] =
    ListBuffer()

  def push(x: Definition[_]): Unit =
    xs.append(x)

  def toGroup(s: String): DefinitionGroup =
    DefinitionGroup(s, xs.toList)
}

case class Definition[A: Operand](name: String, x: A) {
  lazy val value: String =
    implicitly[Operand[A]]
      .toDefinitionLiteral(x)
}

class AsmBlockContext {
  private val xs: ListBuffer[Statement] =
    ListBuffer()

  def push(x: Statement): Unit =
    xs.append(x)
}
