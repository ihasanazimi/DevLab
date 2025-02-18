package ir.ha.meproject.presentation

import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import ir.ha.meproject.R
import ir.ha.meproject.databinding.ActivityMainBinding
import ir.ha.meproject.presentation.features.fragments.users.UsersFragment
import ir.ha.meproject.common.base.BaseActivity
import ir.ha.meproject.common.extensions.addFragmentByAnimation


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initializing() {
        super.initializing()

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        addFragmentByAnimation(UsersFragment(), UsersFragment::class.java.simpleName,true,true,R.id.main)

    }

}