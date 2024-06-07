package com
package sekekama
package wavereplay
package model


/**
 * Signal data at a particular time in a waveform
 * @param tm Time instance
 * @param data Signal value
 */
case class WaveEntry(tm: Long, data: Long)

/**
 * Dictionary-Based waveform
 * @param start Waveform start time
 * @param end Waveform end time
 * @param step Waveform time increment
 */
case class DictWaveform(start: Long,
                        end: Long,
                        step: Long,
                        data: Map[String, List[WaveEntry]]) extends Waveform {
    /**
     * @see Waveform::GetStartTime()
     */
    override def GetStartTime(): Long = start

    /**
     * @see Waveform::GetEndTime()
     */
    override def GetEndTime(): Long = end

    /**
     * @see Waveform::HasPath()
     */
    override def HasPath(path: String): Boolean = {
        data contains path
    }

    /**
     * @see Waveform::GetTimeStep()
     */
    override def GetTimeStep(): Long = step

    /**
     * @see Waveform::GetValue()
     */
    override def GetValue(sig: Signal, tm: Long): Long = {
        if ((tm < start) || (tm > end)) throw new RuntimeException("invalid waveform time (" + tm + ")")

        val weOpt = data.get(sig.GetPath())
        if (!weOpt.isDefined) {
            throw new RuntimeException("invalid signal (" + sig.GetPath() + ")")
        }
        val entry_list: List[WaveEntry] = weOpt.get

        if (entry_list.isEmpty) {
            throw new RuntimeException("no data found for signal (" + sig.GetPath() + ")")
        }

        var prev = entry_list.head
        for (WaveEntry(t, d) <- entry_list) {
            if (t == tm) return d
            else if(t > tm) return prev.data
            prev = WaveEntry(t, d)
        }
        return prev.data
    }

}
