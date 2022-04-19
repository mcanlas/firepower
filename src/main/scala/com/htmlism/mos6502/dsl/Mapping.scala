package com.htmlism.mos6502.dsl

import cats.data.NonEmptyList

/**
  * Like an enum, but values are specified
  */
trait Mapping[A]:
  def definitionGroupComment: String

  /**
    * An ordered list of every value in this enumeration
    */
  def all: NonEmptyList[A]

  def value(x: A): Int

  /**
    * ASM-safe label
    */
  def label(x: A): String

  /**
    * Comment string
    */
  def comment(x: A): String

object Mapping:
  implicit def mappingForBitField[A](implicit ev: BitField[A]): Mapping[A] =
    new Mapping[A] {
      private lazy val valueMap =
        ev.all
          .toList
          .zip(List.iterate(1, ev.all.size)(_ << 1))
          .toMap

      def definitionGroupComment: String =
        ev.definitionGroupComment

      def all: NonEmptyList[A] =
        ev.all

      def value(x: A): Int =
        valueMap(x)

      def label(x: A): String =
        ev.label(x)

      def comment(x: A): String =
        "" // TODO
    }

  implicit def mappingForEnumAsm[A](implicit ev: EnumAsm[A]): Mapping[A] =
    new Mapping[A] {
      private lazy val valueMap =
        ev.all
          .toList
          .zip(List.iterate(0, ev.all.size)(_ + 1))
          .toMap

      def definitionGroupComment: String =
        ev.comment

      def all: NonEmptyList[A] =
        ev.all

      def value(x: A): Int =
        valueMap(x)

      def label(x: A): String =
        ev.label(x)

      def comment(x: A): String =
        ev.comment(x)
    }
