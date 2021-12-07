package com.example.aihack.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.aihack.fragments.TestFragment.Companion.TAG
import com.google.mlkit.vision.face.FaceContour
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraHelper(
    private val owner: FragmentActivity,
    private val context: Context,
    private val viewFinder: PreviewView,
    private val overlay: GraphicOverlay,
    private val onResult: (result: String) -> Unit
) {

    private var cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    @SuppressLint("UnsafeOptInUsageError")
    fun start() {
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(owner, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    fun stop() {
        cameraExecutor.shutdown()
    }

    @androidx.camera.core.ExperimentalGetImage
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.surfaceProvider)
                }
            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also { it.setAnalyzer(cameraExecutor, framesAnalyzer()) }
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    owner, cameraSelector, preview, imageAnalyzer
                )
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(context))
    }

    private fun framesAnalyzer(): ImageAnalysis.Analyzer {
        return FaceContourDetectionProcessor(overlay)
    }

    @SuppressLint("UnsafeOptInUsageError")
    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    context,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    context, permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    companion object {
        const val REQUEST_CODE_PERMISSIONS = 42
        val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}