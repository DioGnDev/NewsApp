package com.android.news.view.impl

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.android.news.R
import kotlinx.android.synthetic.main.fragment_webview.*

class WebViewFragment: Fragment() {

    var linkUrl: String = ""

    companion object {

        fun newInstance(linkUrl: String): WebViewFragment{
            var wvFragment = WebViewFragment()
            var bundle = Bundle()
            bundle.putString("url", linkUrl)
            return wvFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.also {
            linkUrl = it.getString("url")
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_webview, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        webView.loadUrl(linkUrl)

        LayoutInflater.from(view.context)?.apply {
            var emptyView = inflate(R.layout.empty_text, null, false)
            webView.visibility = View.GONE
            var textView = emptyView.findViewById<TextView>(R.id.emptyText)
            textView.text = getString(R.string.no_action)
            textView.textSize = 24.0F
            textView.setTextColor(resources.getColor(R.color.black))
            containerView.addView(emptyView)
            var lParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT)
            emptyView.layoutParams = lParams
        }
    }
}