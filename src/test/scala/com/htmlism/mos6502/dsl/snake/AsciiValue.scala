package com.htmlism.mos6502.dsl
package snake

import cats.data.NonEmptyList

object AsciiValue:
  given asciiValueMapping: Mapping[AsciiValue] =
    new Mapping[AsciiValue]:
      def definitionGroupComment: String =
        "ASCII values of keys controlling the snake"

      def all: NonEmptyList[AsciiValue] =
        NonEmptyList.of(AsciiW, AsciiA, AsciiS, AsciiD)

      def value(x: AsciiValue): Int =
        x match
          case AsciiW => 0x77
          case AsciiA => 0x61
          case AsciiS => 0x73
          case AsciiD => 0x64

      def label(x: AsciiValue): String =
        x.toString.toLowerCase

      def comment(x: AsciiValue): String =
        x.toString

sealed trait AsciiValue

case object AsciiW extends AsciiValue
case object AsciiA extends AsciiValue
case object AsciiS extends AsciiValue
case object AsciiD extends AsciiValue
