package info.smartcard_haw.smartcard

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.io.IOException


class joinActivity : AppCompatActivity() {

    var barCodeString : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        val surfaceView : SurfaceView = findViewById(R.id.cameraPreview)


        var barcodeDetector = BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build()

        var cameraSource = CameraSource.Builder(this, barcodeDetector)
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

                    //Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                    //vibrator.vibrate(1);
                    barCodeString = qrCodes.valueAt(0).displayValue

                    var scannedIntent = Intent(applicationContext, lobbyActivity::class.java)
                    scannedIntent.putExtra("barCodeStringExtra", barCodeString)
                    startActivity(scannedIntent)


                }

            }
        })



    }
}
