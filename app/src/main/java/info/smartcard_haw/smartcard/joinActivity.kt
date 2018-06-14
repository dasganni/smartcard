package info.smartcard_haw.smartcard

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.view.SurfaceView

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.SparseArray
import android.view.SurfaceHolder
import android.widget.Toast

import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector

import android.Manifest.permission.CAMERA
import android.os.Looper
import android.util.Log
import android.util.LogPrinter

import java.io.IOException
import info.smartcard_haw.smartcard.R.id.textView




class joinActivity : AppCompatActivity() {

    private val REQUEST_CAMERA = 1
    var barCodeString : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        val surfaceView : SurfaceView = findViewById(R.id.cameraPreview)

        val barcodeDetector = BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build()

        val cameraSource = CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)//.build();
                .setAutoFocusEnabled(true).build()


        surfaceView.getHolder().addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {

                if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    return

                }
                try {

                    cameraSource.start(holder)

                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }


            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {

                cameraSource.stop()

            }
        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {

            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {

                val qrCodes = detections.detectedItems

                if (qrCodes.size() != 0) {

                    barCodeString = qrCodes.valueAt(0).displayValue

                    //Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                    //vibrator.vibrate(1);

                    scannedCode()

                }

            }
        })

        val currentApiVersion = Build.VERSION.SDK_INT

        if (currentApiVersion >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(applicationContext, "Permission to Camera already granted!", Toast.LENGTH_LONG).show()
            } else {
                requestPermission()
            }
        }
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(applicationContext, CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(CAMERA), REQUEST_CAMERA)
    }


    //Permission für die Benutzung der Kamera vom User abfragen

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CAMERA -> if (grantResults.size > 0) {

                val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (cameraAccepted) {
                    Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_LONG).show()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(CAMERA)) {
                            showMessageOKCancel("You need to allow access to both the permissions",
                                    DialogInterface.OnClickListener { dialog, which ->
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(arrayOf(CAMERA),
                                                    REQUEST_CAMERA)
                                        }
                                    })
                            return
                        }
                    }
                }
            }
        }
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        android.support.v7.app.AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show()
    }




    fun scannedCode(){

        val scannedIntent = Intent(this, lobbyActivity::class.java)
            scannedIntent.putExtra("barCodeStringExtra", barCodeString)
        startActivity(scannedIntent)
    }
}
