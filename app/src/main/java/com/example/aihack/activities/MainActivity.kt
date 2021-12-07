package com.example.aihack.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aihack.CameraHelper
import com.example.aihack.databinding.ActivityMainBinding
import com.example.aihack.fragments.MainFragment

class MainActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return MainFragment()
    }

}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cameraHelper: CameraHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        cameraHelper = CameraHelper(
            owner = this,
            context = this.applicationContext,
            viewFinder = binding.cameraView,
            onResult = ::onResult
        )

        cameraHelper.start()
    }

    private fun onResult(result: String) {
        Log.d(TAG, "Result is $result")
        binding.textResult.text = result
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        const val TAG = "CameraXDemo"
    }
}