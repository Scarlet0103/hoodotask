package scarlet.believe.hoodotask.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import scarlet.believe.hoodotask.data.*
import scarlet.believe.hoodotask.data.model.CommentData
import scarlet.believe.hoodotask.data.model.Data

class CommentViewModel(application: Application,postID : String) : ViewModel()  {

    private val networkService = NetworkService.getService()
    var commentList: LiveData<PagedList<CommentData>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 5
    private val commentDataSourceFactory: CommentDataSourceFactory

    init {
        commentDataSourceFactory = CommentDataSourceFactory(postID,compositeDisposable, networkService)
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build()
        commentList = LivePagedListBuilder(commentDataSourceFactory, config).build()
    }


    fun getState(): LiveData<State> = Transformations.switchMap(
            commentDataSourceFactory.commentDataSourceLiveData,
            CommentDataSource::state
    )

    fun retry() {
        commentDataSourceFactory.commentDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return commentList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}