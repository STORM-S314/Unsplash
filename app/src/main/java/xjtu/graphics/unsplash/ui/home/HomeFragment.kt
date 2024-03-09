package xjtu.graphics.unsplash.ui.home

import xjtu.graphics.unsplash.BaseFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import xjtu.graphics.unsplash.MainActivity
import xjtu.graphics.unsplash.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val activity by lazy{ getActivity() as MainActivity }
    private lateinit var webView:WebView
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root
        webView = WebView(root.context)
        root.addView(webView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        webView.webViewClient=MyWebViewClient()
        webView.loadUrl("https://unsplash.com/")
        return root
    }
    override fun onResume() {
        super.onResume()
        activity.currentFragment = this
    }
    override fun onDestroyView() {
        super.onDestroyView()
        webView.destroy()
        _binding = null
    }
    override fun onBackPressed(): Boolean {
        if(webView.canGoBack())
        {
            webView.goBack()
            return true
        }
        return false
    }
}