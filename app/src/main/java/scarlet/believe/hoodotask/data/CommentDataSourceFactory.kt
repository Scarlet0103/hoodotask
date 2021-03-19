package scarlet.believe.hoodotask.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import scarlet.believe.hoodotask.data.model.CommentData

class CommentDataSourceFactory(
    private val postID : String,
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService
) : DataSource.Factory<Int, CommentData>() {

    val commentDataSourceLiveData = MutableLiveData<CommentDataSource>()

    override fun create(): DataSource<Int, CommentData> {
        val commentDataSource = CommentDataSource(postID,networkService, compositeDisposable)
        commentDataSourceLiveData.postValue(commentDataSource)
        return commentDataSource
    }

}