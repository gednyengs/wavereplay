package com
package sekekama
package wavereplay
package model

/**
 * MTL "Globally/Always" Operator
 * @param prop The proposition to check
 * @param start The time delta from where to start
 * @param end The time delta from where to end
 * @param cursor The optional associated time cursor
 * @note negative values for start and end map to Infinity
 */
case class GOp(prop: Proposition, start: Long = 0, end: Long = -1, cursor: Option[String] = None) extends Proposition {
    /**
     * @see Proposition::Evaluate
     */
    override def Evaluate(time: Long, wvfm: CursoredWaveform): Boolean = {

        val w_end = wvfm.GetEndTime()
        val p_beg = if (start >= 0) time + start else wvfm.GetEndTime()
        val p_end = if (end >= 0) time + end else wvfm.GetEndTime()

        // Checks
        if (p_beg > p_end) return false // moving backwards!
        if (p_end > w_end) return false // out of time!

        // Proposition logic
        val w_step = wvfm.GetTimeStep()
        var p_cur = p_beg
        while (p_cur <= p_end) {
            if (!prop.CursorEval(p_cur, wvfm)) {
                return false
            }
            p_cur = p_cur + w_step
        }
        return true
    }
}

/**
 * MTL "Finally/Eventually" Operator
 * @param prop The proposition to check
 * @param start The time delta from where to start
 * @param end The time delta from where to end
 * @param cursor The optional associated time cursor
 * @note negative values for start and end map to Infinity
 */
case class FOp(prop: Proposition, start: Long = 0, end: Long = -1, cursor: Option[String] = None) extends Proposition {
    /**
     * @see Proposition::Evaluate
     */
    override def Evaluate(time: Long, wvfm: CursoredWaveform): Boolean = {
        val w_end = wvfm.GetEndTime()
        val p_beg = if (start >= 0) (time + start) else w_end
        val p_end = if (end >= 0) (time + end) else w_end

        // Checks
        if (p_beg > p_end) return false // moving backwards!
        if (p_beg > w_end) return false // out of time!

        // Proposition logic
        val w_step = wvfm.GetTimeStep()
        var p_cur = p_beg
        while (p_cur <= p_end) {
            if (prop.CursorEval(p_cur, wvfm)) {
                return true
            }
            p_cur = p_cur + w_step
        }
        return false
    }
}

/**
 * MTL "Next" Operator
 * @param prop The proposition to check
 * @param start The time delta from where to start
 * @param end The time delta from where to end
 * @param cursor The optional associated time cursor
 * @note negative values for start and end map to Infinity
 */
case class XOp(prop: Proposition, start: Long = 0, end: Long = -1, cursor: Option[String] = None) extends Proposition {
    /**
     * @see Proposition::Evaluate
     */
    override def Evaluate(time: Long, wvfm: CursoredWaveform): Boolean = {
        val w_end = wvfm.GetEndTime()
        val p_beg = if (start >= 0) (time + start) else w_end
        val p_end = if (end >= 0) (time + end) else 0

        // Checks
        val w_step = wvfm.GetTimeStep()
        if (p_beg > p_end) return false // moving backwards!
        if ((p_end + w_step) > w_end) return false // out of time!

        // Proposition logic
        var p_cur = p_beg
        while (p_cur <= p_end) {
            if (!prop.CursorEval(p_cur + w_step, wvfm)) {
                return false
            }
            p_cur = p_cur + w_step
        }
        return true
    }
}

/**
 * MTL "Until" Operator
 * @param first_prop The proposition to to hold until
 * @param second_prop The guard proposition
 * @param start The time delta from where to start
 * @param end The time delta from where to end
 * @param cursor The optional associated time cursor
 * @note negative values for start and end map to Infinity
 */
case class UOp(first_prop: Proposition, second_prop: Proposition, start: Long, end: Long, cursor: Option[String] = None) extends Proposition {
    /**
     * @see Proposition::Evaluate
     */
    override def Evaluate(time: Long, wvfm: CursoredWaveform): Boolean = {
        val w_end = wvfm.GetEndTime()
        val p_beg = if (start >= 0) (time + start) else w_end
        val p_end = if (end >= 0) (time + end) else w_end

        // Checks
        val w_step = wvfm.GetTimeStep()
        if (p_beg > p_end) return false // moving backwards!
        if (p_beg > w_end) return false // out of time!

        // Proposition logic
        var t_sec: Long = -1
        var found_t_sec = false
        var p_cur = p_beg
        while ((p_cur <= p_end) && !found_t_sec) {
            if (second_prop.CursorEval(p_cur, wvfm)) {
                t_sec = p_cur
                found_t_sec = true
            }
            p_cur = p_cur + w_step
        }

        if(!found_t_sec) return false
        p_cur = time
        while (p_cur <= t_sec) {
            if (!first_prop.CursorEval(p_cur, wvfm)) {
                return false
            }
            p_cur = p_cur + w_step
        }
        return true
    }
}
