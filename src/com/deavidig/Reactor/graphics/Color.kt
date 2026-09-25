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
		public val WHITE: Color = Color(red = 255, green = 255, blue = 255, alpha = 255)
		public val YELLOW: Color = Color(red = 255, green = 255, blue = 0, alpha = 255)
	}
}

class SubColor {
	val rGB: Int

	private var mFalpha = 0.0f
	private var mOvervalue: FloatArray? = null

	constructor(subColor: SubColor) {
		this.mFalpha = subColor.mFalpha
		this.mOvervalue = subColor.mOvervalue
		this.rGB = subColor.rGB
	}

	constructor(rgba: Int) {
		this.rGB = rgba
	}

	constructor(nm: String?) : this(decode(nm)!!)

	constructor(r: Float, g: Float, b: Float) : this(
		(r * 255 + 0.5).toInt(),
		(g * 255 + 0.5).toInt(),
		(b * 255 + 0.5).toInt()
	) {
		testColorValueRange(r, g, b, 1.0f)
		this.mFalpha = 1.0f
		this.mOvervalue = FloatArray(3)
		this.mOvervalue!![0] = r
		this.mOvervalue!![1] = g
		this.mOvervalue!![2] = b
	}

	constructor(r: Float, g: Float, b: Float, a: Float) : this(
		(r * 255 + 0.5).toInt(),
		(g * 255 + 0.5).toInt(),
		(b * 255 + 0.5).toInt(),
		(a * 255 + 0.5).toInt()
	) {
		this.mFalpha = a
		this.mOvervalue = FloatArray(3)
		this.mOvervalue!![0] = r
		this.mOvervalue!![1] = g
		this.mOvervalue!![2] = b
	}

	@JvmOverloads
	constructor(r: Int, g: Int, b: Int, a: Int = 255) {
		validate(r, g, b, a)
		this.rGB = ((a and 0xFF) shl 24) or
				((r and 0xFF) shl 16) or
				((g and 0xFF) shl 8) or
				((b and 0xFF))
		testColorValueRange(r, g, b, a)
	}

	private fun validate(r: Int, g: Int, b: Int, a: Int) {
		if (r < 0 || r > 255) {
			Reactor.setReactorError(Reactor.JReactorErrorType.IllegalArgumentValue, "Red component out of range: " + r)
		}
		if (g < 0 || g > 255) {
			Reactor.setReactorError(Reactor.JReactorErrorType.IllegalArgumentValue, "Green component out of range: " + g)
		}
		if (b < 0 || b > 255) {
			Reactor.setReactorError(Reactor.JReactorErrorType.IllegalArgumentValue, "Blue component out of range: " + b)
		}
		if (a < 0 || a > 255) {
			Reactor.setReactorError(Reactor.JReactorErrorType.IllegalArgumentValue, "Alpha component out of range: " + a)
		}
	}

	override fun equals(obj: Any?): Boolean {
		return obj is SubColor && obj.rGB == this.rGB
	}

	fun brighter(): SubColor {
		val alpha = this.alpha
		var g = this.green
		var b = this.blue
		var r = this.red
		val i = (1.0 / (1.0 - FACTOR)).toInt()

		if (r == 0 && g == 0 && b == 0) {
			return SubColor(i, i, i, alpha)
		}

		if (b > 0 && b < i) b = i
		if (g > 0 && g < i) g = i
		if (r > 0 && r < i) r = i

		return SubColor(
			min((r / FACTOR).toInt(), 255),
			min((g / FACTOR).toInt(), 255),
			min((b / FACTOR).toInt(), 255),
			alpha
		)
	}

	val alpha: Int
		get() = ((this.rGB shr 24) and 0xFF)

	val blue: Int
		get() = ((this.rGB shr 0) and 0xFF)

	val green: Int
		get() = ((this.rGB shr 8) and 0xFF)

	val red: Int
		get() = ((this.rGB shr 16) and 0xFF)

	override fun hashCode(): Int {
		return this.rGB
	}

	override fun toString(): String {
		return (javaClass.getName() + "[red=" + this.red + ",green=" + this.green + ",blue=" + this.blue + ",alpha="
				+ this.alpha + "]")
	}

	companion object {
		private const val FACTOR = 0.7

		val BLACK: SubColor = SubColor(0, 0, 0)
		val BLUE: SubColor = SubColor(0, 0, 255)
		val CYAN: SubColor = SubColor(0, 255, 255)
		val DARK_GRAY: SubColor = SubColor(64, 64, 64)
		val GRAY: SubColor = SubColor(128, 128, 128)
		val GREEN: SubColor = SubColor(0, 255, 0)
		val LIGHT_GRAY: SubColor = SubColor(192, 192, 192)
		val MAGENTA: SubColor = SubColor(255, 0, 255)
		val ORANGE: SubColor = SubColor(255, 200, 0)
		val PINK: SubColor = SubColor(255, 175, 175)
		val RED: SubColor = SubColor(255, 0, 0)
		val WHITE: SubColor = SubColor(255, 255, 255)
		val YELLOW: SubColor = SubColor(255, 255, 0)

		private fun testColorValueRange(r: Float, g: Float, b: Float, a: Float) {
			var rangeError = false
			var badComponentString = ""
			if (a < 0.0 || a > 1.0) {
				rangeError = true
				badComponentString = badComponentString + " Alpha"
			}
			if (b < 0.0 || b > 1.0) {
				rangeError = true
				badComponentString = badComponentString + " Blue"
			}
			if (g < 0.0 || g > 1.0) {
				rangeError = true
				badComponentString = badComponentString + " Green"
			}
			if (r < 0.0 || r > 1.0) {
				rangeError = true
				badComponentString = badComponentString + " Red"
			}
			require(!rangeError) {
				("Color parameter outside of expected range:"
						+ badComponentString)
			}
		}

		private fun testColorValueRange(r: Int, g: Int, b: Int, a: Int) {
			var rangeError = false
			var badComponentString = ""

			if (a < 0 || a > 255) {
				badComponentString = badComponentString + " Alpha"
				rangeError = true
			}
			if (b < 0 || b > 255) {
				badComponentString = badComponentString + " Blue"
				rangeError = true
			}
			if (g < 0 || g > 255) {
				badComponentString = badComponentString + " Green"
				rangeError = true
			}
			if (r < 0 || r > 255) {
				badComponentString = badComponentString + " Red"
				rangeError = true
			}
			require(!rangeError) {
				("Color parameter outside of expected range:"
						+ badComponentString)
			}
		}

		@Throws(NumberFormatException::class)
		fun decode(nm: String?): SubColor? {
			var nm = nm
			if (nm == null) return null
			nm = nm.trim { it <= ' ' }

			when (nm.lowercase(Locale.getDefault())) {
				"cyan" -> return CYAN
				"blue" -> return BLUE
				"dark_gray", "darkgray" -> return DARK_GRAY
				"light_gray", "lightgray" -> return LIGHT_GRAY
				"gray" -> return GRAY
				"black" -> return BLACK
				"magenta" -> return MAGENTA
				"pink" -> return PINK
				"orange" -> return ORANGE
				"red" -> return RED
				"yellow" -> return YELLOW
				"green" -> return GREEN
				"white" -> return WHITE
			}

			if (nm.startsWith("#")) {
				nm = nm.substring(1)
			} else if (nm.startsWith("0x") || nm.startsWith("0X")) {
				nm = nm.substring(2)
			}

			val parts: Array<String?> = nm.split("[,;\\s]+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
			if (parts.size >= 3) {
				try {
					val r = parts[0]!!.trim { it <= ' ' }.toInt()
					val g = parts[1]!!.trim { it <= ' ' }.toInt()
					val b = parts[2]!!.trim { it <= ' ' }.toInt()
					val a = if (parts.size > 3) parts[3]!!.trim { it <= ' ' }.toInt() else 255
					return SubColor(r, g, b, a)
				} catch (e: NumberFormatException) {
				}
			}

			try {
				if (nm.length == 6) {
					return SubColor(nm.toInt(16) or -0x1000000)
				} else if (nm.length == 8) {
					return SubColor(nm.toLong(16).toInt())
				}
			} catch (e: NumberFormatException) {
			}

			throw NumberFormatException("Invalid color format: " + nm)
		}

		fun getHSBColor(h: Float, s: Float, b: Float): SubColor {
			return SubColor(HSBtoRGB(h, s, b))
		}

		fun RGBtoHSB(r: Int, g: Int, b: Int, hsbvals: FloatArray?): FloatArray {
			var hsbvals = hsbvals
			var cmax = if (r > g) r else g
			var cmin = if (r < g) r else g
			val brightness: Float
			var hue: Float
			val saturation: Float

			if (b > cmax) cmax = b
			if (b < cmin) cmin = b

			if (hsbvals == null) {
				hsbvals = FloatArray(3)
			}

			brightness = (cmax.toFloat()) / 255.0f
			if (cmax != 0) saturation = ((cmax - cmin).toFloat()) / (cmax.toFloat())
			else saturation = 0f

			if (saturation == 0f) hue = 0f
			else {
				val greenc = ((cmax - g).toFloat()) / ((cmax - cmin).toFloat())
				val bluec = ((cmax - b).toFloat()) / ((cmax - cmin).toFloat())
				val redc = ((cmax - r).toFloat()) / ((cmax - cmin).toFloat())

				if (g == cmax) hue = 2.0f + redc - bluec
				else if (r == cmax) hue = bluec - greenc
				else hue = 4.0f + greenc - redc
				hue = hue / 6.0f
				if (hue < 0) hue = hue + 1.0f
			}
			hsbvals[2] = brightness
			hsbvals[0] = hue
			hsbvals[1] = saturation
			return hsbvals
		}

		fun HSBtoRGB(hue: Float, saturation: Float, brightness: Float): Int {
			var r = 0
			var g = 0
			var b = 0
			if (saturation == 0f) {
				b = (brightness * 255.0f + 0.5f).toInt()
				g = b
				r = g
			} else {
				val f = hue - floor(hue.toDouble()).toFloat()
				val h = f * 6.0f
				val p = brightness * (1.0f - saturation)
				val q = brightness * (1.0f - saturation * f)
				val t = brightness * (1.0f - (saturation * (1.0f - f)))
				when (h.toInt()) {
					0 -> {
						r = (brightness * 255.0f + 0.5f).toInt()
						g = (t * 255.0f + 0.5f).toInt()
						b = (p * 255.0f + 0.5f).toInt()
					}

					1 -> {
						r = (q * 255.0f + 0.5f).toInt()
						g = (brightness * 255.0f + 0.5f).toInt()
						b = (p * 255.0f + 0.5f).toInt()
					}

					2 -> {
						r = (p * 255.0f + 0.5f).toInt()
						g = (brightness * 255.0f + 0.5f).toInt()
						b = (t * 255.0f + 0.5f).toInt()
					}

					3 -> {
						r = (p * 255.0f + 0.5f).toInt()
						g = (q * 255.0f + 0.5f).toInt()
						b = (brightness * 255.0f + 0.5f).toInt()
					}

					4 -> {
						r = (t * 255.0f + 0.5f).toInt()
						g = (p * 255.0f + 0.5f).toInt()
						b = (brightness * 255.0f + 0.5f).toInt()
					}

					5 -> {
						r = (brightness * 255.0f + 0.5f).toInt()
						g = (p * 255.0f + 0.5f).toInt()
						b = (q * 255.0f + 0.5f).toInt()
					}
				}
			}
			return -0x1000000 or (r shl 16) or (g shl 8) or (b shl 0)
		}
	}
}