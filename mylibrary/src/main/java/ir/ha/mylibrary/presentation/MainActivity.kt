package ir.ha.mylibrary.presentation

import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import ir.ha.mylibrary.R
import ir.ha.mylibrary.common.base.BaseActivity
import ir.ha.mylibrary.common.extensions.addFragmentByAnimation
import ir.ha.mylibrary.databinding.ActivityMainBinding
import ir.ha.mylibrary.presentation.features.fragments.users.UsersFragment


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

        addFragmentByAnimation(UsersFragment(), UsersFragment::class.java.simpleName,false,true, R.id.main)

    }

}