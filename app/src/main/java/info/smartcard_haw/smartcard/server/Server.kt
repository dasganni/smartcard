package info.smartcard_haw.smartcard

import java.io.IOException
import java.net.ServerSocket
import java.net.Socket
import java.util.HashMap

class Server {

    //Server
    private val connections = HashMap<Socket, ReceiveMsgThread>()
    internal val port: Int = 8060
	
	fun run(){
        		
        try {
            //Das ServerSocket wird an den Port gebunden
            //�ber das ServerSocket k�nnen sich 100 Clients verbinden
            val serverSocket = ServerSocket(port, 5)

            //wenn sich 100 Clients mit dem Server verbunden haben,
            //soll der Server beendet werden
            for (i in 0..4) {

                println("Waiting for Client to connect")
                //warte darauf, bis sich ein Client verbindet
                //wenn sich ein Client verbunden hat, kann der Server mit Hilfe des Sockets
                //mit dem Client kommunizieren
                val socket = serverSocket.accept()
                println("Client is connected: " + socket.inetAddress.hostAddress)

                //erstelle f�r jeden verbundenen Client einen Thread,
                //der darauf horcht, ob der jeweilige Client eine Nachricht schickt.
                //wird eine Nachricht empfangen,
                //wird die Nachricht an alle anderen User weiter geschickt
                val thread = ReceiveMsgThread(socket, connections)
                //alle Verbindungen werden in einer HashMap gespeichert
                connections[socket] = thread
                thread.start()

            }

            //beende das Serversocket
            serverSocket.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


}
