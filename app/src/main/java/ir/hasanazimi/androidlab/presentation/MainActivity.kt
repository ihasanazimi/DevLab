package ir.hasanazimi.androidlab.presentation

import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import ir.hasanazimi.androidlab.R
import ir.hasanazimi.androidlab.common.base.BaseActivity
import ir.hasanazimi.androidlab.common.extensions.addFragmentByAnimation
import ir.hasanazimi.androidlab.databinding.ActivityMainBinding
import ir.hasanazimi.androidlab.presentation.features.fragments.pagination.PaginationFragment


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


        addFragmentByAnimation(PaginationFragment(), PaginationFragment::class.java.simpleName,true,true,R.id.main)

    }

}