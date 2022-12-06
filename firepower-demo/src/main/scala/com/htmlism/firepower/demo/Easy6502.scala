package com.htmlism.firepower.demo

object Easy6502:
  enum Color:
    case Black, White, Red, Cyan, Purple, Green, Blue, Yellow, Orange, Brown, LightRed, DarkGrey, Grey, LightGreen,
      LightBlue, LightGrey

  object Color:
    given Definable[Color] with
      extension (x: Color)
        def toComment: String =
          x.toString

      extension (x: Color)
        def toValue: Int =
          x.ordinal
