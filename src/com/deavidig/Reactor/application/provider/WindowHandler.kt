package com.deavidig.Reactor.application.provider

import com.deavidig.Reactor.application.Window
import com.deavidig.Reactor.graphics.scale.Pixel
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWWindowPosCallbackI

public open class WindowHandler(protected val mWindow: Window) {
	private lateinit var mWindowPositionChangeListener: Window.OnWindowPositionChangeListener;

	private val mWindowPosCallbackI: GLFWWindowPosCallbackI = GLFWWindowPosCallbackI { window, x, y -> if (::mWindowPositionChangeListener.isInitialized) this.mWindowPositionChangeListener.onAfterWindowPositionChanged(window = this.mWindow, x = Pixel(x), y = Pixel(y)) };

	public final fun getWindow(): Window = this.mWindow;

	public final fun setWindowPositionChangeListener(listener: Window.OnWindowPositionChangeListener) {
		this.mWindowPositionChangeListener = listener;
	}

	public final fun registerWindow() {
		GLFW.glfwSetWindowPosCallback(this.mWindow.getWindowIdentifier(), this.mWindowPosCallbackI)
	}

	public final fun getWindowPositionChangeListener(): Window.OnWindowPositionChangeListener? = if (::mWindowPositionChangeListener.isInitialized) this.mWindowPositionChangeListener else null;
}
