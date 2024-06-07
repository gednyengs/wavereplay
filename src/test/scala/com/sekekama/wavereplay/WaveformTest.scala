package com
package sekekama
package wavereplay
package test

import org.scalatest.flatspec.AnyFlatSpec
import wavereplay._

object WaveformTest {

    case class MyWaveform(start: Long, end: Long, step: Long) extends Waveform {
        override def GetStartTime(): Long = start
        override def GetEndTime(): Long = end
        override def GetTimeStep(): Long = step
        override def HasPath(path: String): Boolean = path == "top.CLK"
        override def GetValue(sig: Signal, tm: Long): Long = {
            sig.GetPath() match {
                case "top.CLK" => tm % 2
                case _ => 0
            }
        }
    }

}


class WaveformTest extends AnyFlatSpec {

    "Waveform" should "return start time" in {
        val w_0 = WaveformTest.MyWaveform(0, 1000, 1)
        val w_1 = WaveformTest.MyWaveform(100, 1000, 1)
        val w_2 = WaveformTest.MyWaveform(-20, 1000, 1)

        assert(w_0.GetStartTime() == 0)
        assert(w_1.GetStartTime() == 100)
        assert(w_2.GetStartTime() == -20)
    }

    "Waveform" should "return end time" in {
        val w_0 = WaveformTest.MyWaveform(0, 100, 1)
        val w_1 = WaveformTest.MyWaveform(0, 1000, 1)
        val w_2 = WaveformTest.MyWaveform(0, -20, 1)

        assert(w_0.GetEndTime() == 100)
        assert(w_1.GetEndTime() == 1000)
        assert(w_2.GetEndTime() == -20)
    }

    "Waveform" should "return time step" in {
        val w_0 = WaveformTest.MyWaveform(0, 1000, 1)
        val w_1 = WaveformTest.MyWaveform(0, 1000, 10)
        val w_2 = WaveformTest.MyWaveform(0, 1000, -2)

        assert(w_0.GetTimeStep() == 1)
        assert(w_1.GetTimeStep() == 10)
        assert(w_2.GetTimeStep() == -2)
    }

    "Waveform" should "return check signal presence" in {
        val w_0 = WaveformTest.MyWaveform(0, 1000, 1)

        assert(w_0.HasPath("top.CLK"))
        assert(!w_0.HasPath("top.RESETn"))
    }

    "Waveform" should "return correct signal value" in {
        val w_0 = WaveformTest.MyWaveform(0, 1000, 1)
        val clk = w_0("top.CLK")

        assert(w_0.GetValue(clk, 0) == 0)
        assert(w_0.GetValue(clk, 1) == 1)
        assert(w_0.GetValue(clk, 998) == 0)
        assert(w_0.GetValue(clk, 999) == 1)
        assert(w_0.GetValue(clk, 1000) == 0)
    }

}
