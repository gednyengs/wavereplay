package com
package sekekama
package wavereplay
package model

object posedge {
    def apply(s: Signal): Proposition = new Proposition {
        override def Evaluate(tm: Long, wvfm: CursoredWaveform): Boolean = {
            if (tm == wvfm.GetStartTime()) return false
            val prev_tm = tm - wvfm.GetTimeStep()
            return (wvfm.GetValue(s, prev_tm) < wvfm.GetValue(s, tm))
        }

        override def cursor: Option[String] = None
    }
}

object negedge {
    def apply(s: Signal): Proposition = new Proposition {
        override def Evaluate(tm: Long, wvfm: CursoredWaveform): Boolean = {
            if (tm == wvfm.GetStartTime()) return false
            val prev_tm = tm - wvfm.GetTimeStep()
            return (wvfm.GetValue(s, prev_tm) > wvfm.GetValue(s, tm))
        }

        override def cursor: Option[String] = None
    }
}

object change {
    def apply(s: Signal): Proposition = new Proposition {
        override def Evaluate(tm: Long, wvfm: CursoredWaveform): Boolean = {
            if (tm == wvfm.GetStartTime()) return false
            val prev_tm = tm - wvfm.GetTimeStep()
            return (wvfm.GetValue(s, prev_tm) != wvfm.GetValue(s, tm))
        }

        override def cursor: Option[String] = None
    }
}
