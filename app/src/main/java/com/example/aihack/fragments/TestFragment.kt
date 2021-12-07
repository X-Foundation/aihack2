package com.example.aihack.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aihack.R
import com.example.aihack.utils.CameraHelper

class TestFragment : Fragment() {
    private lateinit var cameraHelper: CameraHelper
    private lateinit var safeContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.test_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraHelper = CameraHelper(
            owner = requireActivity(),
            context = safeContext,
            viewFinder = view.findViewById(R.id.cameraView),
            overlay = view.findViewById(R.id.graphicOverlay_finder)
        )
        cameraHelper.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraHelper.stop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        cameraHelper.onRequestPermissionsResult(requestCode)
    }

    companion object {
        const val TAG = "CameraXDemo"
    }
}