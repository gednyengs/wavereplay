package com
package sekekama
package wavereplay
package test

import org.scalatest.flatspec.AnyFlatSpec
import wavereplay._

class MTLNestedTest extends AnyFlatSpec {


    "Nested Always/Eventually" should "statisfy proposition" in {
        val wave_entries: Map[String, List[WaveEntry]] = Map(
            "top.A" -> List((0, 1)),
            "top.B" -> List((0, 1), (5, 0), (10, 1), (15, 0), (20, 1))
        )
        val wvfm = new CursoredWaveform(DictWaveform(0, 20, 1, wave_entries))
        val sA = wvfm("top.A")
        val sB = wvfm("top.B")

        val p = always within(0, 5) check((sA === 1) && (eventually within(0, 4) check(sB === 1)))

        assert(Replay(p, wvfm))
    }
}
