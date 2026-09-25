package com.deavidig.Reactor.content

public interface Context {
	fun setTheme(theme: Theme): Unit

	fun getTheme(): Theme

	fun getResources(): Resources

	fun getSettings(): Settings
}
