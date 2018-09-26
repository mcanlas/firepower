package com.htmlism

trait BitExtractor[A] {
  self =>

  def length: Int

  def unapply(n: Int): Option[A]

  def >>[B](that: BitExtractor[B]): BitExtractor[(A, B)] =
    new BitExtractor[(A, B)] {
      def length: Int = self.length + that.length

      def unapply(n: Int): Option[(A, B)] =
        for {
          b <- that.unapply(n)
          shifted = n >> that.length
          a <- self.unapply(shifted)
        } yield (a, b)
    }
}

object AtomExtractor {
  def pow(ex: Int, acc: Int = 1): Int =
    if (ex == 0)
      acc
    else
      pow(ex - 1, acc * 2)
}

abstract class PrimitiveBitExtractor(val length: Int)
    extends BitExtractor[Int] {
  private lazy val mask = AtomExtractor.pow(length) - 1

  def unapply(n: Int): Option[Int] = Some(n & mask)
}

object OneBit    extends PrimitiveBitExtractor(1)
object TwoBits   extends PrimitiveBitExtractor(2)
object ThreeBits extends PrimitiveBitExtractor(3)
