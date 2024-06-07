package com
package sekekama
package wavereplay
package model

/**
 * Represents a signal in a waveform
 * @param path The hierarchical path of the signal in the waveform namespace
 * @param wvfm The waveform containing the signal
 */
class Signal private(val path: String, val wvfm: Waveform) extends Expr {

    /**
     * Get the signal path
     * @return The path of the signal
     */
    def GetPath(): String = path

    /**
     * Get the associated waveform
     * @return The associated waveform
     */
    def GetWaveform(): Waveform = wvfm

    /**
     * Get signal value at a given time
     * @param tm The time at which to compute signal value
     * @throws RuntimeException
     */
    def GetValue(tm: Long): Long = wvfm.GetValue(this, tm)

    /**
     * Alias for GetValue(tm: Long)
     */
    def At(tm: Long): Long = GetValue(tm)

    /**
     * Get signal value at a given cursor
     * @param cursor The time cursor at which to get the signal value
     * @param w The implicit cursored waveform
     */
    def At(cursor: String): Long = {
        GetWaveform() match {
            case cw: CursoredWaveform => cw.GetValue(this, cursor)
            case wv: Waveform => throw new RuntimeException(s"Waveform attached to signal $path is not cursored")
            case _ => throw new RuntimeException(s"Cannot get value of signal $path at time cursor \" $cursor \"")
        }
    }

    /**
     * @see Expr::GetValue
     */
    override def GetValue(tm: Long, wvfm: Waveform): Long = {
        wvfm.GetValue(this, tm)
    }

    /**
     * Printable format
     */
    override def toString: String = "Signal(" + path + ", " + wvfm + ")"
}

/**
 * Companion object for class Signal
 */
object Signal {
    /**
     * Create a signal
     * @param path The hierarchical path of the signal in the waveform namespace
     * @param wvfm The waveform containing the signal
     * @return The signal instance if path exists in the waveform
     * @throws RuntimeException
     */
    def apply(path: String, wvfm: Waveform): Signal = {
        val sig = new Signal(path, wvfm)
        if (wvfm.HasPath(path)) return sig
        else {
            throw new RuntimeException("Signal with path " + path + " does not exist in the waveform")
        }
    }

    /**
     * Allow pattern matching on Signal objects
     */
    def unapply(sig: Signal): Option[(String, Waveform)] = Some((sig.path, sig.wvfm))
}
