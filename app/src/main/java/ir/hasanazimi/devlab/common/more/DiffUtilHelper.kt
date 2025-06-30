
package ir.hasanazimi.devlab.common.more

import androidx.recyclerview.widget.DiffUtil

class DiffUtilHelper<T>(
    private val oldList: List<T>,
    private val newList: List<T>,
    private val areItemsTheSame: (T, T) -> Boolean, // Function to compare item IDs
    private val areContentsTheSame: (T, T) -> Boolean // Function to compare item contents
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {

        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsTheSame(oldList[oldItemPosition], newList[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areContentsTheSame(oldList[oldItemPosition], newList[newItemPosition])
    }
}


/** usage :
 *
    fun submitList(newList: List<MyItem>) {

        val diffCallback = DiffUtilCallBack(
        oldList = items [this is global variable into recyclerViewAdapter] ,
        newList = newList,
        areItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id }, // Compare item IDs
        areContentsTheSame = { oldItem, newItem -> oldItem == newItem } // Compare item contents
        )

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = newList
        diffResult.dispatchUpdatesTo(this)
    }
 *
 * */
