package ir.hasanazimi.me.presentation.features.fragments.pagination

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ir.hasanazimi.me.R
import kotlinx.coroutines.delay

class SamplePagingSource : PagingSource<Int, String>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        Log.i("SamplePagingSource", "load: ")
        return try {
            val currentPage = params.key ?: 1
            val data = loadDataFromNetworkOrDb(currentPage) // Function to load data
            LoadResult.Page(
                data = data,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (data.isNotEmpty()) currentPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private suspend fun loadDataFromNetworkOrDb(page: Int): List<String> {
        Log.i("SamplePagingSource", "loadDataFromNetworkOrDb by page: $page")

        delay(1000) /* Simulate loading delay */

        // Simulate fetching data from a network or database
        return listOf(
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 4",
            "Item 5",
            "Item 6",
            "Item 7",
            "Item 8",
            "Item 9",
            "Item 10",
            "______________________________________",
        )
    }

    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        Log.i("SamplePagingSource", "getRefreshKey: ")
        val anchorPos = state.anchorPosition?:return  null
        val closestPage = state.closestPageToPosition(anchorPos)
        return closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
    }
}





class SampleAdapter : PagingDataAdapter<String, SampleAdapter.SampleViewHolder>(DIFF_CALLBACK) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return SampleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }




    class SampleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: String?) {
            (itemView as TextView).text = item
        }
    }



    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {

            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }


}









class SampleLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<SampleLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_load_state, parent, false)
        return LoadStateViewHolder(view, retry)
    }

    class LoadStateViewHolder(view: View, retry: () -> Unit) : RecyclerView.ViewHolder(view) {

        private val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        private val retryButton: Button = view.findViewById(R.id.retryButton)

        init {
            retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Loading) {
                progressBar.visibility = View.VISIBLE
                retryButton.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                if (loadState is LoadState.Error) {
                    retryButton.visibility = View.VISIBLE
                }
            }
        }
    }
}

