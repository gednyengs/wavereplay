package com
package sekekama
package wavereplay
package model

import scala.collection.mutable

/**
 * A waveform with cursors
 * @param wvfm The enclosed non-cursored waveform
 */
class CursoredWaveform(val wvfm: Waveform) extends Waveform {
    /***************************************************************************
     * Waveform Interface
     **************************************************************************/
    /**
     * @see [[Waveform.GetStartTime]]
     */
    override def GetStartTime(): Long = wvfm.GetStartTime()

    /**
     * @see [[Waveform.GetEndTime]]
     */
    override def GetEndTime(): Long = wvfm.GetEndTime()

    /**
     * @see [[Waveform.GetTimeStep]]
     */
    override def GetTimeStep(): Long = wvfm.GetTimeStep()

    /**
     * @see [[Waveform.GetValue]]
     */
    override def GetValue(sig: Signal, tm: Long): Long = wvfm.GetValue(sig, tm)

    /**
     * @see [[Waveform.HasPath]]
     */
    override def HasPath(path: String): Boolean = wvfm.HasPath(path)

    /***************************************************************************
     * Cursor Manager Interface
     **************************************************************************/

     val cursor_map = mutable.Map[String, Long]()

     /**
      * Determines whether the waveform has the specified time cursor
      * @param cur The time cursor
      * @return true if the waveform has the time cursor, false otherwise
      */
     def HasCursor(cur: String): Boolean = {
         cursor_map.contains(cur)
     }

     /**
      * Optionally get the timestamp associated with the specified cursor
      * @param cur The time cursor
      * @return Some(time) if cursor present, None otherwise
      */
     def GetCursorTime(cur: String): Option[Long] = {
         cursor_map.get(cur)
     }

     /**
      * Associate a cursor with the provided time
      * @param cur The time cursor
      * @param time The time to associate with the cursor
      */
     def SetCursor(cur: String, time: Long): Unit = {
         cursor_map.addOne((cur, time))
     }

     /**
      * Get signal value at the given cursor
      * @param sig The signal to get the value of
      * @param c The time cursor at which to retrieve signal value
      * @return Signal's value
      */
     @throws[RuntimeException]
     def GetValue(sig: Signal, c: String): Long = {
       val ctOpt = GetCursorTime(c)
       for (ct <- ctOpt) {
         return wvfm.GetValue(sig, ct)
       }
       throw new RuntimeException(s"No value for signal at cursor $c")
     }

     /**
      * Clear the cursor map
      */
     def ResetCursors(): Unit = {
         cursor_map.clear()
     }
}

/**
 * Companion object for CursoredWaveform class
 */
object CursoredWaveform {
    /**
     * Create a cursored waveform
     * @param wvfm The underlying waveform to enable cursors on
     */
    def apply(wvfm: Waveform): CursoredWaveform = new CursoredWaveform(wvfm)
}
