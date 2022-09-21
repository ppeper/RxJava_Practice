import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.concurrent.TimeUnit

// 기타 유용한 연산자
fun main() {
    // 1. delay
    // 정해진 시간만큼 데이터를 늦게 전달하는 연산자
    Observable.just("Hello", "World")
        .delay(1000, TimeUnit.MILLISECONDS)
        .subscribe {
            println(it)
        }
    Thread.sleep(2000)

    // 2. do
    // doOnXX / doAfterXX 로 되어있으며 XX 전/후 동작을 정한다
    // 디버깅하거나 stream 중에 intercept 할 때 쓰면 된다
    Observable.just("Hello", "RxJava")
        .doOnNext { println("doOnNext") }
        .doOnSubscribe { println("doOnSubscribe") }
        .doAfterNext { println("doAfterNext") }
        .doAfterTerminate { println("doAfterTerminate") }
        .doOnEach { println("doOnEach") } // 모든 이벤트에 걸림 complete 까지
        .doFinally { println("doFinally") }
        .doOnComplete { println("doOnComplete") }
        .doOnDispose { println("doOnDispose") }
        .doOnError { println("doOnError") }
        .doOnTerminate { println("doOnTerminate") }
        .subscribe { println("- $it") }

    // 3. subscribeOn / observeOn
    // subscribe : 구독할 스케쥴러를 결정
    // observeOn : onNext, onError 등의 동작을 실행할 스케쥴러 설정. (체이닝하여 변경가능)
    Observable.just(1,2)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.computation())
        .subscribe(::println)

    // 4. timeOut
    // 일정시간 데이터를 전달하지 못하면 Exception 을 onError 로 전닿하는 연산자
    Observable.just("Hello", "World")
        .delay(3000L, TimeUnit.MILLISECONDS)
        .timeout(2000L, TimeUnit.MILLISECONDS)
        .subscribe((::println), (::println))
    Thread.sleep(4000)

    // 5. retry
    // onError 발생 시 retry 할 수 있는 연산자
    // retryWhen / retryUntil 등으로 조건을 줄 수 있다
    Observable.just(1,2,3,4,5,6)
        .map { if (it > 4) throw IOException() else it }
        .retry { cnt, t2 ->
            cnt <= 1
        }
        .subscribe((::println), (::println))

    // 6. andThen
    // Completable 들을 체이닝하여 하나로 만들어주는 연산자
    Completable.complete()
        .andThen(Completable.complete())
        .subscribe { println("Everything Success") }
}