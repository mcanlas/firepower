package com.htmlism.firepower.demo

import scala.annotation.tailrec
import scala.collection.immutable.ListMap
import scala.util.chaining.*

import cats.syntax.all.*

import com.htmlism.firepower.core.*
import com.htmlism.firepower.core.AsmBlock.Intent

object SnakeEasy6502:
  val program: List[MetaIntent] =
    List(
      init.call,
      loop.call
    )

  lazy val init =
    Subroutine("init", "initializes values")
      .copy(intents =
        () =>
          List(
            initSnake.call,
            generateApplePosition.call
          )
      )

  lazy val loop =
    Subroutine("loop", "primary game loop")

  lazy val initSnake =
    Subroutine("initSnake", "initializes the snake")

  lazy val generateApplePosition =
    Subroutine("generateApplePosition", "generates the position of the apple")

  def firstCodeBlock(xs: List[MetaIntent]): AsmBlock.AnonymousCodeBlock =
    AsmBlock
      .AnonymousCodeBlock(xs.map(_.toIntent))

  def callGraph(xs: List[MetaIntent]): List[AsmBlock.NamedCodeBlock] =
    callGraphRecur(ListMap.empty, xs)
      .values
      .toList

  @tailrec
  private def callGraphRecur(
      callGraph: ListMap[String, AsmBlock.NamedCodeBlock],
      todo: List[MetaIntent]
  ): ListMap[String, AsmBlock.NamedCodeBlock] =
    todo.collect { case x: MetaIntent.Jump => x } match
      case head :: tail =>
        if callGraph.contains(head.target) then callGraphRecur(callGraph, tail)
        else
          val rts =
            AsmBlock.Intent(
              None,
              List(
                AsmBlock.Intent.Instruction.zero("rts")
              )
            )

          val sub =
            AsmBlock.NamedCodeBlock(
              head.target,
              head.description.some,
              head.xs.map(_.toIntent) :+ rts
            )

          // breadth-first expansion
          callGraphRecur(callGraph.updated(head.target, sub), todo ::: head.xs)

      case Nil =>
        callGraph

  def assemble(opts: AssemblerOptions): List[String] =
    (firstCodeBlock(program) :: callGraph(program))
      .map(AsmBlock.toLines(opts.instructionCase))
      .pipe(xs => AsmBlock.interFlatMap(xs)(List("", ""), identity))
