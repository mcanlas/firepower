package com.htmlism.mos6502.dsl

import scala.collection.mutable.ListBuffer

case class AsmDocument(xs: List[TopLevelAsmDocumentFragment])

class AsmDocumentContext {
  private val xs: ListBuffer[TopLevelAsmDocumentFragment] =
    ListBuffer()

  def push(x: TopLevelAsmDocumentFragment): Unit =
    xs.append(x)

  def toDoc: AsmDocument =
    AsmDocument(xs.toList)
}

sealed trait TopLevelAsmDocumentFragment

case class AsmFragment(xs: List[Statement]) extends TopLevelAsmDocumentFragment {
  def printOut(): Unit = {
    xs.map(_.toAsm)
      .foreach(println)
  }
}

case class DefinitionGroup(comment: String, xs: List[Definition[_]]) extends TopLevelAsmDocumentFragment

class DefinitionGroupContext {
  private val xs: ListBuffer[Definition[_]] =
    ListBuffer()

  def push(x: Definition[_]): Unit =
    xs.append(x)

  def toGroup(s: String): DefinitionGroup =
    DefinitionGroup(s, xs.toList)
}

case class Definition[A: Operand](name: String, x: A)

class AsmBlockContext {
  private val xs: ListBuffer[Statement] =
    ListBuffer()

  def push(x: Statement): Unit =
    xs.append(x)
}
