package com.htmlism.firepower.core

object MacroAssembler:
  def assemble(program: List[String], options: AssemblerOptions): List[AsmBlock] =
    Nil

  def defineGraph(program: List[String]): List[AsmBlock.CommentBlock] =
    Nil

  def callGraph(program: List[String]): List[String] =
    Nil
