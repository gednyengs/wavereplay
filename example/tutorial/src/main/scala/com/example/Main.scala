package com
package example

import com.sekekama.wavereplay._

object Main extends App {

    // Get waveform and signal references
    import WaveformData._

    // Get signal values at different time stamps
    println(s"WR_DATA @ 30ns = " + wr_data.At(30))
    println(s"RD_DATA @ 160ns = " + rd_data.At(160))
}
