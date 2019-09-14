package com.example.easywin

import android.Manifest
import android.animation.ValueAnimator
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import kotlinx.android.synthetic.main.qr_activity.*
import org.json.JSONObject

class QrActivity : AppCompatActivity() {
    companion object {
        const val PERMISSIONS_REQUEST_CODE = 128
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qr_activity)

        if (!isCameraGranted()) {
            Log.d("camera", "denied")
            requestCamera()
        } else {
            Log.d("camera", "granted")
            updateCamera()
        }
    }

    private fun updateCamera() {
        textureView.post {
            startCamera()
        }
    }

    enum class State {
        UPDATING,
        NONE,
        SHOWED
    }

    private var currentState = State.NONE

    private var currentAddress: String? = null

    private fun updateState(json: JSONObject) {
        val address = json.optString("address")
        if (currentAddress == null) {
            currentAddress = address
        }
        if (currentAddress != null && currentState == State.NONE) {
            currentState = State.UPDATING

            actionButton.visibility = View.VISIBLE

            val animator = ValueAnimator.ofFloat(0f, 1f)
            animator.duration = 300

            animator.addUpdateListener {
                val fraction = it.animatedFraction
                actionButton.scaleX = (.75f + .25f * fraction)
                actionButton.scaleY = (.75f + .25f * fraction)
                actionButton.translationY = (200f * (1 - fraction))
                actionButton.alpha = fraction
            }

            animator.start()
        }
    }

    private fun startCamera() {
        val previewConfig = PreviewConfig.Builder()
            .setLensFacing(CameraX.LensFacing.BACK)
            .setTargetAspectRatio(Rational(anchor.width, anchor.height))
            .build()

        val preview = Preview(previewConfig)

        preview.setOnPreviewOutputUpdateListener {
            textureView.surfaceTexture = it.surfaceTexture
        }

        val imageAnalysisConfig = ImageAnalysisConfig.Builder()
            .build()
        val imageAnalysis = ImageAnalysis(imageAnalysisConfig)

        imageAnalysis.analyzer = QrCodeAnalyzer(object : QrCodeDetectedListener {
            override fun onDetected(qrCodes: List<FirebaseVisionBarcode>) {
                qrCodes.forEach {
                    updateState(JSONObject(it.rawValue.toString()))
                }
            }
        })

        CameraX.bindToLifecycle(this, preview, imageAnalysis)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                updateCamera()
            }
        }
    }

    private fun requestCamera() {
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.M)
            return
        ActivityCompat.requestPermissions(this, listOf(Manifest.permission.CAMERA).toTypedArray(), PERMISSIONS_REQUEST_CODE)
    }

    private fun isCameraGranted(): Boolean {
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.M)
            return true
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }

    interface QrCodeDetectedListener {
        fun onDetected(qrCodes: List<FirebaseVisionBarcode>)
    }

    class QrCodeAnalyzer(private val listener: QrCodeDetectedListener) : ImageAnalysis.Analyzer {
        override fun analyze(image: ImageProxy, rotationDegrees: Int) {
            val options = FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_QR_CODE)
                .build()

            val detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)

            val rotation = rotationDegreesToFirebaseRotation(rotationDegrees)
            val visionImage = FirebaseVisionImage.fromMediaImage(image.image!!, rotation)

            detector.detectInImage(visionImage)
                .addOnSuccessListener {
                    listener.onDetected(it)
                }
                .addOnFailureListener {
                    Log.e("QrCodeAnalyzer", "something went wrong", it)
                }

        }

        private fun rotationDegreesToFirebaseRotation(rotationDegrees: Int): Int {
            return when (rotationDegrees) {
                0 -> FirebaseVisionImageMetadata.ROTATION_0
                90 -> FirebaseVisionImageMetadata.ROTATION_90
                180 -> FirebaseVisionImageMetadata.ROTATION_180
                270 -> FirebaseVisionImageMetadata.ROTATION_270
                else -> throw IllegalArgumentException("Not supported")
            }
        }

    }
}
