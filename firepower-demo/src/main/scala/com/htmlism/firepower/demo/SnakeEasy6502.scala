package com.htmlism.firepower.demo

import scala.annotation.tailrec
import scala.collection.immutable.ListMap
import scala.util.chaining.*

import cats.syntax.all._

import com.htmlism.firepower.core.AsmBlock.Intent
import com.htmlism.firepower.core.*

object SnakeEasy6502:
  val program: List[MetaIntent.Jump] =
    List(
      init.call,
      loop.call
    )

  lazy val init =
    Subroutine("init", "initializes values")
      .copy(intents =
        () =>
          List(
            initSnake.call
          )
      )

  lazy val loop =
    Subroutine("loop", "primary game loop")

  lazy val initSnake =
    Subroutine("initSnake", "initializes the snake")

  def firstCodeBlock(xs: List[MetaIntent.Jump]): AsmBlock.AnonymousCodeBlock =
    AsmBlock
      .AnonymousCodeBlock(xs.map(MetaIntent.Jump.toIntent))

  def callGraph(xs: List[MetaIntent.Jump]): List[AsmBlock.NamedCodeBlock] =
    callGraphRecur(ListMap.empty, xs)
      .values
      .toList

  @tailrec
  private def callGraphRecur(
      callGraph: ListMap[String, AsmBlock.NamedCodeBlock],
      todo: List[MetaIntent.Jump]
  ): ListMap[String, AsmBlock.NamedCodeBlock] =
    todo match
      case head :: tail =>
        if (callGraph.contains(head.target)) callGraphRecur(callGraph, tail)
        else
          val sub =
            AsmBlock.NamedCodeBlock(
              head.target,
              head.description.some,
              List(
                AsmBlock.Intent(
                  None,
                  List(
                    AsmBlock.Intent.Instruction.zero("rts")
                  )
                )
              )
            )

          callGraphRecur(callGraph.updated(head.target, sub), todo ::: head.xs)

      case Nil =>
        callGraph

  def assemble(opts: AssemblerOptions): List[String] =
    (firstCodeBlock(program) :: callGraph(program))
      .map(AsmBlock.toLines(opts.instructionCase))
      .pipe(xs => AsmBlock.interFlatMap(xs)(List("", ""), identity))
