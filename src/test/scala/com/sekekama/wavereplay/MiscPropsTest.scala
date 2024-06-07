package com
package sekekama
package wavereplay
package test

import org.scalatest.flatspec.AnyFlatSpec
import wavereplay._

class MiscPropsTest extends AnyFlatSpec {

    "Posedge" should "satisfy proposition" in {
        val wave_entries: Map[String, List[WaveEntry]] = Map(
            "top.CLK" -> List((0, 0), (10, 1), (20, 0), (30, 1))
        )
        val wvfm = new CursoredWaveform(DictWaveform(0, 30, 10, wave_entries))
        val sig = wvfm("top.CLK")

        val p = eventually at "t0" check(posedge(sig)) exec {
            val v = sig.At("t0")
            println(s"[Posedge] value of sig at t0 = $v")
        }

        assert(Replay(p, wvfm))
    }

    "Posedge" should "satisfy simple proposition" in {
        val wave_entries: Map[String, List[WaveEntry]] = Map(
            "top.CLK" -> List((0, 0), (10, 1), (20, 0), (30, 1))
        )
        val wvfm = new CursoredWaveform(DictWaveform(0, 30, 10, wave_entries))
        val sig = wvfm("top.CLK")

        val p = posedge(sig) at "t0" exec {
            //val v = sig.At("t0")
            //println(s"[Posedge] value of sig at t0 = $v")
        }

        assert(Replay(p, wvfm))
    }

    "Negedge" should "satisfy proposition" in {
        val wave_entries: Map[String, List[WaveEntry]] = Map(
            "top.CLK" -> List((0, 0), (10, 1), (20, 0), (30, 1), (40, 0))
        )
        val wvfm = new CursoredWaveform(DictWaveform(0, 40, 10, wave_entries))
        val sig = wvfm("top.CLK")

        val p = eventually at "t0" within(0) check(negedge(sig)) exec {
            //val t0 = wvfm.GetCursorTime("t0")
            //println("[Negedge] code block ran at time " + t0)
        }

        assert(Replay(p, wvfm))
    }

    "Change" should "satisfy proposition" in {
        val wave_entries: Map[String, List[WaveEntry]] = Map(
            "top.CLK" -> List((0, 0), (10, 1), (20, 0), (30, 1), (40, 0))
        )
        val wvfm = new CursoredWaveform(DictWaveform(0, 40, 10, wave_entries))
        val sig = wvfm("top.CLK")

        val p = eventually at "t0" within(0) check(change(sig)) exec {
            //val t0 = wvfm.GetCursorTime("t0")
            //rintln("[Change] code block ran at time " + t0)
        }

        assert(Replay(p, wvfm))
    }

}
