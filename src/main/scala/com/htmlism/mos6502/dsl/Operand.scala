package com.htmlism.mos6502.dsl

import cats.Contravariant

trait Operand[A] {
  def toString(x: A): String
}

object Operand {
  implicit val operandInt: Operand[Int] =
    (x: Int) => String.format("#$%02x", x)

  implicit val contra: Contravariant[Operand] = new Contravariant[Operand] {
    def contramap[A, B](fa: Operand[A])(f: B => A): Operand[B] =
      new Operand[B] {
        def toString(x: B): String =
          fa.toString {
            f(x)
          }
      }
  }
}
