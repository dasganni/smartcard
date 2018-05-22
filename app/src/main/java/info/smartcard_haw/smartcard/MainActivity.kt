package info.smartcard_haw.smartcard

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun clickCreate(view: View){
        val createIntent = Intent(this, createActivity::class.java)
        startActivity(createIntent)

        setTitle("Hauptmenü")
    }

    fun clickJoin(view: View){
        val joinIntent = Intent(this, joinActivity::class.java)
        startActivity(joinIntent)
    }

}
