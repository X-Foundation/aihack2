package com.example.aihack.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aihack.R
import com.example.aihack.databinding.TestFragmentBinding
import com.example.aihack.utils.CameraHelper

class TestFragment : Fragment() {
    private lateinit var binding: TestFragmentBinding
    private lateinit var cameraHelper: CameraHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TestFragmentBinding.inflate(LayoutInflater.from(requireActivity()))

        cameraHelper = CameraHelper(
            owner = requireActivity() as AppCompatActivity,
            context = requireContext(),
            viewFinder = binding.cameraView,
            onResult = ::onResult
        )

        cameraHelper.start()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.test_fragment, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraHelper.stop()
    }

    private fun onResult(result: String) {
        Log.d(TAG, "Result is $result")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        cameraHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        const val TAG = "CameraXDemo"
    }
}