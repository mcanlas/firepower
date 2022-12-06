package com.htmlism.firepower.core

case class AssemblerOptions(
    instructionCase: AssemblerOptions.InstructionCase,
    definitionsMode: AssemblerOptions.DefinitionsMode
)

object AssemblerOptions:
  enum DefinitionsMode:
    case UseLiterals, UseDefinitions, UseDefinitionsWithMath

  enum InstructionCase:
    case Uppercase, Lowercase
