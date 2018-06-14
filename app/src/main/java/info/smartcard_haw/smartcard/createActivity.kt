package info.smartcard_haw.smartcard

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class createActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

    }

    fun everybodyIn(view: View){

        val everybodyInIntent = Intent(this, lobbyActivity::class.java)
        startActivity(everybodyInIntent)
    }
}