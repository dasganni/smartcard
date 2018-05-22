package info.smartcard_haw.smartcard

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class joinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        setTitle("Beitreten")
    }

    fun scannedCode(view: View){
        val scannedIntent = Intent(this, lobbyActivity::class.java)
        startActivity(scannedIntent)
    }
}
