package com.deavidig.Reactor.content

import java.awt.Color

interface ThemeColor {

	// Primary
	fun getPrimaryColor(): Color
	fun getOnPrimaryColor(): Color
	fun getPrimaryContainerColor(): Color
	fun getOnPrimaryContainerColor(): Color

	// Secondary
	fun getSecondaryColor(): Color
	fun getOnSecondaryColor(): Color
	fun getSecondaryContainerColor(): Color
	fun getOnSecondaryContainerColor(): Color

	// Tertiary
	fun getTertiaryColor(): Color
	fun getOnTertiaryColor(): Color
	fun getTertiaryContainerColor(): Color
	fun getOnTertiaryContainerColor(): Color

	// Error
	fun getErrorColor(): Color
	fun getOnErrorColor(): Color
	fun getErrorContainerColor(): Color
	fun getOnErrorContainerColor(): Color

	// Background
	fun getBackgroundColor(): Color
	fun getOnBackgroundColor(): Color

	// Surface
	fun getSurfaceColor(): Color
	fun getOnSurfaceColor(): Color
	fun getSurfaceVariantColor(): Color
	fun getOnSurfaceVariantColor(): Color

	// Surface containers
	fun getSurfaceDimColor(): Color
	fun getSurfaceBrightColor(): Color
	fun getSurfaceContainerLowestColor(): Color
	fun getSurfaceContainerLowColor(): Color
	fun getSurfaceContainerColor(): Color
	fun getSurfaceContainerHighColor(): Color
	fun getSurfaceContainerHighestColor(): Color

	// Outline
	fun getOutlineColor(): Color
	fun getOutlineVariantColor(): Color

	// Inverse
	fun getInverseSurfaceColor(): Color
	fun getInverseOnSurfaceColor(): Color
	fun getInversePrimaryColor(): Color

	// Text
	fun getTextColorPrimary(): Color
	fun getTextColorSecondary(): Color
	fun getTextColorTertiary(): Color
	fun getTextColorPrimaryInverse(): Color
	fun getTextColorSecondaryInverse(): Color

	// Scrim
	fun getScrimColor(): Color
}