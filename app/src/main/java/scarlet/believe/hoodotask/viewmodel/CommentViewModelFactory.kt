package scarlet.believe.hoodotask.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class CommentViewModelFactory(private val application: Application,private val postID : String)
    : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CommentViewModel(application,postID) as T
    }
}