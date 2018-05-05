package com.htmlism

sealed trait Instruction { def theme: String; def color: String }

case object NoInstruction extends Instruction { def theme: String = "noop"; def color: String = "white" }

sealed trait Logical extends Instruction { def theme: String = "logical"; def color: String = "OliveDrab" }

case object AND extends Logical
case object EOR extends Logical
case object ORA extends Logical
case object BIT extends Logical

sealed trait Arithmetic extends Instruction { def theme: String = "arithmetic"; def color: String = "CadetBlue" }

case object ADC extends Arithmetic
case object SBC extends Arithmetic
case object CMP extends Arithmetic
case object CPX extends Arithmetic
case object CPY extends Arithmetic

sealed trait Load extends Instruction { def theme: String = "load"; def color: String = "BurlyWood" }
case object LDA extends Load
case object LDX extends Load
case object LDY extends Load

sealed trait Store extends Instruction { def theme: String = "store"; def color: String = "Bisque" }

case object STA extends Store
case object STX extends Store
case object STY extends Store

sealed trait Shift extends Instruction { def theme: String = "shift"; def color: String = "SlateBlue" }

case object ASL extends Shift
case object LSR extends Shift
case object ROL extends Shift
case object ROR extends Shift

sealed trait Increment extends Instruction { def theme: String = "increment"; def color: String = "LightPink" }

case object INC extends Increment
case object INX extends Increment
case object INY extends Increment

sealed trait Decrement extends Instruction { def theme: String = "decrement"; def color: String = "Khaki" }

case object DEC extends Decrement
case object DEX extends Decrement
case object DEY extends Decrement

sealed trait Jump extends Instruction { def theme: String = "jump"; def color: String = "Salmon" }
case object JMP extends Jump
case object JSR extends Jump
case object RTS extends Jump

sealed trait Branch extends Instruction { def theme: String = "branch"; def color: String = "DodgerBlue" }
case object BCC extends Branch
case object BCS extends Branch
case object BEQ extends Branch
case object BMI extends Branch
case object BNE extends Branch
case object BPL extends Branch
case object BVC extends Branch
case object BVS extends Branch

sealed trait System extends Instruction { def theme: String = "system"; def color: String = "Peru" }
case object BRK extends System
case object NOP extends System
case object RTI extends System

sealed trait Stack extends Instruction { def theme: String = "stack"; def color: String = "Wheat" }
case object TSX extends Stack
case object TXS extends Stack
case object PHA extends Stack
case object PHP extends Stack
case object PLA extends Stack
case object PLP extends Stack

sealed trait Transfer extends Instruction { def theme: String = "transfer"; def color: String = "Teal" }
case object TAX extends Transfer
case object TAY extends Transfer
case object TXA extends Transfer
case object TYA extends Transfer

sealed trait Clear extends Instruction { def theme: String = "clear"; def color: String = "LightSteelBlue" }
case object CLC extends Clear
case object CLD extends Clear
case object CLI extends Clear
case object CLV extends Clear

sealed trait SetFlag extends Instruction { def theme: String = "set"; def color: String = "Thistle" }
case object SEC extends SetFlag
case object SED extends SetFlag
case object SEI extends SetFlag
