package week2

import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

fun main() {

    //publishSubject()

    behaviorSubject()

}

fun publishSubject() {
    val publishSubject = PublishSubject.create<Int>()
    publishSubject.subscribe{println("1번째 구독자 $it")}
    publishSubject.onNext(1)
    publishSubject.subscribe{println("2번째 구독자 $it")}
    publishSubject.onNext(2)
    publishSubject.subscribe{println("3번째 구독자 $it")}
    publishSubject.onNext(3)
}

fun behaviorSubject() {
    val behaviorSubject = BehaviorSubject.create<Int>()
    val observable = behaviorSubject.doOnSubscribe { println("생성") }
    observable.subscribe{ println("1번째 구독자 $it")}
    behaviorSubject.onNext(1)
    observable.subscribe{ println("2번째 구독자 $it")}
    behaviorSubject.onNext(2)
    behaviorSubject.onNext(3)
    observable.subscribe{ println("3번째 구독자 $it")}
    behaviorSubject.onNext(4)

}