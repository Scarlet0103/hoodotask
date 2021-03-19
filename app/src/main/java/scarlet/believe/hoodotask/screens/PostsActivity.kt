package scarlet.believe.hoodotask.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import scarlet.believe.hoodotask.R
import scarlet.believe.hoodotask.adapter.PostListAdapter
import scarlet.believe.hoodotask.data.State
import scarlet.believe.hoodotask.utils.RecyclerVewClickListner
import scarlet.believe.hoodotask.viewmodel.PostsViewModel

class PostsActivity : AppCompatActivity(), RecyclerVewClickListner {

    private lateinit var viewModel: PostsViewModel
    private lateinit var postListAdapter: PostListAdapter

    private lateinit var recyclerView : RecyclerView
    private lateinit var txtError : TextView
    private lateinit var progressBar: ProgressBar
    var height : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        viewModel = ViewModelProvider(this).get(PostsViewModel::class.java)
        initView()
        initAdapter()
        initState()
        initHeight()

    }

    private fun initView(){
        recyclerView = findViewById(R.id.recycler_view)
        txtError = findViewById(R.id.txt_error)
        progressBar = findViewById(R.id.progress_bar)
    }

    private fun initAdapter(){
        postListAdapter = PostListAdapter({viewModel.retry()},this)
        recyclerView.adapter = postListAdapter
        viewModel.postList.observe(this,
            Observer {
                postListAdapter.submitList(it)
            })

    }

    private fun initState(){
        txtError.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, Observer { state ->
            progressBar.visibility = if (viewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            txtError.visibility = if (viewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                postListAdapter.setState(state ?: State.DONE)
            }
        })

    }

    private fun initHeight(){
        val displayMetrics = DisplayMetrics()
        windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels
    }

    override fun onRecyclerViewItemClick(view: View, postID: String) {
        val fragment = CommentFragment(postID,height/2).newInstance()
        fragment?.show(supportFragmentManager,"comments")
    }

}