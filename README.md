# 6502-opcodes

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

## TODO

- postfix operations to accumulator values
- implement jump registry
- maybe implement define registry