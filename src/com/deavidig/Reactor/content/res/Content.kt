package com.deavidig.Reactor.content.res

public sealed interface Content<T> {
	public fun getContent(): T;

	public fun getSize(): Long;
}