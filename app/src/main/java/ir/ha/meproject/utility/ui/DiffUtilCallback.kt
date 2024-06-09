package ir.ha.meproject.utility.ui

import androidx.recyclerview.widget.DiffUtil

/** usage :
 *
 * DiffUtil.DiffResult result = DiffUtil.calculateDiff(new MyDiffUtil<>(oldList, newList));
 * result.dispatchUpdatesTo(yourAdapter);
 *
 * */


class MyDiffUtil<T>(private val oldList: List<T>, private val newList: List<T>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Here you should compare unique identifiers of your items
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // If areItemsTheSame() returns true, this method checks if the contents are the same
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
