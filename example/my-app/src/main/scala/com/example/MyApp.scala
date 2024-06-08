package com
package example

import com.sekekama.wavereplay._

object Main extends App {

    // Create waveform and wrap it with cursors
    val wvfm_data : Map[String, List[WaveEntry]] = Map(
            "SignalA" -> List((0, 10), (10, 25), (20, -50), (30, -100), (40, 500))
        )
    val wvfm = CursoredWaveform(DictWaveform(0, 40, 10, wvfm_data))

    // Create references for signals of interest
    val sigA = wvfm("SignalA")

    // Create proposition to find all negative values of SignalA and print them
    val neg_vals_prop = (sigA < 0) at "t" exec {
        println("Captured value = " + sigA.At("t"))
    }

    // Replay the waveform with the specified proposition
    Replay(neg_vals_prop, wvfm)
}
