- normalize A, X, Y, ZP, and GP to "registers", allocated by an allocator
  - X and Y are also index registers, so slightly special
    - unit test: what if a for loop is three deep? the outermost loop must be a memory register
- it would be cool for the 22-tuple Scala code generator code writer to go to files
  - and make the +1 methods optional (for when it is at max == 22)
- the payload/`xs` of a program should be an ADT of either chunks or one instruction
  - where a chunk is just many instructions
  - and an instruction already has the value X with its encoder, but chooses an eternal mode
    - depending on the asm language or config
- create classes for read/write, read-only (volatile), and write-only memory locations
  - zero page and absolute
  - but how do we encode it? subclassing?
- writing a value does two things
  1. establishes a fungible "init" phase (where we can take advantage of accumulator sharing)
  2. allows read-leases for some known scope
- shared initialization can happen with constants but can also be shared with read side effects
  - is the spec for reading from a register descriable as data? e.g. are these two requests semantically equal vs independent side-effects
- but what then is a write lease and how is it finite?
- every register must offer up read or write leases that other instructions can use and emit AsmN programs of
  - a method that offers up a lease maybe has a return type completely inherited from its body (doesn't know N shape, other than that the register should participate somewhere)
- maybe the AXY registers don't offer up leases and are always consumed in predictable, prepackaged ways
- there needs to be another abstraction. just because reads and writes are tracked, doesn't mean they tie to exactly single addresses (think of a mechanism with many independent switches, all producing separate write actions)
- automatic address assignment; if you stack them in a list, you can at "compile" time just assign registers from 0 to n
  - have an easy combinator to switch between byte and word length
  - and another combinator to switch between zero and global (maybe global is the default and zero is opt-in)
- stack register assignment
  - helper functions like multiplication (?) probably need a temp working area
  - if always used like a well bounded resource, maybe you can keep reusing this temp area with different functions
  - but if some one subroutine or "context" uses a function twice (e.g. 3 * 4 * 5) then the stack depth for that context is at least two now, which can be known at interpretation time by going through the call graph
    - imagine the multiplier operation providing context/a lease and every operation on that lease actually pushes onto a stack
      - 99% of the time the stack size would just be one but it could be for nested calls something else
      - and then very late into register assignment (above) it would occupy N registers
  - what if you model all functions using the same "bounce" area and then just use this as the canonical way to calculate stack depth
    - this would maybe be "optimal" register allocation?

## Advanced vs basic interpreters

- Given "basic" elements `foo` and `bar`
- And given another advanced feature `superfoo`
- `foo` and `bar` should inhereit both `BasicInterpreter` and `AdvancedInterpreter`
- and `superfoo` should only inherit `AdvancedInterpreter`
- Then `superfoo` just be desugared into many `BasicInterpreter` blocks
- And then the interpretation for `foo` and `bar` under advanced just echos them as `BasicInterpreter`
