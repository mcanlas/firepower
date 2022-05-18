package com.htmlism.scratchpad

import cats._
import cats.syntax.all._

sealed trait Lease[F[_]]

trait Read[A]

object Read:
  implicit val readLease: Lease[Read] =
    new Lease { }

trait Write[A]

object Write:
  implicit val writeLease: Lease[Write] =
    new Lease { }

object ScratchPad:
  def reg[A : Reg]: PartialUsing[A] =
    new PartialUsing[A]

  class PartialUsing[A](using a: Reg[A]):
    def use[B](f: Reg[A] => B): B =
      f(a)

  reg[Accumulator].use { a =>
    AsmProgram(startA, "")
      .widen[RegisterX]
  }

  trait ReadWrite[A] extends Read[A] with Write[A]

  lazy val startA =
    ??? : StatefulRegister[Accumulator, Unknown]

  lazy val startX =
    ??? : StatefulRegister[RegisterX, Unknown]

  lazy val startY =
    ??? : StatefulRegister[RegisterY, Unknown]

  (AsmProgram(startA, "") : AsmProgram[Accumulator, Unknown, String])

  (AsmProgram(startA, "")
    .widen[RegisterX] : AsmProgram2[Accumulator, Unknown, RegisterX, Unknown, String])

//
//  case class Subroutine2[A, B](name: String, f: (Lease[A], Lease[B]) => State2[A, B]) {
//    def jump(a: Lease[A], b: Lease[B]): State[A] =
//      f(a, b)
//  }

class Accumulator

object Accumulator extends Accumulator:
  given registerA: Reg[Accumulator] with
    def hello: Boolean =
      true

class RegisterX

object RegisterX:
  given registerX: Reg[RegisterX] with
    def hello: Boolean =
      true

class RegisterY

object RegisterY:
  given registerY: Reg[RegisterY] with
    def hello: Boolean =
      true
