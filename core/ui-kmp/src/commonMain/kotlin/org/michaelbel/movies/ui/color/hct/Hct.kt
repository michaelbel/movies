package org.michaelbel.movies.ui.color.hct

import org.michaelbel.movies.ui.color.utils.ColorUtils

class Hct private constructor(
    argb: Int
) {
    var hue = 0.0
    var chroma = 0.0
    var tone = 0.0
    var argb = 0

    init {
        setInternalState(argb)
    }

    fun toInt(): Int {
        return argb
    }

    fun setHue(newHue: Double) {
        setInternalState(HctSolver.solveToInt(newHue, chroma, tone))
    }

    fun setChroma(newChroma: Double) {
        setInternalState(HctSolver.solveToInt(hue, newChroma, tone))
    }

    fun setTone(newTone: Double) {
        setInternalState(HctSolver.solveToInt(hue, chroma, newTone))
    }

    fun inViewingConditions(vc: ViewingConditions): Hct {
        val cam16: Cam16 = Cam16.fromInt(toInt())
        val viewedInVc = cam16.xyzInViewingConditions(vc, null)
        val recastInVc: Cam16 = Cam16.fromXyzInViewingConditions(
            viewedInVc[0], viewedInVc[1], viewedInVc[2], ViewingConditions.DEFAULT
        )
        return from(recastInVc.hue, recastInVc.chroma, ColorUtils.lstarFromY(viewedInVc[1]))
    }

    private fun setInternalState(argb: Int) {
        this.argb = argb
        val cam: Cam16 = Cam16.fromInt(argb)
        hue = cam.hue
        chroma = cam.chroma
        tone = ColorUtils.lstarFromArgb(argb)
    }

    companion object {
        fun from(hue: Double, chroma: Double, tone: Double): Hct {
            val argb = HctSolver.solveToInt(hue, chroma, tone)
            return Hct(argb)
        }

        fun fromInt(argb: Int): Hct {
            return Hct(argb)
        }
    }
}