package com.htmlism.mos6502.dsl

trait DefinitionValue[A] {

  /**
    * The value as presented in a `define` declaration (i.e. where no alias is possible)
    */
  def value(x: A): String
}

object DefinitionValue {
  implicit val definitionValueForInt: DefinitionValue[Int] =
    Operand.operandInt
      .toAddressLiteral(_)
}
