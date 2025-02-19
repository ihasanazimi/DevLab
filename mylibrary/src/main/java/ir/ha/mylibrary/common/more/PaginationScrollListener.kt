package ir.ha.mylibrary.common.more

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class PaginationScrollListener(private val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.getItemCount()
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (!isLoading && !isLastPage) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
            ) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
    abstract val isLastPage: Boolean
    abstract val isLoading: Boolean
}



/** Usage :
 *
 *
 * RecyclerView recyclerView = findViewById(R.id.recyclerView);
 * LinearLayoutManager layoutManager = new LinearLayoutManager(this);
 * recyclerView.setLayoutManager(layoutManager);
 * recyclerView.setAdapter(yourAdapter);
 *
 * recyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
 *     @Override
 *     protected void loadMoreItems() {
 *         // Load more items
 *         // Update your adapter or data source with new items
 *     }
 *
 *     @Override
 *     public boolean isLastPage() {
 *         // Return true if all pages have been loaded
 *         return false;
 *     }
 *
 *     @Override
 *     public boolean isLoading() {
 *         // Return true if data is currently being loaded
 *         return false;
 *     }
 * });
 *
 *
 * */

