package com.htmlism.firepower.core

sealed trait MutationStatus

class Modifies() extends MutationStatus

case class Ignores() extends Modifies
