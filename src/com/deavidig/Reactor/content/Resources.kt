package com.deavidig.Reactor.content

import com.deavidig.Reactor.content.res.Directory
import com.deavidig.Reactor.content.res.File
import com.deavidig.Reactor.content.res.PortableNetworkGraphic
import java.util.Properties

interface Resources {
	// Lazy mode! (Get all (only the called) only when use/instance)
	public fun getDirectories(): Sequence<Directory>

	// Lazy mode! (Get all (only the called) only when use/instance)
	public fun getFiles(): Sequence<File>;

	// Lazy mode! (Get all (only the called) only when use/instance)
	public fun getPortableNetworkGraphics(): Sequence<PortableNetworkGraphic>

	// Lazy mode! (Get all (only the called) only when use/instance)
	public fun getProperties(): Sequence<Properties>;
}