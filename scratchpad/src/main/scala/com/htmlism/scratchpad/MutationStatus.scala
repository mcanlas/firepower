package com.htmlism.scratchpad

sealed trait MutationStatus

class Modifies() extends MutationStatus

case class Ignores() extends Modifies
