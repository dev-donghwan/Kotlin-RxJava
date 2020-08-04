package week1

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

fun main() {
    val compositeDisposable = CompositeDisposable()
    val disposable1 = Observable.just(1, 2)
        .subscribe { println(it) }

    compositeDisposable.add(disposable1)
    compositeDisposable.dispose()

    val disposable2 = Observable.just(3, 4).subscribe { println(it) }
    compositeDisposable.add(disposable2)
    Thread.sleep(2000L)

    //disposed 보단 clear 사용 dispose method 분석하기
    //dispose를 시키면 더 이상 사용할 수 없다.

}