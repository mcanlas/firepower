package com.htmlism.firepower.demo

import scala.collection.immutable._
import scala.util.chaining._

object Easy6502:
  enum Color:
    case Black, White, Red, Cyan, Purple, Green, Blue, Yellow, Orange, Brown, LightRed, DarkGrey, Grey, LightGreen,
      LightBlue, LightGrey

  object Color:
    given Definable[Color] with
      def table: ListMap[String, String] =
        Color
          .values
          .iterator
          .map { c =>
            "COLOR_" + c.toString -> c.ordinal.toString
          }
          .pipe(ListMap.from)

      extension (x: Color)
        def toComment: String =
          x.toString

      extension (x: Color)
        def toValue: Int =
          x.ordinal

      extension (x: Color)
        def toDefine: String =
          "COLOR_" + x.toString

  class Screen(baseAddr: Int):
    def apply(offset: Int): Screen.Pixel =
      Screen.Pixel(baseAddr, offset)

  object Screen:
    case class Pixel(baseAddr: Int, offset: Int)

    object Pixel:
      given Definable[Pixel] with
        def table: ListMap[String, String] =
          ListMap("SCREEN" -> "TODO") // define table needs to be a function of an instance

        extension (x: Pixel)
          def toComment: String =
            s"Screen(${x.offset})"

        extension (x: Pixel)
          def toValue: Int =
            x.baseAddr + x.offset

        extension (x: Pixel)
          def toDefine: String =
            "TODO"
