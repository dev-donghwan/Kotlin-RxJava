package week3

import io.reactivex.Completable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun main() {

    create()

}

fun create() {
    //create 안에서 로직을 처리하고 싶을때 주로 사용.
    //subscribe한 다음 실행
    //마치 클릭리스너
    Observable.create<String> { emitter ->
        emitter.onNext("Hello")
        emitter.onNext("World")
        emitter.onComplete()
    }.subscribe { println(it) }
}

fun defer() {
    val observable = Observable.defer {
        Observable.create<Int> {
            println("생산")
            it.onNext(1)
        }
    }
    observable.subscribe { println(it) }
    observable.subscribe { println(it) }
}

fun fromArray() {
    val items = arrayOf("Hello", "World")
    Observable.fromArray(*items).subscribe { println(it) }
}

fun fromCallable() {
    println("start : ${System.currentTimeMillis()}")
    val observable = Observable.fromCallable {
        println("create : ${System.currentTimeMillis()}")
        "HelloWorld"
    }
    Thread.sleep(1000)
    observable.subscribe { println(it) }
    Thread.sleep(1000)
    observable.subscribe { println(it) }
}

fun interval() {
    Observable.interval(0, 1000, TimeUnit.MILLISECONDS).subscribe { println(it) }
    Thread.sleep(3000)
}

fun range() {
    Observable.range(3, 2).subscribe { println(it) }
}

fun repeat() {
    //repeat에 인자가 없으면 무한반복
    val observable = Observable.just("Hello", "World")
        .doOnSubscribe { println("subscribe") }
        .repeat(1)
    observable.subscribe { println(it) }
}

fun timer() {
    println("Start: \t\t ${System.currentTimeMillis()}")
    Observable.timer(1000, TimeUnit.MILLISECONDS)
        .subscribe { println("subscribe : \t${System.currentTimeMillis()}") }
    Thread.sleep(2000)
}

fun buffer() {
    //default는 buffer size
    Observable.fromIterable(0..10)
        .buffer(2)
        .subscribe { println(it) }
}

fun flatMap() {
    Observable.just(1, 2, 3)
        .flatMap { Observable.just("$it Hello", "$it World") }
        .subscribe { println(it) }
}

fun map() {
    Observable.just(0, 1, 2)
        .map { "RxJava : $it" }
        .subscribe { println(it) }
}

fun scan() {
    Observable.fromIterable(0..3)
        .scan{t1: Int, t2: Int ->
            t1 + t2
        }.subscribe { println(it) }
}