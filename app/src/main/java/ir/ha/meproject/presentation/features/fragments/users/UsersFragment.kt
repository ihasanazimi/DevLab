package ir.ha.meproject.presentation.features.fragments.users

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ir.ha.meproject.common.base.BaseFragment
import ir.ha.meproject.common.extensions.hide
import ir.ha.meproject.common.extensions.show
import ir.ha.meproject.databinding.FragmentUsersBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate) {

    private val viewModel : UsersFragmentVM by viewModels()

    override fun initializing() {
        super.initializing()
        viewModel.getUsers()
    }

    override fun observers() {
        super.observers()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.users.collect{ users ->
                Log.i(TAG, "observers: $users")
                binding.loading.hide()
                binding.tv.show()
                binding.tv.text = users.first().name
            }
        }


    }


    override fun listeners() {
        super.listeners()
    }

}