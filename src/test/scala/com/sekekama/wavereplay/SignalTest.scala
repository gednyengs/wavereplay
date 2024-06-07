package com
package sekekama
package wavereplay
package test

import org.scalatest.flatspec.AnyFlatSpec
import wavereplay._

object SignalTest {

    case class MyWaveform(start: Long, end: Long, step: Long) extends Waveform {
        override def GetStartTime(): Long = start
        override def GetEndTime(): Long = end
        override def GetTimeStep(): Long = step
        override def HasPath(path: String): Boolean = path == "top.CLK"
        override def GetValue(sig: Signal, tm: Long): Long = {
            if ((tm < start) || (tm > end)) throw new RuntimeException("wrong time")
            sig.GetPath() match {
                case "top.CLK" => tm % 2
                case _ => 0
            }
        }
    }
}

class SignalTest extends AnyFlatSpec {

    "Signal" should "throw RuntimeException" in {
        val w_0 = SignalTest.MyWaveform(0, 1000, 1)

        assertThrows[RuntimeException] {
            val sig = w_0("top.RESETn")
        }

        assertThrows[RuntimeException] {
            val sig = Signal("top.RESETn", w_0)
        }
    }

    "Signal" should "exist if constructed with correct path" in {
        val w_0 = SignalTest.MyWaveform(0, 1000, 1)
        val sig_0 = w_0("top.CLK")
        val sig_1 = Signal("top.CLK", w_0)

        assert(sig_0.GetPath() == "top.CLK")
        assert(sig_1.GetPath() == "top.CLK")
    }

    "Signal" should "return correct value" in {
        val w_0 = SignalTest.MyWaveform(0, 1000, 1)
        val sig = w_0("top.CLK")

        assert(sig.GetValue(0) == 0)
        assert(sig.GetValue(1) == 1)
        assert(sig.GetValue(998) == 0)
        assert(sig.GetValue(999) == 1)
        assert(sig.GetValue(1000) == 0)
    }

    "Signal" should "throw exception if value checked at invalid times" in {
        val w_0 = SignalTest.MyWaveform(0, 1000, 1)
        val sig = w_0("top.CLK")

        assertThrows[RuntimeException] {
            val v = sig.GetValue(-1)
        }

    }

}
