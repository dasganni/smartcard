package info.smartcard_haw.smartcard

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_lobby.*

class lobbyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)


        //declare views

        var scanQRCodeBtn = findViewById<Button>(R.id.scanViewBtn)
        var waitScanBtn = findViewById<Button>(R.id.waitScanBtn)
        var codeScannedBtn = findViewById<Button>(R.id.enterViewBtn)
        var ergebnisBtn = findViewById<Button>(R.id.showViewBtn)
        var internalInfoTextView = findViewById<TextView>(R.id.internalInfoTextView)
        var placeholderScannedBtn = findViewById<Button>(R.id.placeholderScannedBtn)
        var toEnterTextView = findViewById<TextView>(R.id.toEnterTextView)
        var scanTextView = findViewById<TextView>(R.id.scanTextView)
        var answerTextBox = findViewById<EditText>(R.id.answerTextBox)
        var answerHereTextView = findViewById<TextView>(R.id.answerHereTextView)
        var placeholderAllAnswerSendBtn = findViewById<Button>(R.id.sendAnswerBtn)
        var waitInfoTextView = findViewById<TextView>(R.id.waitInfoTextView)
        var placeholderWaitingFinishedBtn = findViewById<Button>(R.id.placeholderWaitingFinishedBtn)


        //only show the start of the lobby

        showOnlyLobbyStart()

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
        placeholderScannedBtn.visibility = View.GONE
        scanTextView.visibility = View.GONE

        //waitForScan (warten)
        waitInfoTextView.visibility = View.GONE
        placeholderWaitingFinishedBtn.visibility = View.GONE

        //enterView (eingeben)
        toEnterTextView.visibility = View.GONE
        answerTextBox.visibility = View.GONE
        answerHereTextView.visibility = View.GONE
        sendAnswerBtn.visibility = View.GONE





        //showView(Einfüllen/Tabelle)


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
        placeholderScannedBtn.visibility = View.VISIBLE
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






}

