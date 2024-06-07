package com
package sekekama
package wavereplay
package model

/**
 * Abstract base class for all propositions
 */
abstract class Proposition {

    /***************************************************************************
     * Abstract Interface
     **************************************************************************/

    /**
     * Evaluate the proposition
     * @param time The time at which to evaluate the proposition
     * @param wvfm The waveform on which to evaluate the proposition
     * @return true if the proposition was satisfied, false otherwise
     */
    protected def Evaluate(time: Long, wvfm: CursoredWaveform): Boolean

    /** Cursor associated with the proposition */
    def cursor: Option[String]


    /***************************************************************************
     * Concrete Interface
     **************************************************************************/

     /** Code block for the proposition */
     var codeOpt: Option[() => Unit] = None

     /**
      * Set code block for proposition
      * @param code The code block to set
      */
     def exec(code: => Unit): Proposition = {
         codeOpt = Some(() => code)
         return this
     }

    /**
     * Evaluate proposition and possibly set time cursor
     * @param time The time at which to evaluate the proposition
     * @param wvfm The cursored waveform instance on which to evaluate the proposition
     * @return true if proposition was satisfied, false otherwise
     * @note if proposition is satisfied, attempts to set a cursor if available
     */
    def CursorEval(time: Long, wvfm: CursoredWaveform): Boolean  = {
     val verdict = Evaluate(time, wvfm)
     if (verdict) {
         for (cur <- cursor) {
             wvfm.SetCursor(cur, time)
         }
         for (code <- this.codeOpt) {
           implicit val w: CursoredWaveform = wvfm
           code()
         }
     }
     return verdict
    }

    /**
     * Conjunction connective
     * @param other The other proposition
     */
    def and(other: Proposition): Proposition = {
         val evaluator = (tm: Long, wvfm: CursoredWaveform) => {
             this.CursorEval(tm, wvfm) && other.CursorEval(tm, wvfm)
         }
         AnonymousProp(evaluator)
    }

    def &&(other: Proposition): Proposition = and(other)

    /**
     * Disjunction connective
     * @param other The other proposition
     */
    def or(other: Proposition): Proposition = {
         val evaluator = (tm: Long, wvfm: CursoredWaveform) => {
             this.CursorEval(tm, wvfm) || other.CursorEval(tm, wvfm)
         }
         AnonymousProp(evaluator)
    }

    def ||(other: Proposition): Proposition = or(other)

    /**
     * Negation connective
     */
    def unary_! : Proposition = {
         val evaluator = (tm: Long, wvfm: CursoredWaveform) => {
             !this.CursorEval(tm, wvfm)
         }
         AnonymousProp(evaluator)
    }

    /**
     * Create new proposition based on the current one with assignment of cursor
     * @param c The cursor to assign
     */
    def at(c: String): Proposition = AnonymousProp(Evaluate, Some(c))
}

/**
 * Helper class to create a concrete proposition
 * @param n_evaluator The new evaluator for the proposition
 * @param n_cursor The new cursor for the proposition
 */
case class AnonymousProp(n_evaluator: (Long, CursoredWaveform) => Boolean,
                         n_cursor: Option[String] = None) extends Proposition {
    override def Evaluate(tm: Long, wvfm: CursoredWaveform): Boolean = {
        n_evaluator(tm, wvfm)
    }

    override def cursor: Option[String] = n_cursor
}
