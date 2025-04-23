package ir.hasanazimi.me.pagination

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import ir.hasanazimi.me.common.base.BaseFragment
import ir.hasanazimi.me.databinding.FragmentPaginationBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PaginationFragment : BaseFragment<FragmentPaginationBinding>(FragmentPaginationBinding::inflate) {

    private val viewModel: PaginationFragmentVM by viewModels()
    private var adapter=  SampleAdapter()

    override fun initializing() {
        super.initializing()
        binding.recyclerView.adapter = adapter
        binding.swipeRefreshLayout.isRefreshing = true
    }


    override fun uiConfig() {
        super.uiConfig()

        // Combine adapter with LoadStateAdapter for footer loading
        binding.recyclerView.adapter = adapter.withLoadStateFooter(
            footer = SampleLoadStateAdapter { adapter.retry() }
        )

    }


    override fun observers() {
        super.observers()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pager.collectLatest {
                adapter.submitData(it)
            }
        }



        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.isRefreshing.collect {
                binding.swipeRefreshLayout.isRefreshing = it
            }
        }

    }


    override fun listeners() {
        super.listeners()

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewLifecycleOwner.lifecycleScope.launch {
                /*adapter.submitData(PagingData.empty())*/ // Clear the adapter
                viewModel.refresh()
            }
        }


        // Handle loading state by observing the PagingDataAdapter's load state
        adapter.addLoadStateListener { loadState ->
            Log.i(TAG, "listeners - loading changed: ${loadState.refresh} ")
            binding.swipeRefreshLayout.isRefreshing = loadState.refresh is LoadState.Loading
        }


        adapter.addLoadStateListener { loadState ->

            // Show loading spinner during initial load or pagination
            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            binding.recyclerView.isVisible = isListEmpty.not()


            // Handle retry button visibility on error
            if (loadState.append is LoadState.Error) {
                // Show error toast or message when next page fails to load
                showErrorMessage("Error loading more data")
            }

            when (loadState.refresh) {
                is LoadState.Loading -> {
                    Log.i(TAG, "listeners - addLoadStateListener - loadState is LoadState.Loading")
                    // Show loading spinner
                    viewModel.showLoading(true)
                }
                is LoadState.NotLoading -> {
                    Log.i(TAG, "listeners - addLoadStateListener - loadState is LoadState.NotLoading")
                    // Hide loading spinner
                    viewModel.showLoading(false)
                }
                is LoadState.Error -> {
                    Log.i(TAG, "listeners - addLoadStateListener - loadState is LoadState.Error")
                    // Handle error (show error message)
                    viewModel.showLoading(false)
                }
            }



        }





    }

}