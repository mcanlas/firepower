package com.htmlism.scratchpad

trait GrantsWriteLeases[A]:
  def withWriteLease[B](f: WriteLease[A] => B)(using A: Companion[A]): B =
    val lease =
      new WriteLease[A]:
        def canon: A =
          A.canon

    f(lease)
