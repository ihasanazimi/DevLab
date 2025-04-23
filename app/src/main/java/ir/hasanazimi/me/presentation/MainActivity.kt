package ir.hasanazimi.me.presentation

import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ir.hasanazimi.me.R
import ir.hasanazimi.me.databinding.ActivityMainBinding
import ir.hasanazimi.me.presentation.features.fragments.temp1.Temp1Fragment
import ir.hasanazimi.me.common.base.BaseActivity
import ir.hasanazimi.me.common.extensions.addFragmentByAnimation

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initializing() {
        super.initializing()

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        addFragmentByAnimation(Temp1Fragment(), Temp1Fragment::class.java.simpleName,true,true,R.id.main)

    }

}