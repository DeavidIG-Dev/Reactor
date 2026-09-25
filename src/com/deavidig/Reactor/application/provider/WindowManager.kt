package com.deavidig.Reactor.application.provider

import com.deavidig.Reactor.Reactor
import com.deavidig.Reactor.application.Window
import com.deavidig.Reactor.content.Resources
import com.deavidig.Reactor.content.res.Directory
import com.deavidig.Reactor.content.res.File
import com.deavidig.Reactor.content.res.PortableNetworkGraphic
import java.awt.image.BufferedImage
import java.net.URL
import java.nio.ByteBuffer
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Properties
import javax.imageio.ImageIO
import kotlin.io.path.extension
import kotlin.io.path.name

internal final class WindowManager internal constructor(private val mWindow: Window) {
	public fun getResource(): Resources {
		val uri: URL? = this.mWindow.javaClass.getResource("/");
		val path: Path? = if (uri == null) null else Paths.get(uri.toURI());
		return if (path != null) ResourceImpl(path) else Reactor.setReactorError(Reactor.ReactorErrorType.FailedInitializeValue, "Cannot is possible to get resource")
	}
}

private class ResourceImpl(private val mPath: Path) : Resources {
	override fun getDirectories(): Sequence<Directory> =
		sequence {
			Files.newDirectoryStream(mPath).use { directory ->
				for (path in directory) {
					if (Files.isDirectory(path)) {
						yield(DirectoryImpl(path))
					}
				}
			}
		}

	override fun getFiles(): Sequence<File> =
		sequence {
			Files.newDirectoryStream(mPath).use { directory ->
				for (path in directory) {
					if (Files.isRegularFile(path)) {
						yield(FileImpl(path))
					}
				}
			}
		}

	override fun getPortableNetworkGraphics(): Sequence<PortableNetworkGraphic> =
		sequence {
			Files.newDirectoryStream(mPath).use { directory ->
				for (path in directory) {
					if (
						Files.isRegularFile(path) &&
						path.extension.equals("png", ignoreCase = true)
					) {
						yield(PortableNetworkGraphicImpl(path))
					}
				}
			}
		}

	override fun getProperties(): Sequence<Properties> {
		TODO("Not yet implemented")
	}
}

private class DirectoryImpl(private val mPath: Path) : Directory {
	override fun getDirectories(): Sequence<Directory>  =
		sequence {
			Files.newDirectoryStream(mPath).use { directory ->
				for (path in directory) {
					if (Files.isDirectory(path)) {
						yield(DirectoryImpl(path))
					}
				}
			}
		}

	override fun getFiles(): Sequence<File> =
		sequence {
			Files.newDirectoryStream(mPath).use { directory ->
				for (path in directory) {
					if (Files.isRegularFile(path)) {
						yield(FileImpl(path))
					}
				}
			}
		}

	override fun getPortableNetworkGraphics(): Sequence<PortableNetworkGraphic> =
		sequence {
			Files.newDirectoryStream(mPath).use { directory ->
				for (path in directory) {
					if (
						Files.isRegularFile(path) &&
						path.extension.equals("png", ignoreCase = true)
					) {
						yield(PortableNetworkGraphicImpl(path))
					}
				}
			}
		}

	override fun getName(): String = this.mPath.fileName.name
}

private class FileImpl(private val mPath: Path) : File {
	override fun getContent(): String = Files.readString(mPath)

	override fun getSize(): Long = Files.size(this.mPath)

	override fun getByteContent(): ByteArray = Files.readAllBytes(mPath)

	override fun getName(): String = this.mPath.fileName.name
}

private class PortableNetworkGraphicImpl(
	private val mPath: Path
) : PortableNetworkGraphic {

	override fun getContent(): ByteArray = Files.readAllBytes(this.mPath)

	override fun getSize(): Long = Files.size(this.mPath)

	override fun getWidth(): Int =
		readDimension(16)

	override fun getHeight(): Int =
		readDimension(20)

	override fun getImage(): BufferedImage =
		ImageIO.read(this.mPath.toFile())

	override fun getName(): String =
		this.mPath.fileName.name

	private fun readDimension(offset: Long): Int {
		Files.newByteChannel(this.mPath).use { channel ->
			channel.position(offset)

			val buffer = ByteBuffer.allocate(Int.SIZE_BYTES)
			channel.read(buffer)

			buffer.flip()

			return buffer.int
		}
	}
}