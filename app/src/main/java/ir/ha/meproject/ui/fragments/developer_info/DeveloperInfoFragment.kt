package ir.ha.meproject.ui.fragments.developer_info

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ir.ha.meproject.R
import ir.ha.meproject.databinding.FragmentDeveloperInfoBinding
import ir.ha.meproject.model.data.developer_info.DeveloperInfo
import ir.ha.meproject.utility.base.BaseFragment
import ir.ha.meproject.utility.extensions.withNotNull
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@AndroidEntryPoint
class DeveloperInfoFragment  : BaseFragment<FragmentDeveloperInfoBinding>(FragmentDeveloperInfoBinding::inflate) {

    private val viewModel : DeveloperInfoFragmentVM by viewModels()

    override fun initializing() {
        super.initializing()
        viewLifecycleOwner.lifecycleScope.launch {
            runBlocking {
                val temp1 = async {
                    viewModel.getDeveloperInfoByKotlinCoroutines()
                }
                val temp2 = async {
                    viewModel.getDeveloperInfoByRxKotlin()
                }
                val t1 = temp1.await()
                val t2 = temp2.await()
            }
        }
    }

    override fun observers() {
        super.observers()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.developerInfoFlow.collect{
                Log.i(TAG, "DATA received is BY Kotlin Coroutines ${System.currentTimeMillis()}")
                updateUi(it)
            }
        }

        viewModel.developerInfoLiveData.observe(viewLifecycleOwner){
            Log.i(TAG, "DATA received is BY RX ${System.currentTimeMillis()}")
            updateUi(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner){
            Log.i(TAG, "observers - errorMessage : $it")
            showErrorMessage(it)
        }

        viewModel.showLoading.observe(viewLifecycleOwner){
            Log.i(TAG, "observers - showLoading : $it")
            binding.loadingBar.root.isVisible = it
        }


    }

    private fun updateUi(developerInfo: DeveloperInfo) {
        developerInfo.withNotNull {
            loadProfileImage()
            binding.profleFullNameTV.text = "Mr." + it.firstName.plus(" ").plus(it.lastName)
            binding.jobTitleTV.text = it.jobTitle + " at " + it.resume.organizes.first().organizeName
        }
    }

    private fun loadProfileImage() = Glide.with(requireContext())
        .load("https://img.freepik.com/free-psd/3d-illustration-human-avatar-profile_23-2150671142.jpg")
        .placeholder(R.drawable.loading)
        .error(R.drawable.ic_failed)
        .into(binding.profileProfileIV)


}