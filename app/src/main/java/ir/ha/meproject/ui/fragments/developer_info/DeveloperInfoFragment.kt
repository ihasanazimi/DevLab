package ir.ha.meproject.ui.fragments.developer_info

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ir.ha.meproject.R
import ir.ha.meproject.databinding.FragmentDeveloperInfoBinding
import ir.ha.meproject.model.data.developer_info.DeveloperInfo
import ir.ha.meproject.utility.base.BaseFragment
import ir.ha.meproject.utility.extensions.withNotNull
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DeveloperInfoFragment  : BaseFragment<FragmentDeveloperInfoBinding>(FragmentDeveloperInfoBinding::inflate) {

    private val viewModel : DeveloperInfoFragmentVM by viewModels()


    override fun initializing() {
        super.initializing()
        viewModel.getDeveloperInfoByKotlinCoroutines()
    }

    override fun observers() {
        super.observers()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.developerInfo.collect{
                updateUi(it)
            }
        }


    }

    private fun updateUi(it: DeveloperInfo) {
        it.withNotNull {
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