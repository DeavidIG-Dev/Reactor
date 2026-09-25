package com.deavidig.Reactor.graphics.scale

import com.deavidig.Reactor.Reactor

class Percentage(value: Int) : Dimension(value = if (value !in 0..100) Reactor.setReactorError(errorType = Reactor.ReactorErrorType.IllegalArgumentValue, "The percentage value ($value) only is minor of 100 or mayor of 0") else value.toDouble());