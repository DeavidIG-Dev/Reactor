package com.deavidig.Reactor.application

import com.deavidig.Reactor.Reactor
import org.lwjgl.glfw.GLFW

public open abstract class Application() {
	private var mWindows: MutableList<Window> = arrayListOf()

	init {
		onStart()
	}

	public final fun setWindowRun(run: Unit.() -> Unit): Unit {
		if (!GLFW.glfwInit()) Reactor.setReactorError(Reactor.ReactorErrorType.FailedInitializeValue, "Cannot initialize the GLFW for create Windows.");

		run.invoke(Unit);

		var hasDirty: Boolean;
		while (!this.mWindows.isEmpty()) {
			hasDirty = false;

			var i = 0;
			while (i < this.mWindows.size) {
				val w: Window = this.mWindows[i];

				if (w.isDirtyWindow()) {
					 w.onRender();
					hasDirty = true;
				}

				if (w.isCloseWindow()) {
					// w.onClose()
					this.mWindows.removeAt(i);
				} else {
					i++;
				}
			}

			if (hasDirty) {
				GLFW.glfwPollEvents();
			} else {
				GLFW.glfwWaitEvents();
			}
		}

		GLFW.glfwTerminate();
	}

	final fun setRegisterWindow(window: Window) {
		mWindows.add(window);
	}

	public open abstract fun onStart(): Unit;
}