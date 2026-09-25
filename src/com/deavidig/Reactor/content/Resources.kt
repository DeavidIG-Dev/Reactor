package com.deavidig.Reactor.content

import com.deavidig.Reactor.content.res.Directory
import com.deavidig.Reactor.content.res.File
import com.deavidig.Reactor.content.res.PortableNetworkGraphic
import java.util.Properties

interface Resources {
	public fun getDirectories(): Array<Directory>

	public fun getFiles(): Array<File>;

	public fun getPortableNetworkGraphics(): Array<PortableNetworkGraphic>

	public fun getProperties(): Array<Properties>;
}