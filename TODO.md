- create classes for read/write, read-only (volatile), and write-only memory locations
  - zero page and absolute
  - but how do we encode it? subclassing?
- writing a value does two things
  1. establishes a fungible "init" phase (where we can take advantage of accumulator sharing)
  2. allows read-leases for some known scope
- shared initialization can happen with constants but can also be shared with read side effects
  - is the spec for reading from a register descriable as data? e.g. are these two requests semantically equal vs independent side-effects
- but what then is a write lease and how is it finite?
