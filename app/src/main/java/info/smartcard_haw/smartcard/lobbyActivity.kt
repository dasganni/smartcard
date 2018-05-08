package info.smartcard_haw.smartcard

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_lobby.*

class lobbyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)


        //declare views

        var scanQRCodeBtn = findViewById<Button>(R.id.scanQRCodeBtn)
        var waitScanBtn = findViewById<Button>(R.id.waitScanBtn)
        var codeScannedBtn = findViewById<Button>(R.id.codeScannedBtn)
        var ergebnisBtn = findViewById<Button>(R.id.ergebnisBtn)
        var internalInfoTextView = findViewById<TextView>(R.id.internalInfoTextView)
        var placeholderScannedBtn = findViewById<Button>(R.id.placeholderScannedBtn)
        var toEnterTextView = findViewById<TextView>(R.id.toEnterTextView)
        var scanTextView = findViewById<TextView>(R.id.scanTextView)





        //only show the start of the lobby

        showOnlyLobbyStart()

    }

    //function to hide all views and then show specific views with another function

    fun hideAll(){

        //startview
        scanQRCodeBtn.visibility = View.GONE
        waitScanBtn.visibility = View.GONE
        codeScannedBtn.visibility = View.GONE
        ergebnisBtn.visibility = View.GONE
        internalInfoTextView.visibility = View.GONE

        //scanview
        placeholderScannedBtn.visibility = View.GONE
        scanTextView.visibility = View.GONE

        //otherview
        toEnterTextView.visibility = View.GONE


    }

    fun showOnlyLobbyStart(){
        hideAll()

        scanQRCodeBtn.visibility = View.VISIBLE
        waitScanBtn.visibility = View.VISIBLE
        codeScannedBtn.visibility = View.VISIBLE
        ergebnisBtn.visibility = View.VISIBLE
        internalInfoTextView.visibility = View.VISIBLE


    }

    fun showScanCard(view: View){
        hideAll()

        placeholderScannedBtn.visibility = View.VISIBLE
        scanTextView.visibility = View.VISIBLE

    }


}

