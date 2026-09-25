package com.deavidig.Reactor.content

import com.deavidig.Reactor.content.res.Directory
import com.deavidig.Reactor.content.res.File
import com.deavidig.Reactor.content.res.PortableNetworkGraphic
import java.util.Properties

interface Resources {
	// Lazy mode! (Get all (only the called) only when use/instance)
	public fun getDirectories(): Array<Directory>

	// Lazy mode! (Get all (only the called) only when use/instance)
	public fun getFiles(): Array<File>;

	// Lazy mode! (Get all (only the called) only when use/instance)
	public fun getPortableNetworkGraphics(): Array<PortableNetworkGraphic>

	// Lazy mode! (Get all (only the called) only when use/instance)
	public fun getProperties(): Array<Properties>;
}