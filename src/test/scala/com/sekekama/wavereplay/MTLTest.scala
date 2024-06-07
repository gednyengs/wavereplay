package com
package sekekama
package wavereplay
package test

import org.scalatest.flatspec.AnyFlatSpec
import wavereplay._

class MTLTest extends AnyFlatSpec {

    "Eventually" should "satisfy simple proposition across the entire waveform" in {
        val wave_entries: Map[String, List[WaveEntry]] = Map(
            "top.CLK" -> List((0, 0), (10, 1), (20, 0), (30, 1))
        )
        val wvfm = new CursoredWaveform(DictWaveform(0, 30, 10, wave_entries))
        val sig = wvfm("top.CLK")

        val p = eventually at "t0" check(posedge(sig)) exec {
            //val t0 = wvfm.GetCursorTime("t0")
            //println("[Eventually] code block ran at time " + t0)
        }

        assert(Replay(p, wvfm))
    }

    "Eventually" should "satisfy simple proposition within a window" in {
        val wave_entries: Map[String, List[WaveEntry]] = Map(
            "top.CLK" -> List((0, 0), (10, 1), (20, 0), (30, 1))
        )
        val wvfm = new CursoredWaveform(DictWaveform(0, 30, 10, wave_entries))
        val sig = wvfm("top.CLK")

        val p = eventually at "t0" within(0, 20) check(posedge(sig)) exec {
            //val t0 = wvfm.GetCursorTime("t0")
            //println("[Eventually] code block ran at time " + t0)
        }

        assert(Replay(p, wvfm))
    }

    "Always" should "satisfy simple proposition across the entire waveform" in {
        val wave_entries: Map[String, List[WaveEntry]] = Map(
            "top.CLK" -> List((0, 1))
        )
        val wvfm = new CursoredWaveform(DictWaveform(0, 30, 10, wave_entries))
        val sig = wvfm("top.CLK")

        val p = always at "t0" check(sig === 1) exec {
            //val t0 = wvfm.GetCursorTime("t0")
            //println("[Always] code block ran at time " + t0)
        }

        assert(Replay(p, wvfm))
    }

    "Always" should "satisfy simple proposition within a window" in {
        val wave_entries: Map[String, List[WaveEntry]] = Map(
            "top.CLK" -> List((0, 1))
        )
        val wvfm = new CursoredWaveform(DictWaveform(0, 30, 10, wave_entries))
        val sig = wvfm("top.CLK")

        val p = always at "t0" within(0, 20) check(sig === 1) exec {
            //val t0 = wvfm.GetCursorTime("t0")
            //println("[Always] code block ran at time " + t0)
        }

        assert(Replay(p, wvfm))
    }

    "Next" should "satisfy simple proposition across the entire waveform" in {
        val wave_entries: Map[String, List[WaveEntry]] = Map(
            "top.CLK" -> List((0, 0), (10, 1), (20, 0), (30, 1))
        )
        val wvfm = new CursoredWaveform(DictWaveform(0, 30, 10, wave_entries))
        val sig = wvfm("top.CLK")

        val p = next at "t0" check(sig === 1) exec {
            //val t0 = wvfm.GetCursorTime("t0")
            //println("[Next] code block ran at time " + t0)
        }

        assert(Replay(p, wvfm))
    }

    "Next" should "satisfy simple proposition within a window" in {
        val wave_entries: Map[String, List[WaveEntry]] = Map(
            "top.CLK" -> List((0, 0), (10, 1), (20, 1), (30, 1), (40, 0))
        )
        val wvfm = new CursoredWaveform(DictWaveform(0, 40, 10, wave_entries))
        val sig = wvfm("top.CLK")

        val p = next at "t0" within(0, 20) check(sig === 1) exec {
            //val t0 = wvfm.GetCursorTime("t0")
            //println("[Next] code block ran at time " + t0)
        }

        assert(Replay(p, wvfm))
    }

    "till" should "satisfy simple proposition across the entire waveform" in {
        val wave_entries: Map[String, List[WaveEntry]] = Map(
            "top.A" -> List((0, 1), (150, 0)),
            "top.B" -> List((0,0), (100, 1), (200, 0))
        )
        val wvfm = new CursoredWaveform(DictWaveform(0, 300, 10, wave_entries))
        val sA = wvfm("top.A")
        val sB = wvfm("top.B")

        val p = till(sB === 1) at "t0" check(sA === 1) exec {
            //val t0 = wvfm.GetCursorTime("t0")
            //println("[Till] code block ran at time " + t0)
        }

        assert(Replay(p, wvfm))
    }

    "till" should "satisfy simple proposition within a window" in {
        val wave_entries: Map[String, List[WaveEntry]] = Map(
            "top.A" -> List((0, 1), (150, 0)),
            "top.B" -> List((0,0), (100, 1), (200, 0))
        )
        val wvfm = new CursoredWaveform(DictWaveform(0, 300, 10, wave_entries))
        val sA = wvfm("top.A")
        val sB = wvfm("top.B")

        val p = till(sB === 1) at "t0" within(0, 30) check(sA === 1) exec {
            //val t0 = wvfm.GetCursorTime("t0")
            //println("[Till] code block ran at time " + t0)
        }

        assert(Replay(p, wvfm))
    }

    "Conjunction" should "statisfy proposition" in {
        val wave_entries: Map[String, List[WaveEntry]] = Map(
            "top.A" -> List((0, 1)),
            "top.B" -> List((0, 1), (5, 0), (10, 1), (15, 0), (20, 1))
        )
        val wvfm = new CursoredWaveform(DictWaveform(0, 20, 1, wave_entries))
        val sA = wvfm("top.A")
        val sB = wvfm("top.B")

        val p = ((sA === 1) && (eventually within(0, 4) check(sB === 1)))

        assert(Replay(p, wvfm))
    }
}
