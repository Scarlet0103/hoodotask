package scarlet.believe.hoodotask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import scarlet.believe.hoodotask.data.NetworkService
import scarlet.believe.hoodotask.data.PostsDataSource
import scarlet.believe.hoodotask.data.PostsDataSourceFactory
import scarlet.believe.hoodotask.data.State
import scarlet.believe.hoodotask.data.model.Data

class PostsViewModel : ViewModel() {

    private val networkService = NetworkService.getService()
    var postList: LiveData<PagedList<Data>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 5
    private val postsDataSourceFactory: PostsDataSourceFactory

    init {
        postsDataSourceFactory = PostsDataSourceFactory(compositeDisposable, networkService)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        postList = LivePagedListBuilder(postsDataSourceFactory, config).build()
    }


    fun getState(): LiveData<State> = Transformations.switchMap(
        postsDataSourceFactory.postsDataSourceLiveData,
        PostsDataSource::state
    )

    fun retry() {
        postsDataSourceFactory.postsDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return postList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}