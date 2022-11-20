package com.htmlism.scratchpad

sealed trait Asm1[A]:
  def xs: List[String]

  def widenWith[B]: Asm2[A, B] =
    Asm2Instructions(xs)

case class Asm1Instructions[A](xs: List[String]) extends Asm1[A]

sealed trait Asm2[A, B]:
  def xs: List[String]

  def andThen(that: Asm2[A, B]): Asm2[A, B] =
    AndThen2(this, that)

case class Asm2Instructions[A, B](xs: List[String]) extends Asm2[A, B]:
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

trait Asm3[A, B, C]:
  def xs: List[String]

case class Asm3Instructions[A, B, C](xs: List[String]) extends Asm3[A, B, C]

case class R[A]()
