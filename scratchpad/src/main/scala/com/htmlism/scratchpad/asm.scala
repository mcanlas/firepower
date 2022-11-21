package com.htmlism.scratchpad

sealed trait Asm1[A]:
  def xs: List[String]

  def oComment: Option[String]

  def comment(s: String): Asm1[A]

  def andThen(that: Asm1[A]): Asm1[A] =
    AndThen1[A](this, that, None)

  def widenWith[B]: Asm2[A, B] =
    Asm2Instructions(xs, oComment)

case class AndThen1[A](left: Asm1[A], right: Asm1[A], oComment: Option[String]) extends Asm1[A]:
  def xs: List[String] =
    left.xs ++ right.xs

  def comment(s: String): Asm1[A] =
    copy(oComment = Some(s))

case class Asm1Instructions[A](xs: List[String], oComment: Option[String] = None) extends Asm1[A]:
  def comment(s: String): Asm1[A] =
    copy(oComment = Some(s))

sealed trait Asm2[A, B]:
  def xs: List[String]

  def oComment: Option[String]

  def comment(s: String): Asm2[A, B]

  def andThen(that: Asm2[A, B]): Asm2[A, B] =
    AndThen2[A, B](this, that, None)

case class AndThen2[A, B](left: Asm2[A, B], right: Asm2[A, B], oComment: Option[String]) extends Asm2[A, B]:
  def xs: List[String] =
    left.xs ++ right.xs

  def comment(s: String): Asm2[A, B] =
    copy(oComment = Some(s))

case class Asm2Instructions[A, B](xs: List[String], oComment: Option[String] = None) extends Asm2[A, B]:
  def comment(s: String): Asm2[A, B] =
    copy(oComment = Some(s))
  // TODO not tested
  // B type needs to be a class type, with evidence, not a case class
  def swap[AA, BB](f: (R[A], R[B]) => (R[AA], R[BB])): Asm2Instructions[AA, BB] =
    Asm2Instructions.from(f(R[A](), R[B]()), xs)

object Asm2Instructions:
  def from[A, B](t2: (R[A], R[B]), xs: List[String]) =
    Asm2Instructions[A, B](xs)

trait Asm3[A, B, C]:
  def xs: List[String]

case class Asm3Instructions[A, B, C](xs: List[String]) extends Asm3[A, B, C]

case class R[A]()
