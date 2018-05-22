package info.smartcard_haw.smartcard

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.*
import android.widget.TableRow.LayoutParams
import kotlinx.android.synthetic.main.activity_lobby.*
import android.widget.TableLayout



class lobbyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)

        setTitle("Lobby")


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







        //declare views

        var scanViewBtn = findViewById<Button>(R.id.scanViewBtn)
        var waitScanBtn = findViewById<Button>(R.id.waitScanBtn)
        var codeScannedBtn = findViewById<Button>(R.id.enterViewBtn)
        var showViewBtn = findViewById<Button>(R.id.showViewBtn)
        var internalInfoTextView = findViewById<TextView>(R.id.internalInfoTextView)
        var placeholderScannedBtn = findViewById<Button>(R.id.placeholderScannedBtn)
        var toEnterTextView = findViewById<TextView>(R.id.toEnterTextView)
        var scanTextView = findViewById<TextView>(R.id.scanTextView)
        var answerTextBox = findViewById<EditText>(R.id.answerTextBox)
        var answerHereTextView = findViewById<TextView>(R.id.answerHereTextView)
        var placeholderAllAnswerSendBtn = findViewById<Button>(R.id.sendAnswerBtn)
        var waitInfoTextView = findViewById<TextView>(R.id.waitInfoTextView)
        var placeholderWaitingFinishedBtn = findViewById<Button>(R.id.placeholderWaitingFinishedBtn)
        var tableTL = findViewById<TableLayout>(R.id.tableTL)
        var answersTextView = findViewById<TextView>(R.id.answersTextView)



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

        //tableView (Tabelle)
        tableTL.visibility = View.GONE
        answersTextView.visibility = View.GONE






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

    fun showTable(view: View) {
        hideAll()
        tableTL.visibility = View.VISIBLE
        answersTextView.visibility = View.VISIBLE

    }

}

