package com.deavidig.Reactor.application.provider

import com.deavidig.Reactor.Reactor
import com.deavidig.Reactor.application.Window
import com.deavidig.Reactor.content.Settings
import com.deavidig.Reactor.graphics.Color
import com.deavidig.Reactor.graphics.scale.Dimension
import com.deavidig.Reactor.graphics.scale.Fractional
import com.deavidig.Reactor.graphics.scale.Percentage
import com.deavidig.Reactor.graphics.scale.Pixel
import com.deavidig.Reactor.graphics.system.TargetVideo
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWVidMode
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryUtil

public open class WindowDelegate(private val mWindow: Window) {
	private val mTargetVideo: TargetVideoImp = TargetVideoImp()

	private var mHeight: Dimension = Pixel(0);
	private var mWidth: Dimension = Pixel(0);
	private var mX: Dimension = Pixel(0);
	private var mY: Dimension = Pixel(0);

	private var mColor: Color = Color.BLACK

	private var mIsDirty: Boolean = true

	private val mSettings: Settings = Settings();

	companion object {
		public const val TARGET_VIDEO_WIDTH = 0x0001;
		public const val TARGET_VIDEO_HEIGHT = 0x0002;
	}

	internal final fun onRender(): Unit {
		if (this.mWindow.getWindowIdentifier() == MemoryUtil.NULL) return

		GLFW.glfwMakeContextCurrent(this.mWindow.getWindowIdentifier())

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT or GL11.GL_STENCIL_BUFFER_BIT)

		GL11.glClearColor(
			this.mColor.getRed() / 255f,
			this.mColor.getGreen() / 255f,
			this.mColor.getBlue() / 255f,
			this.mColor.getAlpha() / 255f
		)

		this.mIsDirty = false
	}

	public final fun invalidateWindow(): Unit {
		this.mIsDirty = true

		val width = getIntSystemDimension(this.mWidth, TARGET_VIDEO_WIDTH)
		val height = getIntSystemDimension(this.mHeight, TARGET_VIDEO_HEIGHT)
		val x = getIntSystemDimension(this.mX, TARGET_VIDEO_WIDTH)
		val y = getIntSystemDimension(this.mY, TARGET_VIDEO_HEIGHT)

		GLFW.glfwSetWindowSize(this.mWindow.getWindowIdentifier(), width, height)
		GLFW.glfwSetWindowPos(this.mWindow.getWindowIdentifier(), x, y)

		this.mWindow.getOnWindowPositionChangedListener()?.onAfterWindowPositionChanged(this.mWindow, if (this.mX is Pixel) this.mX as Pixel else Pixel(getIntSystemDimension(this.mX, TARGET_VIDEO_WIDTH)), if (this.mY is Pixel) this.mY as Pixel else Pixel(getIntSystemDimension(this.mY, TARGET_VIDEO_HEIGHT)));
	}

	public final fun isDirtyWindow(): Boolean = this.mIsDirty

	public final fun setHeight(dimension: Dimension): Unit {
		this.mHeight = dimension;
		invalidateWindow()
	}

	public final fun setWidth(dimension: Dimension): Unit {
		this.mWidth = dimension
		invalidateWindow()
	}

	public final fun setX(x: Dimension): Unit {
		this.mWindow.getOnWindowPositionChangedListener()?.onBeforeWindowPositionChanged(this.mWindow, if (this.mX is Pixel) this.mX as Pixel else Pixel(getIntSystemDimension(this.mX, TARGET_VIDEO_WIDTH)), if (this.mY is Pixel) this.mY as Pixel else Pixel(getIntSystemDimension(this.mY, TARGET_VIDEO_HEIGHT)));
		this.mX = x;
		invalidateWindow()
	}

	public final fun setY(y: Dimension): Unit {
		this.mWindow.getOnWindowPositionChangedListener()?.onBeforeWindowPositionChanged(this.mWindow, if (this.mX is Pixel) this.mX as Pixel else Pixel(getIntSystemDimension(this.mX, TARGET_VIDEO_WIDTH)), if (this.mY is Pixel) this.mY as Pixel else Pixel(getIntSystemDimension(this.mY, TARGET_VIDEO_HEIGHT)));
		this.mY = y;
		invalidateWindow()
	}

	public final fun getHeight(): Dimension = this.mHeight;

	public final fun getSettings(): Settings = this.mSettings;

	public final fun getTargetVideo(): TargetVideo = this.mTargetVideo

	public final fun getWidth(): Dimension = this.mWidth;

	public final fun getWindow(): Window = mWindow;

	public final fun getX(): Dimension = this.mX;

	public final fun getY(): Dimension = this.mY;

	public open fun getIntSystemDimension(dimension: Dimension, type: Int): Int = when (dimension) {
		is Pixel -> dimension.getDimension().toInt()
		is Fractional -> ((if (type == TARGET_VIDEO_WIDTH) this.mTargetVideo.getWidth() else if (type == TARGET_VIDEO_HEIGHT) this.mTargetVideo.getHeight() else Reactor.setReactorError(arrayOf(Reactor.JReactorErrorType.IllegalArgumentValue, Reactor.JReactorErrorType.IllegalArgumentValue), "Only is posible use TARGET_VIDEO_WIDTH ($TARGET_VIDEO_WIDTH) or TARGET_VIDEO_HEIGHT ($TARGET_VIDEO_HEIGHT)"))  / dimension.getDimension()).toInt()
		is Percentage -> ((dimension.getDimension() * (if (type == TARGET_VIDEO_WIDTH) this.mTargetVideo.getWidth() else if (type == TARGET_VIDEO_HEIGHT) this.mTargetVideo.getHeight() else Reactor.setReactorError(arrayOf(Reactor.JReactorErrorType.IllegalArgumentValue, Reactor.JReactorErrorType.IllegalArgumentValue), "Only is posible use TARGET_VIDEO_WIDTH ($TARGET_VIDEO_WIDTH) or TARGET_VIDEO_HEIGHT ($TARGET_VIDEO_HEIGHT)")) ) / 100f).toInt();
	}

	private final class TargetVideoImp() : TargetVideo {
		private final val mTargetVideo: GLFWVidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())!!;

		override fun getWidth(): Int = this.mTargetVideo.width();

		override fun getHeight(): Int = this.mTargetVideo.height();

		override fun getRedBit(): Int = this.mTargetVideo.redBits();

		override fun getGreenBit(): Int = this.mTargetVideo.greenBits();

		override fun getBlueBit(): Int = this.mTargetVideo.blueBits();

		override fun getRateRefresh(): Int = this.mTargetVideo.refreshRate();
	}
}