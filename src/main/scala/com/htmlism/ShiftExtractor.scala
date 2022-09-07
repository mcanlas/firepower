package com.htmlism

import scala.annotation.tailrec

/**
  * Given an `Int`, destructure it into smaller `Int`s that use less bits
  */
trait BitExtractor[A]:
  self =>

  def length: Int

  def unapply(n: Int): Option[A]

  /**
    * A combinator for appending one extractor to another
    */
  def >>[B](that: BitExtractor[B]): BitExtractor[(A, B)] =
    new BitExtractor[(A, B)]:
      def length: Int = self.length + that.length

      def unapply(n: Int): Option[(A, B)] =
        for {
          b <- that.unapply(n)
          shifted = n >> that.length
          a <- self.unapply(shifted)
        } yield (a, b)

object AtomExtractor:
  @tailrec
  def pow(ex: Int, acc: Int = 1): Int =
    if (ex == 0)
      acc
    else
      pow(ex - 1, acc * 2)

abstract class PrimitiveBitExtractor(val length: Int) extends BitExtractor[Int]:
  private lazy val mask =
    AtomExtractor.pow(length) - 1

  def unapply(n: Int): Option[Int] =
    Some(n & mask)

object OneBit extends PrimitiveBitExtractor(1)
object TwoBits extends PrimitiveBitExtractor(2)
object ThreeBits extends PrimitiveBitExtractor(3)
