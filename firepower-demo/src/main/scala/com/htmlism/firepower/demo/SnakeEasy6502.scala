package com.htmlism.firepower.demo

import scala.util.chaining._

import com.htmlism.firepower.core._

object SnakeEasy6502:
  def assemble(opts: AssemblerOptions): List[String] =
    (Nil)
      .map(AsmBlock.toLines(opts.instructionCase))
      .pipe(xs => AsmBlock.interFlatMap(xs)(List("", ""), identity))
