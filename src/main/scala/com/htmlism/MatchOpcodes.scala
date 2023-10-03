package com.htmlism

import java.io.PrintWriter

import cats.syntax.all.*

import com.htmlism.mos6502.model.*

object MatchOpcodes:
  def paddedBinary(n: Int, width: Int) =
    String.format(s"%${width}s", Integer.toBinaryString(n)).replace(" ", "0")

  def main(args: Array[String]): Unit =
    write(args(0))(doStuff)

  def generatedOpcodes: Map[Int, (Instruction, AddressingMode)] =
    (0 to 255)
      .map(n => n -> toOpcode(n))
      .collect { case (n, Some(x)) => (n, x) }
      .toMap

  // format: off
  def injectedOpcodesSpecific: Map[Int, (Instruction, AddressingMode)] =
    Map(
      0x4C -> (JMP -> Absolute),
      0x6C -> (JMP -> Indirect),

      0x96 -> (STX -> ZeroPageY),
      0xB6 -> (LDX -> ZeroPageY),
      0xBE -> (LDX -> AbsoluteY)
    )

  def injectedOpcodesRelative: Map[Int, (Instruction, AddressingMode)] =
    Map(
      0x10 -> BPL,
      0x30 -> BMI,
      0x50 -> BVC,
      0x70 -> BVS,
      0x90 -> BCC,
      0xB0 -> BCS,
      0xD0 -> BNE,
      0xF0 -> BEQ
    ).view.mapValues(x => x -> Relative).toMap

  def injectedOpcodesImplied: Map[Int, (Instruction, AddressingMode)] =
    Map(
      0x00 -> BRK,
      0x20 -> JSR,
      0x40 -> RTI,
      0x60 -> RTS,

      0x08 -> PHP,
      0x28 -> PLP,
      0x48 -> PHA,
      0x68 -> PLA,
      0x88 -> DEY,
      0xA8 -> TAY,
      0xC8 -> INY,
      0xE8 -> INX,

      0x18 -> CLC,
      0x38 -> SEC,
      0x58 -> CLI,
      0x78 -> SEI,
      0x98 -> TYA,
      0xB8 -> CLV,
      0xD8 -> CLD,
      0xF8 -> SED,

      0x8A -> TXA,
      0x9A -> TXS,
      0xAA -> TAX,
      0xBA -> TSX,
      0xCA -> DEX,
      0xEA -> NOP
    ).view.mapValues(x => x -> Implied).toMap
  // format: on

  def doStuff(out: PrintWriter): Unit =
    val lookup =
      generatedOpcodes ++
        injectedOpcodesImplied ++
        injectedOpcodesRelative ++
        injectedOpcodesSpecific

    out.print("<table>")

    // print headers
    out.print("<tr>")
    out.print("<th/>")

    val fancyColumns =
      for {
        c <- 0 to 3
        b <- 0 to 7
      } yield b * 4 + c

    for f <- fancyColumns do out.print(s"<th>${paddedBinary(f, 8)}</th>")

    out.print("</tr>")

    for r <- Seq(0x00, 0x20, 0x40, 0x60, 0x80, 0xa0, 0xc0, 0xe0) do
      out.print("<tr>")

      // left header
      out.print(s"<th>${paddedBinary(r, 3)}</th>")

      for f <- fancyColumns do
        val fullInt = r + f

        lookup.get(fullInt) match
          case Some((ints, mode)) =>
            val hex = f"$fullInt%2X"
            out.print(
              s"""<th class="${ints.theme}" style="${"background-color: " + ints.color}">$ints $mode<br>$hex</th>"""
            )

          case None =>
            out.print(s"<td>UNDEF</td>")

      out.print("</tr>")

    out.print("</table>")

    out.print("<table>")

    // print headers
    out.print("<tr>")
    out.print("<th/>")

    for c <- wideColumns do out.print(s"<th>${paddedBinary(c, 5)}</th>")

    out.print("</tr>")

    for r <- wideRows do
      out.print("<tr>")

      // left header
      out.print(s"<th>${paddedBinary(r, 3)}</th>")

      for c <- wideColumns do
        val fullInt = (r << 5) + c

        lookup.get(fullInt) match
          case Some((ints, mode)) =>
            val hex = f"$fullInt%2X"
            out.print(
              s"""<th class="${ints.theme}" style="${"background-color: " + ints.color}">$ints $mode<br>$hex</th>"""
            )

          case None =>
            out.print(s"<td>UNDEF</td>")

      out.print("</tr>")

    out.print("</table>")

    quartile(out, 0, lookup)
    quartile(out, 1, lookup)
    quartile(out, 2, lookup)
    quartile(out, 3, lookup)

  def quartile(out: PrintWriter, n: Int, lookup: Map[Int, (Instruction, AddressingMode)]) =
    out.print(s"<h2>${paddedBinary(n, 2)}</h2>")

    out.print("<table>")

    // print headers
    out.print("<tr>")
    out.print("<th/>")

    val columns =
      for {
        y <- 0 to 1
        x <- 0 to 1
        z <- 0 to 1
      } yield (z << 2) + (x << 1) + y

    val rows =
      0 to 7

    for c <- columns do out.print(s"<th>${paddedBinary(c, 3)}</th>")

    out.print("</tr>")

    for r <- rows do
      out.print("<tr>")

      // left header
      out.print(s"<th>${paddedBinary(r, 3)}</th>")

      for c <- columns do
        val fullInt = (r << (3 + 2)) + (c << 2) + n

        lookup.get(fullInt) match
          case Some((ints, mode)) =>
            val hex = f"$fullInt%2X"
            out.print(
              s"""<th class="${ints.theme}" style="${"background-color: " + ints.color}">$ints $mode<br>$hex</th>"""
            )

          case None =>
            out.print(s"<td>UNDEF</td>")

      out.print("</tr>")

    out.print("</table>")

  def wideColumns: Seq[Int] =
    for {
      cc <- 0 to 3
      y  <- 0 to 1
      xx <- 0 to 3
    } yield (xx << 3) + (y << 2) + cc

  def wideRows: Seq[Int] =
    0 to 7

  private def write(file: String)(f: PrintWriter => Unit) =
    val out = new PrintWriter(file)
    f(out)
    out.close()

  def toOpcode(n: Int): Option[(Instruction, AddressingMode)] =
    val BitPattern = ThreeBits >> ThreeBits >> TwoBits

    n match
      case BitPattern((aaa, bbb), cc) =>
        cc match
          case 0 => c00(aaa, bbb)
          case 1 => c01(aaa, bbb)
          case 2 => c10(aaa, bbb)
          case 3 => None
      case _ => throw new IllegalStateException("an int should always have bits to find")

  def c01(aaa: Int, bbb: Int): Option[(Instruction, AddressingMode)] =
    val instruction =
      Seq(ORA, AND, EOR, ADC, STA, LDA, CMP, SBC)(aaa)

    val addressingMode =
      Seq(IndirectX, ZeroPage, Immediate, Absolute, IndirectY, ZeroPageX, AbsoluteY, AbsoluteX)(bbb)

    (instruction, addressingMode) match
      case (STA, Immediate) =>
        None

      case _ =>
        (instruction -> addressingMode).some

  def c10(aaa: Int, bbb: Int): Option[(Instruction, AddressingMode)] =
    val instruction =
      Seq(ASL, ROL, LSR, ROR, STX, LDX, DEC, INC)(aaa)

    val addressingMode =
      Seq(Immediate, ZeroPage, Accumulator, Absolute, NoMode, ZeroPageX, Implied, AbsoluteX)(bbb)

    val useLookup =
      (instruction -> addressingMode).some

    (instruction, addressingMode) match
      case (ASL | ROL | LSR | ROR, ZeroPage | Accumulator | Absolute | ZeroPageX | AbsoluteX) =>
        useLookup

      case (STX, ZeroPage | Absolute) =>
        useLookup

      case (DEC | INC, ZeroPage | Absolute | AbsoluteX | ZeroPageX) =>
        useLookup

      case (LDX, Immediate | ZeroPage | Absolute) =>
        useLookup

      case _ =>
        None

  def c00(aaa: Int, bbb: Int): Option[(Instruction, AddressingMode)] =
    val instruction =
      Seq(NoInstruction, BIT, NoInstruction, NoInstruction, STY, LDY, CPY, CPX)(aaa)

    val addressingMode =
      Seq(Immediate, ZeroPage, NoMode, Absolute, NoMode, ZeroPageX, NoMode, AbsoluteX)(bbb)

    val useLookup =
      (instruction -> addressingMode).some

    (instruction, addressingMode) match
      case (BIT, ZeroPage | Absolute) =>
        useLookup

      case (STY, ZeroPage | Absolute | ZeroPageX) =>
        useLookup

      case (LDY, Immediate | ZeroPage | Absolute | ZeroPageX | AbsoluteX) =>
        useLookup

      case (CPY | CPX, Immediate | ZeroPage | Absolute) =>
        useLookup

      case _ =>
        None
