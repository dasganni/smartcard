package info.smartcard_haw.smartcard

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import core.Server

class createActivity : AppCompatActivity() {

    var client : GameClient? = null
    var server : Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        var server = Thread(Server(1))
        server.start()
        this.server = server
        System.out.println(Utils.getIpAdress())
        //QR Code aus IP generieren
        client = GameClient(Utils.getIpAdress())
        client?.start()
        client?.outB?.put(Message(GameState.START, "name"))
    }

    fun everybodyIn(view: View){
        System.out.println("Start InterruptServer")
        server?.interrupt()
        System.out.println("InterruptServer done")
        val everybodyInIntent = Intent(this, lobbyActivity::class.java)
        startActivity(everybodyInIntent)
    }
}