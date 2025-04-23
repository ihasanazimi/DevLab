package ir.hasanazimi.me.presentation.features.fragments.developer_info

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ir.hasanazimi.me.R
import ir.hasanazimi.me.common.base.BaseFragment
import ir.hasanazimi.me.common.extensions.withNotNull
import ir.hasanazimi.me.data.model.data.developer_info.DeveloperInfo
import ir.hasanazimi.me.databinding.FragmentDeveloperInfoBinding
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DeveloperInfoFragment  : BaseFragment<FragmentDeveloperInfoBinding>(FragmentDeveloperInfoBinding::inflate) {

    private val viewModel : DeveloperInfoFragmentVM by viewModels()
    private val sendRequestByCoroutines = true

    override fun initializing() {
        super.initializing()

        if (sendRequestByCoroutines){
            viewModel.getDeveloperInfoByKotlinCoroutines()
        }else {
            viewModel.getDeveloperInfoByRxKotlin()
        }

    }

    override fun observers() {
        super.observers()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.developerInfoForKotlinCoroutines.collect{
                Log.i(TAG, "DATA received By KotlinCoroutines at this time -> ${System.currentTimeMillis()}")
                updateUi(it)
            }
        }

        viewModel.developerInfoForRxAndroid.observe(viewLifecycleOwner){
            Log.i(TAG, "DATA received By RxAndroid at this time -> ${System.currentTimeMillis()}")
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
            showMessage("done!")
        }
    }

    private fun loadProfileImage() = Glide.with(requireContext())
        .load("https://img.freepik.com/free-psd/3d-illustration-human-avatar-profile_23-2150671142.jpg")
        .placeholder(R.drawable.loading)
        .error(R.drawable.ic_failed)
        .into(binding.profileProfileIV)


}