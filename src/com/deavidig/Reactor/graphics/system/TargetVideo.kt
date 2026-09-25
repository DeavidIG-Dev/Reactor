package com.deavidig.Reactor.graphics.system

interface TargetVideo {
	fun getWidth(): Int

	fun getHeight(): Int

	fun getRedBit(): Int

	fun getGreenBit(): Int

	fun getBlueBit(): Int

	fun getRateRefresh(): Int
}
