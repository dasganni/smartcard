package info.smartcard_haw.smartcard

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.*
import android.widget.TableRow.LayoutParams
import kotlinx.android.synthetic.main.activity_lobby.*
import android.widget.TableLayout
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.io.IOException
import info.smartcard_haw.smartcard.R.id.textView




class lobbyActivity : AppCompatActivity() {

    private val REQUEST_CAMERA = 1
    var barCodeString : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)
        var lobbyCodeScan = intent.getStringExtra("barCodeStringExtra")

        setTitle("Lobby-ID: $lobbyCodeScan")

        //declare views

        var scanViewBtn = findViewById<Button>(R.id.scanViewBtn)
        var waitScanBtn = findViewById<Button>(R.id.waitScanBtn)
        var codeScannedBtn = findViewById<Button>(R.id.enterViewBtn)
        var showViewBtn = findViewById<Button>(R.id.showViewBtn)
        var internalInfoTextView = findViewById<TextView>(R.id.internalInfoTextView)
        var surfaceView = findViewById<SurfaceView>(R.id.surfaceView)
        var toEnterTextView = findViewById<TextView>(R.id.toEnterTextView)
        var scanTextView = findViewById<TextView>(R.id.scanTextView)
        var answerTextBox = findViewById<EditText>(R.id.answerTextBox)
        var answerHereTextView = findViewById<TextView>(R.id.answerHereTextView)
        var placeholderAllAnswerSendBtn = findViewById<Button>(R.id.sendAnswerBtn)
        var waitInfoTextView = findViewById<TextView>(R.id.waitInfoTextView)
        var placeholderWaitingFinishedBtn = findViewById<Button>(R.id.placeholderWaitingFinishedBtn)
        var tableTL = findViewById<TableLayout>(R.id.tableTL)
        var answersTextView = findViewById<TextView>(R.id.answersTextView)

        //Barcode-Scanner

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
                    cameraSource.stop()
                    barcodeDetector.release()
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



        //Platzhalter-Parameter bis Lobby fertig ist


        var players = ArrayList<player>()

        players.add(player("Marcus"))
        players.add(player("Lampe"))
        players.add(player("Patrick"))
        players.add(player("Daniel"))
        players.add(player("Ich"))

        var anzahlSpieler = players.size

        players[0].answer="Mario"
        players[1].answer="Luigi"
        players[2].answer="Peach"
        players[3].answer="Bowser"
        players[4].answer="Quark"

        players[0].speed=1.5
        players[1].speed=2.0
        players[2].speed=2.5
        players[3].speed=3.0
        players[4].speed=20.1


        //generate table for count of players

        var tableTextViewsOuter = ArrayList<ArrayList<TextView>>()
        var tableTextViewsInner = ArrayList<TextView>()


        for(i in 0..anzahlSpieler - 1) {

            print("test $i")
            // Create a new table row.
            val tableRow = TableRow(this)


            // Set new table row layout parameters.


            tableTL.addView(tableRow)

            // Add a TextView in the first column.
            val namesTextView = TextView(this)
            namesTextView.text = players[i].name
            namesTextView.layoutParams = TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f)
            tableRow.addView(namesTextView)
            tableTextViewsInner.add(namesTextView)

            // Add the answer text in the second column.
            val answerTextView = TextView(this)
            answerTextView.text = players[i].answer
            answerTextView.layoutParams = TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f)
            tableRow.addView(answerTextView)
            tableTextViewsInner.add(answerTextView)

            // Add the speed text in the third column.
            val speedTextView = TextView(this)
            speedTextView.text = players[i].speed.toString()
            speedTextView.layoutParams = TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f)
            tableRow.addView(speedTextView)
            tableTextViewsInner.add(speedTextView)

            tableTextViewsOuter.add(tableTextViewsInner)
        }

        //Nach öffnen der Activity nur improvisierten Start zeigen

        showOnlyLobbyStart()

    }

    //Prüfe Kamera-Rechte
    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    //Frage nach Rechte für Kamera
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA)
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
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                            showMessageOKCancel("You need to allow access to both the permissions",
                                    DialogInterface.OnClickListener { dialog, which ->
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(arrayOf(Manifest.permission.CAMERA),
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

    //Fenster für Rechte-Abfrage
    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        android.support.v7.app.AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show()
    }



    //QR-Code wurde gescannt
    fun scannedCode(){

        showEnterView()
    }



    //function to hide all views and then show specific views with another function

    fun hideAll(){

        //startview
        scanViewBtn.visibility = View.GONE
        waitScanBtn.visibility = View.GONE
        enterViewBtn.visibility = View.GONE
        showViewBtn.visibility = View.GONE
        internalInfoTextView.visibility = View.GONE

        //scanview (einlesen)
        surfaceView.visibility = View.GONE
        scanTextView.visibility = View.GONE

        //waitForScan (warten)
        waitInfoTextView.visibility = View.GONE
        placeholderWaitingFinishedBtn.visibility = View.GONE

        //enterView (eingeben)
        toEnterTextView.visibility = View.GONE
        answerTextBox.visibility = View.GONE
        answerHereTextView.visibility = View.GONE
        sendAnswerBtn.visibility = View.GONE

        //tableView (Tabelle)
        tableTL.visibility = View.GONE
        answersTextView.visibility = View.GONE


    }

    fun showOnlyLobbyStart(){
        hideAll()
        scanViewBtn.visibility = View.VISIBLE
        waitScanBtn.visibility = View.VISIBLE
        enterViewBtn.visibility = View.VISIBLE
        showViewBtn.visibility = View.VISIBLE
        internalInfoTextView.visibility = View.VISIBLE


    }

    fun showScanCard(view: View){
        hideAll()
        surfaceView.visibility = View.VISIBLE
        scanTextView.visibility = View.VISIBLE

    }

    fun showWaitView(view: View){
        hideAll()
        waitInfoTextView.visibility = View.VISIBLE
        placeholderWaitingFinishedBtn.visibility = View.VISIBLE

    }



    fun showEnterView(view: View){
        hideAll()
        toEnterTextView.visibility = View.VISIBLE
        answerTextBox.visibility = View.VISIBLE
        answerHereTextView.visibility = View.VISIBLE
        sendAnswerBtn.visibility = View.VISIBLE

    }

    fun showEnterView(){
        hideAll()
        toEnterTextView.post(Runnable {
            //Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            //vibrator.vibrate(1);
            toEnterTextView.text = "Yeah, du hast eine Karte gescannt, das steht drauf: $barCodeString"
        })
        toEnterTextView.visibility = View.VISIBLE
        answerTextBox.visibility = View.VISIBLE
        answerHereTextView.visibility = View.VISIBLE
        sendAnswerBtn.visibility = View.VISIBLE

    }

    fun showTable(view: View) {
        hideAll()
        tableTL.visibility = View.VISIBLE
        answersTextView.visibility = View.VISIBLE

    }

}

