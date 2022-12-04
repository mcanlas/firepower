package com.htmlism.firepower.core

trait WriteLease[A]:
  def canon: A

  def to(f: A => Int): WriteLease.ByteAddress[A] =
    WriteLease.ByteAddress(f(canon))

object WriteLease:
  case class ByteAddress[A](address: Int)

  case class WordAddress[A](address: Int)
