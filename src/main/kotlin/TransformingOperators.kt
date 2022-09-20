import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import kotlin.random.Random

// 변환 연산자
fun main() {
    // 1. buffer(count: Int, skip: Int) 형태로 count 만큼 데이터가 모이면 한번에 전달
    // count 까지 포함해서 skip 만큼의 data 는 버린다. (count < skip 이면 차이만큼 데이터 무시)
    // 마지막 남은 데이터는 count 만큼 차지 않아도 전달
    Observable.fromIterable(0..8)
        .buffer(2, 4)
        .subscribe(::println)
    println()

    // 2. map
    // 데이터를 변환하는 연산자
    Observable.fromIterable(0..3)
        .map { "RxJava : $it" }
        .subscribe(::println)
    println()

    // 3. xxxMap
    // 공통 : Observable 을 받아 새로운 Observable 을 만드는 연산자

    // flatMap : 데이터를 병렬적으로 처리
    // -> 랜덤한 순서로 출력
    Observable.fromIterable(listOf(1, 2, 3, 4, 5, 6))
        .flatMap { original: Int ->
            Observable.just("$original plusplus")
                .delay(Random.nextLong(5), TimeUnit.SECONDS)
        }
        .subscribe(::println)
    println()

    // concatMap : 데이터를 직렬적으로 처리
    // -> 순서대로 출력
    Observable.fromIterable(listOf(1, 2, 3, 4, 5, 6))
        .concatMap { original: Int ->
            Observable.just("$original plusplus")
                .delay(Random.nextLong(5), TimeUnit.SECONDS)
        }
        .subscribe(::println)
    println()

    // switchMap : 중간에 데이터가 들어오면 무시
    // -> 6만 출력
    Observable.fromIterable(listOf(1, 2, 3, 4, 5, 6))
        .switchMap { original: Int ->
            Observable.just("$original plusplus")
                .delay(Random.nextLong(5), TimeUnit.SECONDS)
        }
        .subscribe(::println)
    println()

    // 4. scan
    // 이전 데이터와 현재 데이터를 조합하여 데이터를 전달하는 연산자
    // 첫 데이터는 그대로 전달한다. reduce 로 이해하면 된다
    // 0 -> 0 + 1 -> (0 + 1) + 2 -> (0 + 1 + 2) + 3
    Observable.fromIterable(0..3)
        .scan { t1, t2 -> t1 + t2 }
        .subscribe(::println)
}
