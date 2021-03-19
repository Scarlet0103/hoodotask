package scarlet.believe.hoodotask.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import scarlet.believe.hoodotask.data.model.Data

class PostsDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService
) : DataSource.Factory<Int, Data>() {

    val postsDataSourceLiveData = MutableLiveData<PostsDataSource>()

    override fun create(): DataSource<Int, Data> {
        val postsDataSource = PostsDataSource(networkService, compositeDisposable)
        postsDataSourceLiveData.postValue(postsDataSource)
        return postsDataSource
    }
}