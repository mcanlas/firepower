package com.htmlism.scratchpad

object CodeGenerator extends App:
  val allLetters =
    ('A' to 'Z')
      .map(_.toString)

  for (n <- 1 to 3)
    val letters =
      allLetters.take(n)

    val nextLetter =
      allLetters(n)

    val typeParameterList =
      letters.mkString(", ")

    println(s"trait Asm$n[$typeParameterList]:")
    println("  def xs: List[String]")
    println
    println("  def oComment: Option[String]")
    println
    println(s"  def comment(s: String): Asm$n[$typeParameterList]")
    println
    println(s"  def andThen(that: Asm$n[$typeParameterList]): Asm$n[$typeParameterList] =")
    println(s"    AndThen$n[$typeParameterList](this, that, None)")
    println
    println(s"  def widenWith[$nextLetter]: Asm${n + 1}[$typeParameterList, $nextLetter] =")
    println(s"    Asm${n + 1}Instructions(xs, oComment)")
    println
    println(
      s"case class AndThen${n}[$typeParameterList](left: Asm$n[$typeParameterList], right: Asm$n[$typeParameterList], oComment: Option[String]) extends Asm${n}[$typeParameterList]:"
    )
    println("  def xs: List[String] =")
    println("    left.xs ++ right.xs")
    println
    println(s"  def comment(s: String): Asm$n[$typeParameterList] =")
    println("    copy(oComment = Some(s))")
    println
    println(
      s"case class Asm${n}Instructions[$typeParameterList](xs: List[String], oComment: Option[String]) extends Asm${n}[$typeParameterList]:"
    )
    println(s"  def comment(s: String): Asm$n[$typeParameterList] =")
    println("    copy(oComment = Some(s))")
    println

  for (n <- 1 to 3)
    val classNum =
      n

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
    println(s"  def widen[$newLetter : Reg]: AsmProgram$nPlus[$typeParametersShort, $newLetter, Ignores, Z] =")
    println(s"    AsmProgram$nPlus($arguments, ??? : StatefulRegister[$newLetter, Ignores], z)")
    println()
    println(s"  def name(s: String): SubRoutine$classNum[$typeParametersShort, Z] =")
    println(s"    SubRoutine$classNum(s, this)")
    println()

    println(
      s"case class SubRoutine$classNum[$typeParametersLong](name: String, program: AsmProgram$classNum[$typeParametersShort, Z])"
    )
    println()
