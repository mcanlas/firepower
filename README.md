# firepower

## Addressing modes

* Immediate: a value literal
* ZeroPage: argument is an 8-bit address to the zero page
* ZeroPageX: argument is an 8-bit address to the zero page, incremented by X without carry
* ZeroPageY: argument is an 8-bit address to the zero page, incremented by Y without carry
* Absolute: argument is an 16-bit address
* AbsoluteX: argument is an 16-bit address, incremented by X with carry
* AbsoluteY: argument is an 16-bit address, incremented by Y with carry
* Indirect: argument an address (A); the effective value is an address (B) stored at that address (A); used only by Jump
* IndirectX: argument is an 8-bit address; find a value at this address incremented by the value X
* IndirectY: argument is an 8-bit address; find a value at this address; increment the value by Y

## TODO - Nescant

- mappings, enums, and bitfields are enums

## TODO

1. operand support should be derived from enum support

- postfix operations to accumulator values
- maybe implement define registry
- register locking (i.e. disallow X writes during indexed traversal that uses X)
- compiler optimization
  - when writing multiple, 16-bit address literals to memory, they may share the same high byte. if all writes are done by bouncing off the accumulator, you can save instructions by using the accumulator for the high byte once and writing multiple times
- articulate AST as different from ASM stream
- register locking (transactions) via phantom types

## Mutation tracking

- If the injected scope is local to the caller (and not reused), then it is also local to the called method, and can be rendered as a local val declaration

## Elsewhere

- [Easy 6502](https://skilldrick.github.io/easy6502/)
- [Annotated Snake6502](https://gist.github.com/wkjagt/9043907)
