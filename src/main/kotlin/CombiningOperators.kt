import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

// 결합 연산자
fun main() {
    // 1. combineLatest
    // 각각 Observable 에 데이터 생성될 때 데이터를 조합해서 전달하는 연산자
    val observable1 = Observable.interval(1000L, TimeUnit.MILLISECONDS)
    val observable2 = Observable.interval(750L, TimeUnit.MILLISECONDS).map { it + 100 }
    Observable.combineLatest(
        observable1, observable2,
        BiFunction { t1, t2 ->
            "$t1 $t2"
        }
    ).subscribe {
        println(System.currentTimeMillis())
        println(it)
    }
    Thread.sleep(3000)

    // 2. merge
    // 각각의 Observable 을 단순히 합치는 연산자
    // mergeWith 으로 이어 붙일 수 있음
    val observable3 = Observable.interval(0, 1000, TimeUnit.MILLISECONDS).map { "1:$it" }
    val observable4 = Observable.interval(0, 500, TimeUnit.MILLISECONDS).map { "2:$it" }
    val observableM = Observable.merge(observable3, observable4)
    observableM.subscribe {
        println(it)
    }

    // 3. zip
    // Observable 에서 만든 데이터 순서에 맞게 조합하는 연산자
    val observable5 = Observable.just(1,2,3,4,5,6,7)
    val observable6 = Observable.just("a", "b", "c", "d", "e", "f", "g")
    val observableZ = Observable.zip(
        observable5, observable6,
        BiFunction { t1, t2 ->
            "$t1 and $t2"
        }
    )
    observableZ.subscribe(::println)

    // 4. startWith
    // Observable 에 첫번째 데이터를 추가한다
    Observable.just(2,3,4)
        .startWith(1)
        .subscribe(::println)
}