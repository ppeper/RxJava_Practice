import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

fun main() {
    /**
     * Disposable
     * 여러 이유로 작업을 취소해야할 수 있다
     * 이 때 Disposable 을 dispose() 함으로써 작업을 취소할 수 있다
     * 여러 Disposable 들을 일일히 dispose() 하면 귀찮으므로 CompositeDisposable 을 사용한다
     * 기본적으로 add 로 Disposable 을 등록하고, clear 로 등록된 작업을 취소한다
     */
    val compositeDisposable = CompositeDisposable()
    compositeDisposable.addAll( // 한개만 등록시 add
        Observable.just(1).subscribe(),
        Single.just(1).subscribe(),
        Maybe.just(1).subscribe()
    )
    compositeDisposable.clear() // 작업이 도중 취소 된다 -> 위에서 모든 Observable, Single, Maybe 가 dispose() 가 된다
    // dispose() 를 사용하면 compositeDisposable 을 더이상 사용이 불가능하다
}