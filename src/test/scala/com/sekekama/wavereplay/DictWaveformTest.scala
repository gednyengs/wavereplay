package com
package sekekama
package wavereplay
package test

import org.scalatest.flatspec.AnyFlatSpec
import wavereplay._

class DictWaveformTest extends AnyFlatSpec {

    "DictWaveform" should "return the right values" in {
        val wave_entries: Map[String, List[WaveEntry]] = Map(
            "top.CLK" -> List((0, 0), (10, 1), (20, 0), (30, 1))
        )
        val wvfm = DictWaveform(0, 30, 10, wave_entries)
        val sig = wvfm("top.CLK")

        assert(sig.GetValue(0) == 0)
        assert(sig.GetValue(9) == 0)
        assert(sig.GetValue(10) == 1)
        assert(sig.GetValue(19) == 1)
        assert(sig.GetValue(20) == 0)
        assert(sig.GetValue(29) == 0)
        assert(sig.GetValue(30) == 1)

        assertThrows[RuntimeException] {
            sig.GetValue(31)
        }

        assertThrows[RuntimeException] {
            val sig = wvfm("top.RESETn")
        }
    }

}
