package xiaoyuz.com.tiktoktrainer.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import xiaoyuz.com.tiktoktrainer.R
import xiaoyuz.com.tiktoktrainer.contract.view.MainFragment
import xiaoyuz.com.tiktoktrainer.utils.addFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MainFragment().also { addFragment(it) }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        super.onKeyDown(keyCode, event)
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (supportFragmentManager.backStackEntryCount == 1) {
                finish()
            }
            return supportFragmentManager.backStackEntryCount >= 1
        }
        return false
    }
}
