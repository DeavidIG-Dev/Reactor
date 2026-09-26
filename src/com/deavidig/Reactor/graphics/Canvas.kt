package com.deavidig.Reactor.graphics

import com.deavidig.Reactor.Reactor
import org.lwjgl.nanovg.NVGColor
import org.lwjgl.nanovg.NanoVG
import org.lwjgl.nanovg.NanoVGGL3
import org.lwjgl.system.MemoryUtil

public class Canvas {
	private val mNanoVectorialGraphicColor: NVGColor = NVGColor.create();
	private val mVectorialGraphics: Long =
		NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS or NanoVGGL3.NVG_STENCIL_STROKES);

	init {
		if (this.mVectorialGraphics == MemoryUtil.NULL) {
			Reactor.setReactorError(
				Reactor.ReactorErrorType.FailedInitializeValue,
				"Cannot initialize the VectorialGraphics"
			)
		}
	}

	private inline fun create(callback: Canvas.() -> Unit, end: Canvas.() -> Unit): Unit {
		NanoVG.nvgSave(this.mVectorialGraphics)

		NanoVG.nvgBeginPath(this.mVectorialGraphics)

		callback()

		NanoVG.nvgClosePath(this.mVectorialGraphics)

		end()

		NanoVG.nvgRestore(this.mVectorialGraphics)
	}

	public fun createLine(startX: Float, startY: Float, endX: Float, endY: Float, color: Color): Unit {
		create(
			callback = {
				NanoVG.nvgMoveTo(this.mVectorialGraphics, startX, startY)
				NanoVG.nvgLineTo(this.mVectorialGraphics, endX, endY)
			},
			end = {
				NanoVG.nvgRGBA(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), this.mNanoVectorialGraphicColor)
				NanoVG.nvgStrokeColor(this.mVectorialGraphics, this.mNanoVectorialGraphicColor)
				NanoVG.nvgStroke(this.mVectorialGraphics)
			}
		)
	}

	public fun createSquare(x: Float, y: Float, width: Float, height: Float, fill: Color, stroke: Color = Color.TRANSPARENT): Unit {
		create(
			callback = {
				NanoVG.nvgMoveTo(this.mVectorialGraphics, x, y)

				NanoVG.nvgLineTo(this.mVectorialGraphics, x + width, y) // L1
				NanoVG.nvgLineTo(this.mVectorialGraphics, x + width, y + height) // L2
				NanoVG.nvgLineTo(this.mVectorialGraphics, x, y + height) // L3
				NanoVG.nvgLineTo(this.mVectorialGraphics, x, y) // L4
			},
			end = {
				NanoVG.nvgRGBA(fill.getRed(), fill.getGreen(), fill.getBlue(), fill.getAlpha(), this.mNanoVectorialGraphicColor)
				NanoVG.nvgFillColor(this.mVectorialGraphics, this.mNanoVectorialGraphicColor)
				NanoVG.nvgFill(this.mVectorialGraphics)
			}
		)
	}

	public fun createTriangula(x: Float, y: Float, width: Float, height: Float, color: Color): Unit {
		create(
			callback = {
				NanoVG.nvgMoveTo(this.mVectorialGraphics, x, y)

				NanoVG.nvgLineTo(this.mVectorialGraphics, x - width, y) // L1
				NanoVG.nvgLineTo(this.mVectorialGraphics, x - width, y + height) // L2
				NanoVG.nvgLineTo(this.mVectorialGraphics, x, y) // L3
			},
			end = {
				NanoVG.nvgRGBA(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), this.mNanoVectorialGraphicColor)
				NanoVG.nvgFillColor(this.mVectorialGraphics, this.mNanoVectorialGraphicColor)
				NanoVG.nvgFill(this.mVectorialGraphics)
			}
		)
	}
}