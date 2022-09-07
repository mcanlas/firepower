package com.htmlism.mos6502.dsl
package snake

import cats.data.NonEmptyList

object Direction:
  implicit val directionBitField: BitField[Direction] =
    new BitField[Direction]:
      def definitionGroupComment: String =
        "Directions"

      def all: NonEmptyList[Direction] =
        NonEmptyList.of(Up, Down, Left, Right)

      def label(x: Direction): String =
        "moving" + x.toString

sealed trait Direction

case object Up extends Direction
case object Down extends Direction
case object Left extends Direction
case object Right extends Direction
