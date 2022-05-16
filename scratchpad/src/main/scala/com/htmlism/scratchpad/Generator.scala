package com.htmlism.scratchpad

object Generator extends App:
  val allLetters =
    ('A' to 'Z')
      .map(_.toString)

  for (n <- 1 to 22)
    val classNum =
      if (n == 1) "" else n

    val letters =
      allLetters.take(n)

    val typeParametersLong =
      letters.flatMap { s =>
        List(s"$s : Reg", s"M$s <: MutationStatus")
      }
      .appended("Z : Semigroup")
      .mkString(", ")

    val typeParametersShort =
      letters.flatMap { s =>
        List(s"$s", s"M$s")
      }
        .mkString(", ")

    val parameters =
      letters.map { s =>
        s"${s.toLowerCase}: StatefulRegister[$s, M$s]"
      }
        .appended("z: Z")
        .mkString(", ")

    val nPlus =
      n + 1

    val functionArgs =
      val base =
        letters.map { s =>
          s"StatefulRegister[$s, M$s]"
        }
        .mkString(", ")

      if (n == 1) base else s"($base)"

    val arguments =
      letters
        .map(_.toLowerCase)
        .mkString(", ")

    val newLetter =
      allLetters(n)

    println(s"case class AsmProgram$classNum[$typeParametersLong]($parameters):")
    println(s"  def map(f: $functionArgs => Z): AsmProgram$classNum[$typeParametersShort, Z] =")
    println(s"    AsmProgram$classNum($arguments, z |+| f($arguments))")
    println()
    println(s"  def widen[$newLetter : Reg]: AsmProgram$nPlus[$typeParametersShort, $newLetter, Unknown, Z] =")
    println(s"    AsmProgram$nPlus($arguments, ??? : StatefulRegister[$newLetter, Unknown], z)")
    println()

//case class AsmProgram[A : Reg, FA <: MutationStatus, Z : Semigroup](a: StatefulRegister[A, FA], z: Z):
//  def map(f: StatefulRegister[A, FA] => Z): AsmProgram[A, FA, Z] =
//    AsmProgram(a, z |+| f(a))
//
//  def widen[B : Reg]: AsmProgram2[A, FA, B, Unknown, Z] =
//    AsmProgram2(a, ??? : StatefulRegister[B, Unknown], z)
//
//case class AsmProgram2[A : Reg, FA <: MutationStatus, B: Reg, FB <: MutationStatus, Z : Semigroup](a: StatefulRegister[A, FA], b: StatefulRegister[B, FB], z: Z):
//  def map(f: (StatefulRegister[A, FA], StatefulRegister[B, FB]) => Z): AsmProgram2[A, FA, B, FB, Z] =
//    AsmProgram2(a, b, z |+| f(a, b))
