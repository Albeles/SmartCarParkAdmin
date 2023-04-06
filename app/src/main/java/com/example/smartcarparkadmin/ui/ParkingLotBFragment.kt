package com.example.smartcarparkadmin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.smartcarparkadmin.R
import com.example.smartcarparkadmin.databinding.ParkinglotbBinding

class ParkingLotBFragment : Fragment() {

    private lateinit var binding: ParkinglotbBinding
    private val nav by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ParkinglotbBinding.inflate(inflater, container, false)

        val webView: WebView = binding.webview
//        var url = "http://127.0.0.2:1234/" // replace with your own IP address
        val webSettings: WebSettings = webView.settings
        webSettings.allowFileAccess = true
        webView.loadUrl("http://172.16.110.91:2204/live3")

        binding.compoundsubmit.setOnClickListener{insertC()}

        return binding.root
    }
    private fun insertC() {
        nav.navigate(R.id.compoundInsertB)
    }

    //private fun restore() {
    //    val ctx = requireContext()
    //    RESTORE_USERS(ctx)
    //    snackbar("Users restored.")
}

