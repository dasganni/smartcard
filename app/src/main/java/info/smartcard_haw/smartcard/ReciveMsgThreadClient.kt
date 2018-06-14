package info.smartcard_haw.smartcard

import java.io.IOException
import java.net.Socket
import java.util.*

class ReciveMsgThreadClient(socket: Socket) : Thread() {

    private var isRunning = false
    private var `in`: Scanner? = null

    init {

        try {
            //erstell ein Scanner, der Nachrichten �ber das Socket empf�ngt
            `in` = Scanner(socket.getInputStream())
            isRunning = true
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun run() {
        super.run()

        //warte auf Nachrichten und geb sie in der Konsole aus
        while (isRunning) {
            System.out.println(`in`!!.nextLine())

        }

    }

    fun stopThread() {
        //beende den Thread und schliesse den Scanner
        isRunning = false
        `in`!!.close()
    }

}
