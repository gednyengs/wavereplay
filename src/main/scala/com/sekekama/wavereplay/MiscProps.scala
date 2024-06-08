package com
package sekekama
package wavereplay
package model

/**
 * Proposition to find a positive edge transition on a signal
 *
 * A positive edge transition occurs at time 't' when the value of the signal at the
 * time stamp immediately preceeding 't' is less than that at time 't'
 */
object posedge {
    /**
     * Create a 'posedge' proposition
     * @param s The reference signal
     */
    def apply(s: Signal): Proposition = new Proposition {
        override def Evaluate(tm: Long, wvfm: CursoredWaveform): Boolean = {
            if (tm == wvfm.GetStartTime()) return false
            val prev_tm = tm - wvfm.GetTimeStep()
            return (wvfm.GetValue(s, prev_tm) < wvfm.GetValue(s, tm))
        }

        override def cursor: Option[String] = None
    }
}

/**
 * Proposition to find a negative edge transition on a signal
 *
 * A negative edge transition occurs at time 't' when the value of the signal at the
 * time stamp immediately preceeding 't' is greater than that at time 't'
 */
object negedge {
    /**
     * Create a 'negedge' proposition
     * @param s The reference signal
     */
    def apply(s: Signal): Proposition = new Proposition {
        override def Evaluate(tm: Long, wvfm: CursoredWaveform): Boolean = {
            if (tm == wvfm.GetStartTime()) return false
            val prev_tm = tm - wvfm.GetTimeStep()
            return (wvfm.GetValue(s, prev_tm) > wvfm.GetValue(s, tm))
        }

        override def cursor: Option[String] = None
    }
}

/**
 * Proposition to find any change on a signal
 *
 * A change occurs at time 't' when the value of the signal at the
 * time stamp immediately preceeding 't' is different from that at time 't'
 */
object change {
    /**
     * Create a 'change' proposition
     * @param s The reference signal
     */
    def apply(s: Signal): Proposition = new Proposition {
        override def Evaluate(tm: Long, wvfm: CursoredWaveform): Boolean = {
            if (tm == wvfm.GetStartTime()) return false
            val prev_tm = tm - wvfm.GetTimeStep()
            return (wvfm.GetValue(s, prev_tm) != wvfm.GetValue(s, tm))
        }

        override def cursor: Option[String] = None
    }
}
