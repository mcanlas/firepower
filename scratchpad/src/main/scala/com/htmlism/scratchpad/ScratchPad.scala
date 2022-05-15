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

  case class AsmProgram[A : Reg, FA <: MutationStatus, Z : Semigroup](a: StatefulRegister[A, FA], z: Z):
    def map(f: StatefulRegister[A, FA] => Z): AsmProgram[A, FA, Z] =
      AsmProgram(a, z |+| f(a))

    def widen[B : Reg]: AsmProgram2[A, FA, B, Unknown, Z] =
      AsmProgram2(a, ??? : StatefulRegister[B, Unknown], z)

  case class AsmProgram2[A : Reg, FA <: MutationStatus, B: Reg, FB <: MutationStatus, Z : Semigroup](a: StatefulRegister[A, FA], b: StatefulRegister[B, FB], z: Z):
    def map(f: (StatefulRegister[A, FA], StatefulRegister[B, FB]) => Z): AsmProgram2[A, FA, B, FB, Z] =
      AsmProgram2(a, b, z |+| f(a, b))

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
