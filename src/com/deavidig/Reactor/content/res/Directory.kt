package com.deavidig.Reactor.content.res

public interface Directory {
	public fun getDirectories(): Array<Directory>

	public fun getFiles(): Array<File>;

	public fun getPortableNetworkGraphics(): Array<PortableNetworkGraphic>

	public fun getName(): String
}