package xjtu.graphics.unsplash

import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {
    abstract fun onBackPressed(): Boolean
}