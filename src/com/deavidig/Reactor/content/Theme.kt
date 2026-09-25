package com.deavidig.Reactor.content

public interface Theme {
	fun getContext(): Context

	fun getThemeColor(): ThemeColor = if (getContext().getSettings().getSystemMode() == Settings.SYSTEM_MODE_THEME_DARK) getDarkThemeColor() else getLightThemeColor()

	fun getDarkThemeColor(): ThemeColor
	fun getLightThemeColor(): ThemeColor
}