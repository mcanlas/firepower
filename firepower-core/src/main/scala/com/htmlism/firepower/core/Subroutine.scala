package com.htmlism.firepower.core

import cats.syntax.all.*

import com.htmlism.firepower.core.AsmBlock._

case class Subroutine(name: String, intents: List[MetaIntent]):
  def call: Intent.Instruction =
    Intent.Instruction.one("jsr", name, None)

  def attach: NamedCodeBlock =
    NamedCodeBlock(name, "this is a named block".some, intents.map(_ => Intent("TODO".some, Nil)))

object Subroutine:
  def apply(name: String): Subroutine =
    Subroutine(name, Nil)
