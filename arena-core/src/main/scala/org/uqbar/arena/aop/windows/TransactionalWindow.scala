package org.uqbar.arena.aop.windows

import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner

abstract class TransactionalWindow[T](parent: WindowOwner, model: T)
	extends SimpleWindow[T](parent, model)
	with DialogTrait[T]
	with TransactionalWindowTrait[T] {
}