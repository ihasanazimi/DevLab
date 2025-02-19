package ir.ha.mylibrary.presentation.features.fragments.users

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.ha.mylibrary.common.base.BaseViewModel
import ir.ha.mylibrary.data.model.Users
import ir.ha.mylibrary.domain.UserUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UsersFragmentVM @Inject constructor(
    private val userUseCase: UserUseCase
) : BaseViewModel() {

    private val _users = MutableSharedFlow<Users>()
    val users = _users.asSharedFlow()

    fun getUsers(){
        viewModelScope.launch {
            userUseCase.getUsers().collect{
                _users.emit(it)
            }
        }
    }

}