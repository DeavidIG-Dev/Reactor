package com.deavidig.Reactor.content.res

public interface File : Content<String> {
	public override fun getContent(): String;

	public fun getByteContent(): ByteArray;

	public fun getName(): String;
}