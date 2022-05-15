package com.htmlism.scratchpad

sealed trait MutationStatus

trait Unknown extends MutationStatus
trait Known extends MutationStatus
