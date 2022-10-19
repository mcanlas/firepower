package com.htmlism.scratchpad

trait Load[A]:
  // TODO genericize to take in encoder
  // from constant
  def instruction: String

  def const: Asm1Instructions[A] =
    Asm1Instructions(List(instruction))

  // from register
  def from[B <: Address: ReadAddress]: Asm2Instructions[A, B] =
    Asm2Instructions(List(instruction))

object Load:
  def apply[A: Load]: Load[A] =
    summon[Load[A]]

trait Asm1[A]:
  def xs: List[String]

case class Asm1Instructions[A](xs: List[String]) extends Asm1[A]:
  def and[B]: Asm2Instructions[A, B] =
    Asm2Instructions(xs)

trait Asm2[A, B]:
  def xs: List[String]

case class Asm2Instructions[A, B](xs: List[String]) extends Asm2[A, B]:
  def andThen(that: Asm2Instructions[A, B]): Asm2[A, B] =
    AndThen2(this, that)

  // TODO not tested
  // B type needs to be a class type, with evidence, not a case class
  def swap[AA, BB](f: (R[A], R[B]) => (R[AA], R[BB])): Asm2Instructions[AA, BB] =
    Asm2Instructions.from(f(R[A](), R[B]()), xs)

object Asm2Instructions:
  def from[A, B](t2: (R[A], R[B]), xs: List[String]) =
    Asm2Instructions[A, B](xs)

case class AndThen2[A, B](x: Asm2[A, B], y: Asm2[A, B]) extends Asm2[A, B]:
  def xs: List[String] =
    x.xs ++ y.xs

case class R[A]()

// TODO needs evidence that it is a storable target of one thing
case class StoreTo[A: Store, B]() extends Asm2[A, B]:
  // TODO
  def xs: List[String] =
    Nil

case class LoadImmediate[R: Load, A: ImmediateValue]() extends Asm1[R]:
  // TODO
  def xs: List[String] =
    Nil

trait ImmediateValue[A]:
  def toByte(x: A): Int
