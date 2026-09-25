package com.deavidig.Reactor.graphics

import com.deavidig.Reactor.Reactor
import java.util.Locale
import kotlin.experimental.and
import kotlin.math.floor
import kotlin.math.min

typealias NColor = Int

public class Color(red: Byte, green: Byte, blue: Byte, alpha: Byte) {
	private val color: NColor = ((alpha and COLOR_CAPACITY_FULL).toInt() shl ALPHA_SHL_SPACE) or
			((red and COLOR_CAPACITY_FULL).toInt() shl RED_SHL_SPACE) or
			((green and COLOR_CAPACITY_FULL).toInt() shl GREEN_SHL_SPACE) or
			((blue and COLOR_CAPACITY_FULL).toInt() shl BLUE_SHL_SPACE);

	constructor(red: Byte, green: Byte, blue: Byte) : this(red = red, green = green, blue = blue, alpha = COLOR_CAPACITY_FULL)

	constructor(red: Int, green: Int, blue: Int) : this(red = red.toByte(), green = green.toByte(), blue = blue.toByte(), alpha = COLOR_CAPACITY_FULL)

	constructor(red: Int, green: Int, blue: Int, alpha: Int) : this(red = red.toByte(), green = green.toByte(), blue = blue.toByte(), alpha = alpha.toByte())

	constructor(rgba: Int) : this(red = ((rgba shr RED_SHL_SPACE) and COLOR_CAPACITY_FULL.toInt()).toByte(), green = ((rgba shr GREEN_SHL_SPACE) and COLOR_CAPACITY_FULL.toInt()).toByte(), blue = ((rgba shr BLUE_SHL_SPACE) and COLOR_CAPACITY_FULL.toInt()).toByte(), alpha = ((rgba shr ALPHA_SHL_SPACE) and COLOR_CAPACITY_FULL.toInt()).toByte())

	public fun getAlpha(): Byte = ((this.color shr ALPHA_SHL_SPACE) and COLOR_CAPACITY_FULL.toInt()).toByte()

	public fun getBlue(): Byte = ((this.color shr BLUE_SHL_SPACE) and COLOR_CAPACITY_FULL.toInt()).toByte()

	public fun getGreen(): Byte = ((this.color shr GREEN_SHL_SPACE) and COLOR_CAPACITY_FULL.toInt()).toByte()

	public fun getRed(): Byte = ((this.color shr RED_SHL_SPACE) and COLOR_CAPACITY_FULL.toInt()).toByte()

	public final override fun toString(): String = "${javaClass.simpleName}(red=${getRed()}, green=${getGreen()}, blue=${getBlue()}, alpha=${getAlpha()})"

	companion object {
		private const val COLOR_CAPACITY_FULL: Byte = 0xFF.toByte()

		private const val ALPHA_SHL_SPACE: Int = 24
		private const val RED_SHL_SPACE: Int = 16
		private const val GREEN_SHL_SPACE: Int = 8
		private const val BLUE_SHL_SPACE: Int = 0

		public val BLACK: Color = Color(red = 0, green = 0, blue = 0, alpha = 255)
		public val BLUE: Color = Color(red = 0, green = 0, blue = 255, alpha = 255)
		public val CYAN: Color = Color(red = 0, green = 255, blue = 255, alpha = 255)
		public val DARK_GRAY: Color = Color(red = 64, green = 64, blue = 64, alpha = 255)
		public val GRAY: Color = Color(red = 128, green = 128, blue = 128, alpha = 255)
		public val GREEN: Color = Color(red = 0, green = 255, blue = 0, alpha = 255)
		public val LIGHT_GRAY: Color = Color(red = 192, green = 192, blue = 192, alpha = 255)
		public val MAGENTA: Color = Color(red = 255, green = 0, blue = 255, alpha = 255)
		public val ORANGE: Color = Color(red = 255, green = 200, blue = 0, alpha = 255)
		public val PINK: Color = Color(red = 255, green = 175, blue = 175, alpha = 255)
		public val RED: Color = Color(red = 255, green = 0, blue = 0, alpha = 255)
		public val TRANSPARENT: Color = Color(red = 0, green = 0, blue = 0, alpha = 0)
		public val WHITE: Color = Color(red = 255, green = 255, blue = 255, alpha = 255)
		public val YELLOW: Color = Color(red = 255, green = 255, blue = 0, alpha = 255)
	}
}