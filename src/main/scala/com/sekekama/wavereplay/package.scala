package com
package sekekama

import scala.language.implicitConversions

package object wavereplay {

    // Object aliases
    val always = wavereplay.builder.always
    val eventually = wavereplay.builder.eventually
    val next = wavereplay.builder.next
    val till = wavereplay.builder.till

    val posedge = wavereplay.model.posedge
    val negedge = wavereplay.model.negedge
    val change = wavereplay.model.change

    val NumExpr = wavereplay.model.NumExpr
    val DictWaveform = wavereplay.model.DictWaveform
    val WaveEntry = wavereplay.model.WaveEntry
    val Signal = wavereplay.model.Signal

    // Type aliases
    type Proposition = wavereplay.model.Proposition
    type Waveform = wavereplay.model.Waveform
    type CursoredWaveform = wavereplay.model.CursoredWaveform
    type Signal = wavereplay.model.Signal
    type Expr = wavereplay.model.Expr
    type NumExpr = wavereplay.model.NumExpr
    type DictWaveform = wavereplay.model.DictWaveform
    type WaveEntry = wavereplay.model.WaveEntry


    // Convert Strings to Option[String] implicitly
    implicit def strToOption(s: String): Option[String] = Some(s)

    // Convert integer literals to Expr implicitly
    implicit def intToExpr(i: Int): Expr = NumExpr(i.toLong)

    // Convert from a tuple to WaveEntry implicitly
    implicit def tupleToWaveEntry(t: (Int, Int)): WaveEntry = WaveEntry(t._1.toLong, t._2.toLong)

    /**
     * Replays the waveform with the given proposition within a time range
     * @param start The start time of the time range
     * @param end The end time of the time range
     * @param prop The proposition to check
     * @param wvfm The waveform to replay
     */
    def ReplayWithin(start: Long, end: Long, prop: Proposition, wvfm: CursoredWaveform): Boolean = {
        val t_inc = wvfm.GetTimeStep()

        wvfm.ResetCursors()
        var verdict = false
        var t_c = start
        while (t_c <= end) {
            try {
                if (prop.CursorEval(t_c, wvfm)) {
                    verdict = true
                }
            } catch {
                case _ : Throwable => ()
            } finally {
                t_c = t_c + t_inc
            }
            wvfm.ResetCursors()
        }
        return verdict
    }

    /**
     * Replays the waveform with the given proposition at all time steps
     * @param prop The proposition to check
     * @param wvfm The waveform to replay
     */
    def Replay(prop: Proposition, wvfm: CursoredWaveform): Boolean = {
        val t_s = wvfm.GetStartTime()
        val t_e = wvfm.GetEndTime()
        return ReplayWithin(t_s, t_e, prop, wvfm)
    }

    /**
     * Replays the waveform with the given proposition at a specific time
     * @param tm The time instance at which to replay the waveform
     * @param prop The proposition to check
     * @param wvfm The waveform to replay
     */
    def ReplayAt(tm: Long, prop: Proposition, wvfm: CursoredWaveform): Boolean = {
        return ReplayWithin(tm, tm, prop, wvfm)
    }
}
