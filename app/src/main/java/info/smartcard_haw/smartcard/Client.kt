package info.smartcard_haw.smartcard

import java.io.PrintWriter
import java.net.Socket
import java.util.Scanner

class Client {

    init {
        //port ueber den der Client und der Server reden
        val port = 12345
        //ip addresse vom server
        val serverIP = ""

        try {
            println("Try to connect to server")
            //baue Verbindung zum Server mit der serverIP und dem port auf
            val socket = Socket(serverIP, port)

            println("Connected: " + socket.inetAddress.hostAddress)

            //erstelle und starte einen Thread, der dauerhaft horcht,
            //ob Nachrichten vom Server ankommen (anderen Clients)
            val inputThread = ReceiveMsgThread(socket)
            inputThread.start()

            //erstelle einen PrintWriter und
            //schreibe zuk�nftig die Nachrichten �ber das Socket
            //schreibe eine Nachricht an den Server/an alle anderen Clients
            val out = PrintWriter(socket.getOutputStream())
            val `in` = Scanner(System.`in`)
            var msg = `in`.nextLine()
            //Schreibt der User Quit, wird das Programm beendet
            while (msg != "Quit") {
                out.println(msg)
                out.flush()
                println("Die Nachricht $msg wurde gesendet")
                msg = `in`.nextLine()
            }

            //wenn das Programm beendet wird, soll der Thread beendet werden
            inputThread.stopThread()

            //Es m�ssen immer alle Reader/Writer/Sockets geschlossen werden
            `in`.close()
            out.close()
            socket.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
