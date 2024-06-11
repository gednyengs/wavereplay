package com
package sekekama
package wavereplay
package model

/**
 * A base class representing a waveform
 *
 * Classes that implement concrete waveform types should
 * inherit from this base class.
 */
abstract class Waveform {

    /***************************************************************************
     * Abstract Interface
     **************************************************************************/

    /**
     * Get the first start time in the waveform
     */
    def GetStartTime(): Long

    /**
     * Get the end time in the waveform
     */
    def GetEndTime(): Long

    /**
     * Get the time step value
     */
    def GetTimeStep(): Long

    /**
     * Get value of a signal at the given time from the waveform
     * @param sig The signal to retrieve the value of
     * @param tm The time at which to compute the signal's value
     * @return The value of the signal at the given time
     * @note Throws a runtime exception if the provided time "tm" is out of range
     */
    @throws[RuntimeException]
    def GetValue(sig: Signal, tm: Long): Long

    /**
     * Check whether the given signal path exists in the waveform
     * @param path The signal to check
     * @return True if the signal path exists in the waveform, false otherwise
     */
    def HasPath(path: String): Boolean

    /***************************************************************************
     * Concrete Interface
     **************************************************************************/

     /**
      * Get a signal reference from waveform
      * @param path The path of signal to return
      */
     def apply(path: String): Signal = Signal(path, this)
}
