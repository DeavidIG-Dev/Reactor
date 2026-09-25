package com.deavidig.Reactor.content.res

public interface PortableNetworkGraphic : Content<ByteArray> {
	public override fun getContent(): ByteArray;

	public fun getWidth(): Int;

	public fun getHeight(): Int;

	public fun getName(): String;
}