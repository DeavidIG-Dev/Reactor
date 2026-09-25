package com.deavidig.Reactor.content.res

import java.awt.image.BufferedImage

public interface PortableNetworkGraphic : Content<ByteArray> {
	public override fun getContent(): ByteArray;

	public fun getImage(): BufferedImage;

	public fun getWidth(): Int;

	public fun getHeight(): Int;

	public fun getName(): String;
}