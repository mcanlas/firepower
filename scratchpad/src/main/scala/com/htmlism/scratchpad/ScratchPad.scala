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

class Reg[A]

object ScratchPad:
  trait ReadWrite[A] extends Read[A] with Write[A]

  val startA =
    ??? : StatefulRegister[Accumulator, Unknown]

  val startX =
    ??? : StatefulRegister[RegisterX, Unknown]

  val startY =
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
  implicit val registerA: Reg[Accumulator] =
    new Reg

class RegisterX

object RegisterX:
  implicit val registerX: Reg[RegisterX] =
    new Reg

class RegisterY

object RegisterY:
  implicit val registerY: Reg[RegisterY] =
    new Reg

trait StatefulRegister[A : Reg, F <: MutationStatus]
