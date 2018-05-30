package info.smartcard_haw.smartcard

import android.Manifest
import android.Manifest.permission.CAMERA
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    val REQUEST_CAMERA = 1
    val currentApiVersion = Build.VERSION.SDK_INT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "Hauptmenü"


        if (currentApiVersion >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(applicationContext, "Permission to Camera already granted!", Toast.LENGTH_LONG).show()
            } else {
                requestPermission()
            }
        }


    }

    fun clickCreate(view: View){
        val createIntent = Intent(this, createActivity::class.java)
        startActivity(createIntent)

    }

    fun clickJoin(view: View){
        val joinIntent = Intent(this, joinActivity::class.java)
        startActivity(joinIntent)

    }

    fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }


    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA)
    }


    //Permission für die Benutzung der Kamera vom User abfragen

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CAMERA -> if (grantResults.isNotEmpty()) {

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

    fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        android.support.v7.app.AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show()
    }




}
