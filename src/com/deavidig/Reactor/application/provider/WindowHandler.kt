package com.deavidig.Reactor.application.provider

import com.deavidig.Reactor.application.Window
import com.deavidig.Reactor.application.provider.WindowDelegate.Companion.TARGET_VIDEO_WIDTH
import com.deavidig.Reactor.graphics.scale.Percentage
import com.deavidig.Reactor.graphics.scale.Pixel
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWWindowPosCallbackI

public open class WindowHandler(protected val mWindow: Window) {
	private var mWindowPositionChangeListener: Window.OnWindowPositionChangeListener? = null;

	private val mWindowPosCallbackI: GLFWWindowPosCallbackI = GLFWWindowPosCallbackI { _, x, y ->
		this.mWindow.mWindowDelegate.setX(Pixel(x));
		this.mWindow.mWindowDelegate.setY(Pixel(y));
	};

	public final fun getWindow(): Window = this.mWindow;

	public final fun setWindowPositionChangeListener(listener: Window.OnWindowPositionChangeListener?): Unit {
		this.mWindowPositionChangeListener = listener;
		invalidate()
	}

	public final fun getWindowPositionChangeListener(): Window.OnWindowPositionChangeListener? = this.mWindowPositionChangeListener;

	public open fun invalidate(): Unit {
		GLFW.glfwSetWindowPosCallback(this.mWindow.getWindowIdentifier(), this.mWindowPosCallbackI)
	}
}
