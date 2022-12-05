package com.htmlism.firepower.core

case class AssemblerOptions()

object AssemblerOptions:
  enum Definitions:
    case InlineDefinitions, UseDefinitions, UseDefinitionsWithMath
