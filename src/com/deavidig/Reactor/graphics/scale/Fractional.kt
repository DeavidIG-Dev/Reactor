package com.deavidig.Reactor.graphics.scale

import com.deavidig.Reactor.Reactor

class Fractional(value: Double) : Dimension(value = if (value !in 0.0..1.0) Reactor.setReactorError(errorType = Reactor.ReactorErrorType.IllegalArgumentValue, "The fractional value ($value) only is minor of 1.0 or mayor of 0") else value);