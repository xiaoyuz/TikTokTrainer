package xiaoyuz.com.tiktoktrainer.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import xiaoyuz.com.tiktoktrainer.R


fun AppCompatActivity.addFragment(fragment: Fragment) {
    supportFragmentManager.transact {
        supportFragmentManager.let {
            if (it.backStackEntryCount > 0) {
                val fromFragment = it.findFragmentByTag(it.getBackStackEntryAt(it.backStackEntryCount - 1).name)
                hide(fromFragment)
            }
            add(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
            addToBackStack(fragment.javaClass.simpleName)
        }
    }
}

fun AppCompatActivity.popFragment() {
    supportFragmentManager.popBackStack()
    supportFragmentManager.transact {
        supportFragmentManager.let {
            val preFragment = it.findFragmentByTag(it.getBackStackEntryAt(it.backStackEntryCount - 1).name)
            show(preFragment)
        }
    }
}

/**
 * Runs a FragmentTransaction, then calls commit().
 */
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
    executePendingTransactions()
}