package week4

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.zipWith
import java.util.concurrent.TimeUnit
import kotlin.random.Random

fun main() {
    timeOut()
}

fun debounce() {
    Observable.interval(250, TimeUnit.MILLISECONDS)
        .debounce(200, TimeUnit.MILLISECONDS)
        .subscribe { println(it) }

    Thread.sleep(1500)
}

fun distinct() {
    Observable.just(1, 1, 2, 1, 2, 3)
        .distinct()
        .subscribe { println(it) }
}

fun distinctUntilChanged() {
    Observable.just(1, 1, 2, 1, 2, 3)
        .distinctUntilChanged()
        .subscribe { println(it) }
}

fun filter() {
    Observable.just(1, 1, 2, 1, 2, 3)
        .filter { it % 2 == 1 }
        .subscribe { println(it) }
}

fun ofType() {
    Observable.just(1, true, "Hello", "World", 2f)
        .ofType(String::class.java) // filter -> type casting까지 진행
        .subscribe { println(it) }
}


//데이터 요스는 사용하지 않고, 전송 결과만 받고 싶을때
fun ignoreElements() {
    Observable.just(1, 1, 2, 1, 2, 3)
        .ignoreElements()
        .subscribe { println("Complete") }
}

//예시 버튼 중복 방지
//경계에선 나올수도 안나올수도 있다.
//각각 스레드에서 돌기떄문에 타이밍 이슈로 인해 경계에서 안나올수도 있음
fun throttleFirst() {
    println(System.currentTimeMillis())
    val observable = Observable.interval(100, TimeUnit.MILLISECONDS)
        .throttleFirst(250, TimeUnit.MILLISECONDS)

    observable.subscribe {
        println(System.currentTimeMillis())
        println(it)
    }

    Thread.sleep(1000)
}

fun throttleLast() {
    println(System.currentTimeMillis())
    val observable = Observable.interval(0, 100, TimeUnit.MILLISECONDS)
        .throttleLast(250, TimeUnit.MILLISECONDS)

    observable.subscribe {
        println(System.currentTimeMillis())
        println(it)
    }

    Thread.sleep(1000)
}


//skipLast는 complete가 호출되고 난 다음 실행가능
//마지막을 알아야 하기때문
fun skip() {
    Observable.just(1, 1, 2, 1, 2, 3)
        .skip(2)
//        .skipLast(2)
        .subscribe { println(it) }
}

//single과 거의 동일하지만 take는 Observable을 return
fun take() {
    Observable.just(1, 1, 2, 1, 2, 3)
        .take(2)
//        .takeLast(2)
        .subscribe { println(it) }
}


fun combineLatest() {
    val observable1 = Observable.interval(1000L, TimeUnit.MILLISECONDS)
    val observable2 = Observable.interval(750L, TimeUnit.MILLISECONDS).map { Random.nextInt(10) }
    val observable = Observable.combineLatest<Long, Int, String>(
        observable1, observable2, BiFunction<Long, Int, String> { t1, t2 -> "$t1 $t2" }
    )

    observable.subscribe {
        println(System.currentTimeMillis())
        println(it)
    }

    Thread.sleep(3000)
}

fun merge() {
    val observable1 = Observable.interval(0, 1000L, TimeUnit.MILLISECONDS).map { "1:$it" }
    val observable2 = Observable.interval(0, 500L, TimeUnit.MILLISECONDS).map { "2:$it" }.mergeWith(observable1)
    val observable = Observable.merge(observable1, observable2)

//    observable.subscribe {
//        println(System.currentTimeMillis())
//        println(it)
//    }

    observable2.subscribe { println(it) }

    Thread.sleep(3000)
}

fun startWith() {
    Observable.just(1, 2, 3, 4, 5)
        .startWith(100)
        .subscribe { println(it) }

}

fun zip() {
    val observable1 = Observable.just(1, 2, 3, 4, 5)
    val observable2 = Observable.just("a", "b", "c", "d", "e")
        .zipWith(observable1, BiFunction<String, Int, String> { t1: String, t2: Int ->
            "$t1 $t2"
        })
    val observable = Observable.zip(observable1, observable2, BiFunction<Int, String, String> { t1, t2 ->
        "$t1 $t2"
    })

    observable1.zipWith(observable2, { t1, t2 ->
        "$t1 $t2"
    }).subscribe { println(it) }

//    observable.subscribe { println(it) }
//
//    observable2.subscribe { println(it) }

}

fun delay() {
    println(System.currentTimeMillis())
    Observable.just("Hello World")
        .delay(500, TimeUnit.MILLISECONDS)
        .subscribe {
            println(System.currentTimeMillis())
            println(it)
        }

    Thread.sleep(2000)
}

fun doOnMethod() {
    Observable.just("Hello", "World")
        .doOnNext { println("doOnNext") }
        .doOnSubscribe { println("doOnNext") }
        .doAfterNext { println("doAfterNext") }
        .doAfterTerminate { println("doAfterTerminate") } // 언제나 호출 error나 complete 둘다
        .doFinally { println("doFinally") }
        .doOnComplete { println("doOnComplete") }
        .doOnDispose { println("doOnDispose") }
        .doOnEach { println("doOnEach") } // 각각의 이벤트라고 생각
        .doOnError { println("doOnError") }
        .doOnTerminate { println("doOnTerminate") }
        .subscribe { println("-$it") }
}

fun test() {
    Observable.just(1,2,3,4,5)
        .doOnTerminate {  }
        .doOnError {  }
}

fun timeOut() {
    Observable.just("Hello", "World")
        .delay(3000L, TimeUnit.MILLISECONDS)
//        .timeout(2000L, TimeUnit.MILLISECONDS,Observable.just("TImeOut"))
        .timeout(2000L, TimeUnit.MILLISECONDS) { it.onNext("TimeOut") }
//        .timeout(2000L, TimeUnit.MILLISECONDS) // onError를 타게하려면 other를 없애면 된다
        .subscribe({ println(it) }, { it.printStackTrace() })

    Thread.sleep(4000)
}

