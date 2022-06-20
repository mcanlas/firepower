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
      letters
        .flatMap { s =>
          List(s"$s : Reg", s"M$s <: MutationStatus")
        }
        .appended("Z : Monoid")
        .mkString(", ")

    val typeParametersShort =
      letters
        .flatMap { s =>
          List(s"$s", s"M$s")
        }
        .mkString(", ")

    val parameters =
      letters
        .map { s =>
          s"${s.toLowerCase}: StatefulRegister[$s, M$s]"
        }
        .appended("z: Z")
        .mkString(", ")

    val nPlus =
      n + 1

    val functionArgs =
      val base =
        letters
          .map { s =>
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
    println(
      s"  def andThen(that: AsmProgram$classNum[$typeParametersShort, Z]): AsmProgram$classNum[$typeParametersShort, Z] ="
    )
    println(s"    AsmProgram$classNum($arguments, z |+| that.z)")
    println()
    println(s"  def widen[$newLetter : Reg]: AsmProgram$nPlus[$typeParametersShort, $newLetter, Unknown, Z] =")
    println(s"    AsmProgram$nPlus($arguments, ??? : StatefulRegister[$newLetter, Unknown], z)")
    println()
    println(s"  def name(s: String): SubRoutine$classNum[$typeParametersShort, Z] =")
    println(s"    SubRoutine$classNum(s, this)")
    println()

    println(
      s"case class SubRoutine$classNum[$typeParametersLong](name: String, program: AsmProgram$classNum[$typeParametersShort, Z])"
    )
    println()
