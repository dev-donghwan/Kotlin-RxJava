package week2

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiConsumer
import io.reactivex.plugins.RxJavaPlugins
import java.lang.Exception

fun main() {

}

fun exam() {
    ObservableSource<Int> {
        it.onNext(1)
        it.onNext(2)
        it.onNext(3)
    }.subscribe(object : Observer<Int> {
        override fun onComplete() {
        }

        override fun onSubscribe(d: Disposable) {
        }

        override fun onNext(t: Int) {
            println("$t")
        }

        override fun onError(e: Throwable) {
        }
    })

    //RxJavaPlugins.setErrorHandler << 전부 캐치 가능
    RxJavaPlugins.setErrorHandler {
        //error handler
        //onErrorReturn은 타지 않는다.
        //doOnError는 캐치한다
    }
    
    //consumer 방식
    val observable = Observable.just(1, 2, 3)
    observable.subscribe()
    observable.subscribe {}
    observable.subscribe({}, {})
    observable.subscribe({}, {}, {})


    //single consumer
    Single.just(1)
        .subscribe({}, {})

    //
    Single.just(1)
        .subscribe(object : BiConsumer<Int, Throwable> {
            override fun accept(t1: Int?, t2: Throwable?) {
                //notnull check를 해줘야함
                //t1 != null , t2 != null
            }

        })
}