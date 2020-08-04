package week1

typealias Observer<T> = (event: T) -> Unit

class EventSource<T> {
    private val observers = mutableListOf<Observer<T>>()

    fun addObserver(observer: Observer<T>) {
        observers += observer
    }

    fun notify(event: T) {
        observers.forEach { it(event) }
    }
}

fun main() {

    val eventSource = EventSource<String>()
    eventSource.addObserver {
        println("첫 번째 옵저버: $it")
    }
    eventSource.addObserver { println("두 번째 옵저버: $it") }
    eventSource.notify("Hello")
    eventSource.notify("week1.Observer Pattern")
}