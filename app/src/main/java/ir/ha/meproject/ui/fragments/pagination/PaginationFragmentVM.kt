package ir.ha.meproject.ui.fragments.pagination

import androidx.lifecycle.viewModelScope
import ir.ha.meproject.utility.base.BaseViewModel
import kotlinx.coroutines.launch

class PaginationFragmentVM : BaseViewModel() {


    fun getFakeData(url : String = "https://jsonplaceholder.typicode.com/todos"){
        viewModelScope.launch {

        }
    }


}