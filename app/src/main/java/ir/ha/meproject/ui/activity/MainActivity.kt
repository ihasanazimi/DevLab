package ir.ha.meproject.ui.activity

import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ir.ha.meproject.R
import ir.ha.meproject.databinding.ActivityMainBinding
import ir.ha.meproject.ui.fragments.temp1.Temp1Fragment
import ir.ha.meproject.utility.base.BaseActivity
import ir.ha.meproject.utility.extensions.addFragmentByAnimation

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