package com.htmlism.scratchpad

import cats.Semigroup
import cats.syntax.all._

case class AsmProgram[A: Reg, MA <: MutationStatus, Z: Semigroup](a: StatefulRegister[A, MA], z: Z):
  def map(f: StatefulRegister[A, MA] => Z): AsmProgram[A, MA, Z] =
    AsmProgram(a, z |+| f(a))

  def widen[B: Reg]: AsmProgram2[A, MA, B, Ignores, Z] =
    AsmProgram2(a, ??? : StatefulRegister[B, Ignores], z)

case class AsmProgram2[A: Reg, MA <: MutationStatus, B: Reg, MB <: MutationStatus, Z: Semigroup](
    a: StatefulRegister[A, MA],
    b: StatefulRegister[B, MB],
    z: Z
):
  def map(f: (StatefulRegister[A, MA], StatefulRegister[B, MB]) => Z): AsmProgram2[A, MA, B, MB, Z] =
    AsmProgram2(a, b, z |+| f(a, b))

  def widen[C: Reg]: AsmProgram3[A, MA, B, MB, C, Ignores, Z] =
    AsmProgram3(a, b, ??? : StatefulRegister[C, Ignores], z)

case class AsmProgram3[
    A: Reg,
    MA <: MutationStatus,
    B: Reg,
    MB <: MutationStatus,
    C: Reg,
    MC <: MutationStatus,
    Z: Semigroup
](a: StatefulRegister[A, MA], b: StatefulRegister[B, MB], c: StatefulRegister[C, MC], z: Z):
  def map(
      f: (StatefulRegister[A, MA], StatefulRegister[B, MB], StatefulRegister[C, MC]) => Z
  ): AsmProgram3[A, MA, B, MB, C, MC, Z] =
    AsmProgram3(a, b, c, z |+| f(a, b, c))
