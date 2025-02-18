package ir.ha.meproject.presentation.features.fragments.users

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.ha.meproject.common.base.BaseViewModel
import ir.ha.meproject.data.model.Users
import ir.ha.meproject.domain.UserUseCase
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