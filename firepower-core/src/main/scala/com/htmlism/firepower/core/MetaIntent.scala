package com.htmlism.firepower.core

import cats.syntax.all.*

/**
  * Anything that can be compiled into an `Intent`
  */
sealed trait MetaIntent:
  def toIntent: AsmBlock.Intent

object MetaIntent:
  case class Jump(target: String, description: String, xs: List[MetaIntent]) extends MetaIntent:
    def toIntent: AsmBlock.Intent =
      AsmBlock.Intent(None, List(AsmBlock.Intent.Instruction.one("jsr", target)))

  case class Move[A: Definable, B: Definable](src: A, dest: B):
    def defines: List[Definable.Table] =
      List(
        Definable[A]
          .table(src),
        Definable[B]
          .table(dest)
      )

  object Move:
    def toIntent[A: Definable, B: Definable](mv: Move[A, B], opts: AssemblerOptions.DefinitionsMode): AsmBlock.Intent =
      val argument =
        opts match
          case AssemblerOptions.DefinitionsMode.UseLiterals =>
            f"#$$${mv.src.toValue}%02X"

          case AssemblerOptions.DefinitionsMode.UseDefinitions |
              AssemblerOptions.DefinitionsMode.UseDefinitionsWithMath =>
            "#" + mv.src.toDefine

      val argumentTwo =
        opts match
          case AssemblerOptions.DefinitionsMode.UseLiterals =>
            AsmBlock.toHex(mv.dest.toValue)

          case AssemblerOptions.DefinitionsMode.UseDefinitions =>
            mv.dest.toDefine

          case AssemblerOptions.DefinitionsMode.UseDefinitionsWithMath =>
            mv.dest.toDefineWithMath

      AsmBlock.Intent(
        s"${mv.dest.toComment} = ${mv.src.toComment}".some,
        List(
          AsmBlock
            .Intent
            .Instruction
            .one("LDA", argument, s"a = ${mv.src.toComment}".some),
          AsmBlock
            .Intent
            .Instruction
            .one(
              "STA",
              argumentTwo,
              s"${mv.dest.toComment} = a".some
            )
        )
      )
