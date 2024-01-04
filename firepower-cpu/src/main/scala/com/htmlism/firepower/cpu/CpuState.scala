package com.htmlism.firepower.cpu

case class CpuState(
    accumulator: UByte,
    xRegister: UByte,
    yRegister: UByte,
    stackPointer: UByte,
    programCounterLo: UByte,
    programCounterHi: UByte,
    negative: Boolean,
    overflow: Boolean,
    break: Boolean,
    decimal: Boolean,
    interruptsDisabled: Boolean,
    zero: Boolean,
    carry: Boolean
)

object CpuState:
  val empty: CpuState =
    CpuState(
      accumulator        = UByte(0),
      xRegister          = UByte(0),
      yRegister          = UByte(0),
      stackPointer       = UByte(0),
      programCounterLo   = UByte(0),
      programCounterHi   = UByte(0),
      negative           = false,
      overflow           = false,
      break              = false,
      decimal            = false,
      interruptsDisabled = false,
      zero               = false,
      carry              = false
    )
