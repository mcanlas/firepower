package com.htmlism.firepower.core

import cats.syntax.all.*

import com.htmlism.firepower.core.AsmBlock._

case class Subroutine(name: String, intents: List[Intent]):
  def call: Intent.Instruction =
    Intent.Instruction(s"jsr $name", None)

  def attach: NamedCodeBlock =
    NamedCodeBlock(name, "this is a named block".some, intents)

object Subroutine
