package week1

import io.reactivex.Observable

fun main() {
    Observable
        .just(0,1,2,3)
        .map { it * 2 }
        .subscribe { println(it) }
}