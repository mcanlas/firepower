package com.htmlism.scratchpad

trait Load[A]:
  // TODO genericize to take in encoder
  // from constant
  def instruction: String

  def const: Asm1[A] =
    Asm1(List(instruction))

  // from register
  def from[B <: Address: ReadAddress]: Asm2[A, B] =
    Asm2(List(instruction))

object Load:
  def apply[A: Load]: Load[A] =
    summon[Load[A]]

case class Asm1[A](xs: List[String]):
  def and[B]: Asm2[A, B] =
    Asm2(xs)

case class Asm2[A, B](xs: List[String]):
  def andThen(that: Asm2[A, B]): Asm2[2, B] =
    Asm2(xs ++ that.xs)

  // TODO not tested
  // B type needs to be a class type, with evidence, not a case class
  def swap[AA, BB](f: (R[A], R[B]) => (R[AA], R[BB])): Asm2[AA, BB] =
    Asm2.from(f(R[A](), R[B]()), xs)

object Asm2:
  def from[A, B](t2: (R[A], R[B]), xs: List[String]) =
    Asm2[A, B](xs)

case class R[A]()
