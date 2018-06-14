package info.smartcard_haw.smartcard


import java.io.IOException
import java.io.PrintWriter
import java.net.Socket
import java.util.*

class ReceiveMsgThread (
        //Socket des zu dem Thread geh�rigen Clients
        private val ownSocket: Socket,
        //Referenz auf die HashMap in der Serverklasse
        private val connections: HashMap<Socket, ReceiveMsgThread>) : Thread() {

    //boolean wert um den Thread sp�ter zu beenden
    private var isRunning = false

    //�ber den Scanner liest der Thread alle Nachrichten eines Clients
    private var `in`: Scanner? = null

    init {

        try {
            //erstell einen Scanner, um �ber diesen alle Nachrichten des Clients zu lesen
            `in` = Scanner(ownSocket.getInputStream())
            isRunning = true
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun run() {

        while (isRunning) {
            //warte auf eine Nachricht des Clients
            val msg = `in`!!.nextLine()
            println(msg)

            try {
                //schicke die Nachricht an alle anderen Clients
                //iteriere daf�r �ber die HashMap und
                //hol die keys (sockets) als Set
                val keys = connections.keys
                for (socket in keys) {
                    if (socket !== ownSocket) {
                        //schick die Nachricht �ber die Sockets der Clients
                        val pw = PrintWriter(socket.getOutputStream())
                        pw.println(msg)
                        pw.flush()
                        //TODO Testen, ob es funktioniert,
                        //wenn man ohne Verz�gerung den PrintWriter schlie�t
                        pw.close()
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: NoSuchElementException) {
                System.err.println("Die Verbindung zum Client wurde auf Grund eines Fehlers beendet")
                //wenn ein Fehler beim lesen auftaucht,
                //wird die Verbindung zum Client beendet
                connections.remove(ownSocket)
                stopThread()
            }

        }

    }

    fun stopThread() {
        isRunning = false
        `in`!!.close()
        try {
            ownSocket.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

}
