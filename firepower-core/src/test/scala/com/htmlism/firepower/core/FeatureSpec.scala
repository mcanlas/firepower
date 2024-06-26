package com.htmlism.firepower.core

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.*

import com.htmlism.firepower.core.syntax.*

object ExampleRegister extends ZeroPageAddress(0x01, "example") with WriteOnlyByteAddress[ExampleRegister]

class ExampleRegister

class FeatureSpec extends AnyFunSuite with Matchers:
  test("zero page address as write only supports writing"):
    ExampleRegister
      .writeConst(2)[Reg.A] shouldBe "LDA 2 STA 1 ; example = 2, via A"

  test("zero page address as read/write supports writing"):
    ExampleRegister
      .writeConst(2)[Reg.A] shouldBe "LDA 2 STA 1 ; example = 2, via A"

  test("writing to an address can use A registers for bouncing"):
    ExampleRegister
      .writeConst(2)[Reg.A] shouldBe "LDA 2 STA 1 ; example = 2, via A"

  test("writing to an address can use X registers for bouncing"):
    ExampleRegister
      .writeConst(2)[Reg.X] shouldBe "LDX 2 STX 1 ; example = 2, via X"

  test("writing to an address can use Y registers for bouncing"):
    ExampleRegister
      .writeConst(2)[Reg.Y] shouldBe "LDY 2 STY 1 ; example = 2, via Y"

  ignore("the write payload is a typesafe enum") {}

  // intializations must be constant
  // cannot involve reading from a register, that is a side-effect
  // can also support 16-bit initializations
  ignore("initializations of the same value and register are aggReg.Ated") {}
