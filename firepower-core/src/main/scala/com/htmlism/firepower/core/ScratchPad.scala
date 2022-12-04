package com.htmlism.firepower.core

import cats._
import cats.syntax.all._

object ScratchPad:
  def reg[A: Reg]: PartialUsing[A] =
    new PartialUsing[A]

  class PartialUsing[A](using a: Reg[A]):
    def use[B](f: Reg[A] => B): B =
      f(a)

  reg[Accumulator].use { a =>
    AsmProgram(startA, "")
      .widen[RegisterX]
  }

  lazy val startA =
    ??? : StatefulRegister[Accumulator, Ignores]

  lazy val startX =
    ??? : StatefulRegister[RegisterX, Ignores]

  lazy val startY =
    ??? : StatefulRegister[RegisterY, Ignores]

  (AsmProgram(startA, ""): AsmProgram[Accumulator, Ignores, String])

  (AsmProgram(startA, "")
    .widen[RegisterX]: AsmProgram2[Accumulator, Ignores, RegisterX, Ignores, String])

//
//  case class Subroutine2[A, B](name: String, f: (Lease[A], Lease[B]) => State2[A, B]) {
//    def jump(a: Lease[A], b: Lease[B]): State[A] =
//      f(a, b)
//  }

class Accumulator

object Accumulator extends Accumulator:
  given Reg[Accumulator] with
    def hello: Boolean =
      true

class RegisterX

object RegisterX:
  given Reg[RegisterX] with
    def hello: Boolean =
      true

class RegisterY

object RegisterY:
  given Reg[RegisterY] with
    def hello: Boolean =
      true
