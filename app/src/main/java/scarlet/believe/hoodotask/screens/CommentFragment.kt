package scarlet.believe.hoodotask.screens

import android.media.Image
import android.os.Bundle
import android.text.style.LineHeightSpan
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import scarlet.believe.hoodotask.R
import scarlet.believe.hoodotask.adapter.CommentListAdapter
import scarlet.believe.hoodotask.data.State
import scarlet.believe.hoodotask.viewmodel.CommentViewModel
import scarlet.believe.hoodotask.viewmodel.CommentViewModelFactory


class CommentFragment(private val postID: String,private val height : Int) : BottomSheetDialogFragment(){

    private lateinit var viewModel: CommentViewModel
    private lateinit var commentListAdapter: CommentListAdapter

    private lateinit var txtError : TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var noCommentImage : ImageView
    private lateinit var constraintLayout: ConstraintLayout

    fun newInstance(): CommentFragment? {
        return CommentFragment(postID,height)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_comment, container, false)
        viewModel = ViewModelProvider(this, CommentViewModelFactory(this.activity!!.application, postID))
                    .get(CommentViewModel::class.java)
        initView(view)
        initAdapter()
        initState()
        return view
    }

    private fun initView(view: View){
        recyclerView = view.findViewById(R.id.recycler_view_comment)
        txtError = view.findViewById(R.id.txt_error_frag)
        progressBar = view.findViewById(R.id.progress_bar_frag)
        noCommentImage = view.findViewById(R.id.no_comments)
        constraintLayout = view.findViewById(R.id.layout_comment)
        constraintLayout.minHeight = height
    }

    private fun initAdapter(){
        commentListAdapter = CommentListAdapter {viewModel.retry()}
        recyclerView.adapter = commentListAdapter
        viewModel.commentList.observe(this.viewLifecycleOwner,
                Observer {
                    commentListAdapter.submitList(it)
                    if (it.isEmpty()) noCommentImage.visibility = View.VISIBLE
                })

    }

    private fun initState(){
        txtError.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this.viewLifecycleOwner, Observer { state ->
            progressBar.visibility = if (viewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            txtError.visibility = if (viewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                commentListAdapter.setState(state ?: State.DONE)
            }
        })

    }


}