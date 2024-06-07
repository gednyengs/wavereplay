package com
package sekekama
package wavereplay
package builder

import com.sekekama.wavereplay.model.{FOp, GOp, Proposition, XOp, UOp}

/**
 * Always/Globally Op Builder
 */
case class GOpBuilder(start: Long = 0, end: Long = -1, cursor: Option[String] = None) {
    def at(c: String): GOpBuilder = this.copy(cursor = Some(c))
    def within(e: Long): GOpBuilder = this.copy(start=0, end=e)
    def within(s: Long, e: Long): GOpBuilder = this.copy(start=s, end=e)
    def check(p: Proposition): GOp = GOp(p, start, end, cursor)
}

object always {
    def at(c: String): GOpBuilder = GOpBuilder(cursor=Some(c))
    def within(e: Long): GOpBuilder = GOpBuilder(start=0, end=e)
    def within(s: Long, e: Long): GOpBuilder = GOpBuilder(start=s, end=e)
    def check(p: Proposition): GOp = GOpBuilder().check(p)
}

/**
 * Finally/Eventually Op Builder
 */
case class FOpBuilder(start: Long = 0, end: Long = -1, cursor: Option[String] = None) {
    def at(c: String): FOpBuilder = this.copy(cursor = Some(c))
    def within(e: Long): FOpBuilder = this.copy(start=0, end=e)
    def within(s: Long, e: Long): FOpBuilder = this.copy(start=s, end=e)
    def check(p: Proposition): FOp = FOp(p, start, end, cursor)
}

object eventually {
    def at(c: String): FOpBuilder = FOpBuilder(cursor=Some(c))
    def within(e: Long): FOpBuilder = FOpBuilder(start=0, end=e)
    def within(s: Long, e: Long): FOpBuilder = FOpBuilder(start=s, end=e)
    def check(p: Proposition): FOp = FOpBuilder().check(p)
}

/**
 * Next Op Builder
 */
case class XOpBuilder(start: Long = 0, end: Long = -1, cursor: Option[String] = None) {
    def at(c: String): XOpBuilder = this.copy(cursor = Some(c))
    def within(e: Long): XOpBuilder = this.copy(start=0, end=e)
    def within(s: Long, e: Long): XOpBuilder = this.copy(start=s, end=e)
    def check(p: Proposition): XOp = XOp(p, start, end, cursor)
}

object next {
    def at(c: String): XOpBuilder = XOpBuilder(cursor=Some(c))
    def within(e: Long): XOpBuilder = XOpBuilder(start=0, end=e)
    def within(s: Long, e: Long): XOpBuilder = XOpBuilder(start=s, end=e)
    def check(p: Proposition): XOp = XOpBuilder().check(p)
}

/**
 * Until/till Op Builder
 */
case class UOpBuilder(guard: Proposition, start: Long = 0, end: Long = -1, cursor: Option[String] = None) {
    def at(c: String): UOpBuilder = this.copy(cursor = Some(c))
    def within(e: Long): UOpBuilder = this.copy(start=0, end=e)
    def within(s: Long, e: Long): UOpBuilder = this.copy(start=s, end=e)
    def check(p: Proposition): UOp = UOp(p, guard, start, end, cursor)
}

object till {
    def apply(guard: Proposition): UOpBuilder = UOpBuilder(guard)
}
