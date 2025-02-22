package ir.ha.meproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ir.ha.mylibrary.common.extensions.singleClick

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.btn).singleClick {
            startActivity(Intent(
                this@MainActivity,
                ir.ha.mylibrary.presentation.MainActivity::class.java
            ))
        }


    }


}