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
