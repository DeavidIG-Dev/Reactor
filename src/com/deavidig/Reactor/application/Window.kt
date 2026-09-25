package com.deavidig.Reactor.application

import com.deavidig.Reactor.Reactor
import com.deavidig.Reactor.application.provider.WindowDelegate
import com.deavidig.Reactor.application.provider.WindowHandler
import com.deavidig.Reactor.content.Context
import com.deavidig.Reactor.content.Resources
import com.deavidig.Reactor.content.Settings
import com.deavidig.Reactor.content.Theme
import com.deavidig.Reactor.graphics.scale.Dimension
import com.deavidig.Reactor.graphics.scale.Pixel
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryUtil

public open class Window(private val application: Application) : Context {
	private val mWindowIdentifier: Long;
	private val mWindowDelegate: WindowDelegate;
	private val mWindowHandler: WindowHandler;

	init {
		application.setRegisterWindow(this)

		GLFW.glfwDefaultWindowHints()
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE)
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE)
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 4)
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 1)
		GLFW.glfwWindowHint(GLFW.GLFW_STENCIL_BITS, 8)

		this.mWindowIdentifier = GLFW.glfwCreateWindow(
			1,
			1,
			"Window",
			MemoryUtil.NULL,
			MemoryUtil.NULL
		)

		if (this.mWindowIdentifier == MemoryUtil.NULL) Reactor.setReactorError(Reactor.JReactorErrorType.FailedInitializeValue, "Cannot initialize the Window in ???")

		// this.mWindowDelegate.getIntSystemDimension(this.getWidth(), WindowDelegate.TARGET_VIDEO_WIDTH)
		// this.mWindowDelegate.getIntSystemDimension(this.getHeight(), WindowDelegate.TARGET_VIDEO_HEIGHT)

		this.mWindowDelegate = WindowDelegate(this)
		this.mWindowHandler = WindowHandler(this)

		onCreate()

		GLFW.glfwShowWindow(this.mWindowIdentifier)
		GLFW.glfwMakeContextCurrent(this.mWindowIdentifier)
		GLFW.glfwSwapInterval(GLFW.GLFW_TRUE);

		this.mWindowHandler.registerWindow()

		onStart()

		GL.createCapabilities()
	}

	public abstract class OnWindowPositionChangeListener() {
		public abstract fun onAfterWindowPositionChanged(window: Window, x: Pixel, y: Pixel): Unit;
		public abstract fun onBeforeWindowPositionChanged(window: Window, x: Pixel, y: Pixel): Unit;
	}

	public abstract class OnWindowSizeChangeListener() {
		public abstract fun onAfterWindowSizeChanged(window: Window, width: Pixel, height: Pixel): Unit;
		public abstract fun onBeforeWindowSizeChanged(window: Window, width: Pixel, height: Pixel): Unit;
	}

	public abstract class OnWindowResizeChangeListener() {
		public abstract fun onAfterWindowResizeChanged(window: Window, width: Pixel, height: Pixel): Unit;
		public abstract fun onBeforeWindowResizeChanged(window: Window, width: Pixel, height: Pixel): Unit;
	}

	public open fun onCreate(): Unit = Unit;

	public open fun onDestroy(): Unit = Unit;

	public open fun onStart(): Unit = Unit;

	public open fun onStop(): Unit = Unit;

	public open fun onResume(): Unit = Unit;

	internal fun onRender(): Unit = this.mWindowDelegate.onRender();

	public final fun setHeight(dimension: Dimension): Unit = this.mWindowDelegate.setHeight(dimension);

	public final fun setOnWindowPositionChangedListener(listener: OnWindowPositionChangeListener): Unit = this.mWindowHandler.setWindowPositionChangeListener(listener);

	public final fun setOnWindowSizeChangedListener(listener: OnWindowSizeChangeListener?): Unit = Unit;

	public final fun setWidth(dimension: Dimension): Unit = this.mWindowDelegate.setWidth(dimension);

	public final fun setX(dimension: Dimension): Unit = this.mWindowDelegate.setX(dimension);

	public final fun setY(dimension: Dimension): Unit = this.mWindowDelegate.setY(dimension);

	public final fun isCloseWindow(): Boolean = GLFW.glfwWindowShouldClose(this.mWindowIdentifier);

	public final fun isDirtyWindow(): Boolean = this.mWindowDelegate.isDirtyWindow();

	public final fun getApplication(): Application = application;

	public final fun getHeight(): Dimension = this.mWindowDelegate.getHeight();

	public final fun getOnWindowPositionChangedListener(): OnWindowPositionChangeListener? = this.mWindowHandler.getWindowPositionChangeListener();

	public final fun getOnWindowSizeChangedListener(): OnWindowSizeChangeListener? = null;

	public final fun getWindowIdentifier(): Long = this.mWindowIdentifier

	public final fun getWidth(): Dimension = this.mWindowDelegate.getWidth();

	public final fun getX(): Dimension = this.mWindowDelegate.getX();

	public final fun getY(): Dimension = this.mWindowDelegate.getY();

	public override fun setTheme(theme: Theme): Unit = Unit

	public override fun getTheme(): Theme = TODO("Not yet implemented")

	public override fun getResources(): Resources = TODO("Not yet implemented")

	public override fun getSettings(): Settings = this.mWindowDelegate.getSettings();
}