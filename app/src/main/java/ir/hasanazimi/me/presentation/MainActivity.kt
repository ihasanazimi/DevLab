package ir.hasanazimi.me.presentation

import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import ir.hasanazimi.me.R
import ir.hasanazimi.me.databinding.ActivityMainBinding
import ir.hasanazimi.me.presentation.features.fragments.x.XFragment
import ir.hasanazimi.me.common.base.BaseActivity
import ir.hasanazimi.me.common.extensions.addFragmentByAnimation


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


        addFragmentByAnimation(XFragment(), XFragment::class.java.simpleName,true,true,R.id.main)

    }

}