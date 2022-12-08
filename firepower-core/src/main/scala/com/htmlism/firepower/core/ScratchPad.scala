package com.htmlism.firepower.core

import cats._
import cats.syntax.all._

object ScratchPad:
  def reg[A: Reg]: PartialUsing[A] =
    new PartialUsing[A]

  class PartialUsing[A](using a: Reg[A]):
    def use[B](f: Reg[A] => B): B =
      f(a)

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
