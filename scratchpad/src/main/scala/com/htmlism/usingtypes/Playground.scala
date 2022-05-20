package com.htmlism.usingtypes

trait Reg[+A]:
  def get: A

object Reg:
  given accumulatorReg: Reg[Accumulator] with
    def get: Accumulator =
      new Accumulator

  given xReg: Reg[RegisterX] with
    def get: RegisterX =
      new RegisterX

  given yReg: Reg[RegisterY] with
    def get: RegisterY =
      new RegisterY

object Playground extends App:
  private val first =
    new AsmProgram[Accumulator]

  private val second =
    new AsmProgram[RegisterX]

  private val both: AsmProgram[Accumulator & RegisterX] =
    first
      .join(second)

  println(both.use)
