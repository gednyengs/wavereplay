package com
package example

import com.sekekama.wavereplay._

object Main extends App {

    // Get waveform and signal references
    import WaveformData._


    // Define enqueue condition
    val enq_cond = posedge(clk) && (wr_valid === 1) && (wr_ready === 1)

    // Define proposition
    val prop = always at "t0" within(40) check(!enq_cond) exec {
        println("Failing time = " + wvfm.GetCursorTime("t0"))
    }

    // Get result
    ReplayWithin(0, 180, prop, wvfm)

}
