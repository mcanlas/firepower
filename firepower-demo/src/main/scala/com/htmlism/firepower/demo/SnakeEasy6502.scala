package com.htmlism.firepower.demo

import scala.util.chaining.*

import com.htmlism.firepower.core.AsmBlock.Intent
import com.htmlism.firepower.core.*

object SnakeEasy6502:
  val program: List[MetaIntent.Jump] =
    List(
      init.call,
      loop.call
    )

  lazy val init =
    Subroutine("init")

  lazy val loop =
    Subroutine("loop")

  lazy val initSnake =
    Subroutine("initSnake")

  def firstCodeBlock(xs: List[MetaIntent.Jump]): AsmBlock.AnonymousCodeBlock =
    AsmBlock
      .AnonymousCodeBlock(xs.map(MetaIntent.Jump.toIntent))

  def callGraph(xs: List[MetaIntent.Jump]): List[AsmBlock.NamedCodeBlock] =
    Nil

  def assemble(opts: AssemblerOptions): List[String] =
    (firstCodeBlock(program) :: callGraph(program))
      .map(AsmBlock.toLines(opts.instructionCase))
      .pipe(xs => AsmBlock.interFlatMap(xs)(List("", ""), identity))
