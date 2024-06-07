package com
package sekekama
package wavereplay
package model

/**
 * abstract base class for all expressions
 */
abstract class Expr {

    /***************************************************************************
     * Abstract Interface
     **************************************************************************/

    /**
     * Get the value of the expression
     * @param tm The time in the waveform at which to potentially compute value
     * @param wvfm The waveform to potentially compute the value from
     */
    def GetValue(tm: Long, wvfm: Waveform): Long

    /***************************************************************************
     * Concrete Interface
     **************************************************************************/

    /**
     * Add two expressions
     * @param other The other expression to add to the current expression
     */
    def +(other: Expr): Expr = AnonymousExpr(this, other, _+_)

    /**
     * Subtract two expressions
     * @param other The other expression to subtract from the current expression
     */
    def -(other: Expr): Expr = AnonymousExpr(this, other, _-_)

    /**
     * Subtract two expressions
     * @param other The other expression to multiply with the current expression
     */
    def *(other: Expr): Expr = AnonymousExpr(this, other, _*_)

    /**
     * Bitwise AND
     * @param other The other expression
     */
    def &(other: Expr): Expr = AnonymousExpr(this, other, _&_)

    /**
     * Bitwise OR
     * @param other The other expression
     */
    def |(other: Expr): Expr = AnonymousExpr(this, other, _|_)

    /**
     * Bitwise NOT
     */
    def unary_~ : Expr = AnonymousUnaryExpr(this, ~_)

    /**
     * Left Shift
     * @param other The other expression
     */
    def >>(other: Expr): Expr = AnonymousExpr(this, other, _>>_)

    /**
     * Right Shift
     * @param other The other expression
     */
    def <<(other: Expr): Expr = AnonymousExpr(this, other, _>>_)

    /**
     * Less-than Comparison
     * @param other The other expression
     */
    def <(other: Expr): Proposition = {
        val evaluator = (tm: Long, wvfm: CursoredWaveform) => {
            this.GetValue(tm, wvfm) < other.GetValue(tm, wvfm)
        }
        AnonymousProp(evaluator)
    }

    /**
     * Greater-than Comparison
     * @param other The other expression
     */
    def >(other: Expr): Proposition = {
        val evaluator = (tm: Long, wvfm: CursoredWaveform) => {
            this.GetValue(tm, wvfm) > other.GetValue(tm, wvfm)
        }
        AnonymousProp(evaluator)
    }

    /**
     * Equal-to Comparison
     * @param other The other expression
     */
    def ===(other: Expr): Proposition = {
        val evaluator = (tm: Long, wvfm: CursoredWaveform) => {
            this.GetValue(tm, wvfm) == other.GetValue(tm, wvfm)
        }
        AnonymousProp(evaluator)
    }

    /**
     * Less-than-or-equal-to Comparison
     * @param other The other expression
     */
    def <=(other: Expr): Proposition = {
        val evaluator = (tm: Long, wvfm: CursoredWaveform) => {
            this.GetValue(tm, wvfm) <= other.GetValue(tm, wvfm)
        }
        AnonymousProp(evaluator)
    }

    /**
     * Greater-than-or-equal-to Comparison
     * @param other The other expression
     */
    def >=(other: Expr): Proposition = {
        val evaluator = (tm: Long, wvfm: CursoredWaveform) => {
            this.GetValue(tm, wvfm) >= other.GetValue(tm, wvfm)
        }
        AnonymousProp(evaluator)
    }

    /**
     * Not-equal-to Comparison
     * @param other The other expression
     */
    def !=(other: Expr): Proposition = {
        val evaluator = (tm: Long, wvfm: CursoredWaveform) => {
            this.GetValue(tm, wvfm) != other.GetValue(tm, wvfm)
        }
        AnonymousProp(evaluator)
    }
}

/**
 * Literal number expression
 * @param n The literal value of the number
 */
case class NumExpr(n: Long) extends Expr {
    /**
     * @see Expr::GetValue
     */
    override def GetValue(tm: Long, wvfm: Waveform): Long = n
}

/**
 * Helper class to create an anonymous binary expression
 * @param lhs The expression on the left-hand side
 * @param rhs The expression on the right-hand side
 * @param reductor The reduction function
 */
case class AnonymousExpr(lhs: Expr, rhs: Expr, reductor: (Long, Long) => Long) extends Expr {
    override def GetValue(tm: Long, wvfm: Waveform): Long = {
        reductor(lhs.GetValue(tm, wvfm), rhs.GetValue(tm, wvfm))
    }
}

/**
 * Helper class to create an anonymous unary expression
 * @param e The original expression
 * @param reductor The reduction function
 */
case class AnonymousUnaryExpr(e: Expr, reductor: (Long) => Long) extends Expr {
    override def GetValue(tm: Long, wvfm: Waveform): Long = {
        reductor(e.GetValue(tm, wvfm))
    }
}
