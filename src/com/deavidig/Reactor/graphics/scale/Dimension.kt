package com.deavidig.Reactor.graphics.scale

public abstract sealed class Dimension(private val value: Double) {
	public fun getDimension(): Double = value

	public override fun toString(): String = "${javaClass.simpleName}(${javaClass.simpleName.lowercase()}=$value)"
}