package com.deavidig.Reactor.content

public class Settings() {
	companion object {
		public const val SYSTEM_MODE_THEME_DARK = 0x0001;
		public const val SYSTEM_MODE_THEME_LIGHT = 0x0002;
	}

	private var systemMode: Int = SYSTEM_MODE_THEME_LIGHT

	public fun setSystemMode(systemMode: Int) {
		this.systemMode = systemMode
	}

	public fun getSystemMode(): Int = this.systemMode
}