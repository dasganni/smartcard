package info.smartcard_haw.smartcard

import info.smartcard_haw.smartcard.core.Message
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket
import java.util.concurrent.*


class socketClient(private val ip: String) : Runnable {
    val executorService: ExecutorService
    val inB: BlockingQueue<Message> = LinkedBlockingQueue()
    val outB: BlockingQueue<Message> = LinkedBlockingQueue()

    init {
        executorService = Executors.newFixedThreadPool(2)
    }

    override fun run() {
        //Gamelogik implementieren mit Activity in Methoden auslagern
        val socket: Socket
        try {
            socket = Socket(ip, GameServer.PORT)
            executorService.submit({
                try {
                    ObjectOutputStream(socket.getOutputStream()).use({ out ->
                        while (true) {
                            try {
                                out.writeObject(outB.take())
                            } catch (e: InterruptedException) {
                                e.printStackTrace()
                            }

                        }
                    })
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            })
            executorService.submit({
                try {
                    ObjectInputStream(socket.getInputStream()).use({ out ->
                        while (true) {
                            val input = out.readObject()
                            if (input is Message) {
                                try {
                                    inB.put(input as Message)
                                } catch (e: InterruptedException) {
                                    e.printStackTrace()
                                }

                            } else {
                                //Error Handling?!
                            }
                        }
                    })
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }
            })
        } catch (e: IOException) {
            e.printStackTrace()
        }

        try {
            while (true) TimeUnit.DAYS.sleep(Long.MAX_VALUE)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }

    fun send(socket: Socket, m: Message) {

    }

    fun receive(socket: Socket): Message? {
        try {
            ObjectInputStream(socket.getInputStream()).use({ out ->
                val input = out.readObject()
                if (input is Message) {
                    return input as Message
                } else {
                    //Error Handling?!
                }
            })
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        return null
    }

}
