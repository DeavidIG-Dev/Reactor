package com.deavidig.Reactor.content.res

import com.deavidig.Reactor.application.provider.WindowManager

public interface Directory {
	public fun getDirectories(): Sequence<Directory>

	public fun getFiles(): Sequence<File>;

	public fun getPortableNetworkGraphics(): Sequence<PortableNetworkGraphic>

	public fun getName(): String
}