package info.smartcard_haw.smartcard


import info.smartcard_haw.smartcard.core.GameState
import info.smartcard_haw.smartcard.core.Message
import info.smartcard_haw.smartcard.core.Player
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class GameServer(private var size: Int) : Runnable {

    private val ROUNDS = 4
    private val ROUND_LENGTH = 60

    private var state: GameState? = null
    private var round: Int = 0
    private val clients: MutableMap<String, Client>
    private val players: MutableMap<String, Player>
    private var serverSocket: ServerSocket? = null
    private val executorService: ExecutorService

    init {
        state = GameState.START
        round = 0
        clients = HashMap()
        players = HashMap()
        executorService = Executors.newFixedThreadPool(size)
        try {
            serverSocket = ServerSocket(PORT)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun run() {
        startLobby()
        wait(Integer.MAX_VALUE)
        size = clients.size
        state = GameState.ROUND
        while (true) {
            when (state) {
                GameState.ROUND -> {
                    ++round
                    startRound()
                    wait(60)
                    distributeResult()
                    if (round == ROUNDS)
                        state = GameState.DONE
                    wait(20)
                }
                GameState.DONE -> {
                }
            }//endGame();
            //Error Handling!?
        }
    }

    private fun startLobby() {
        for (i in 0 until size) {
            executorService.submit({
                try {
                    val client = serverSocket!!.accept()
                    gameInit(client)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            })
        }
    }

    private fun gameInit(client: Socket) {
        try {
            var `in` = ObjectInputStream(client.getInputStream())
            var out = ObjectOutputStream(client.getOutputStream())
            var input = `in`.readObject()
            if (input is Message) {
                if (input.state === GameState.START && input.message != null) {
                    // Error Handling für doppelt verteilte Namen!?
                    players[input.message!!] = Player(input.message!!)
                    clients[input.message!!] = Client(client, `in`, out)
                } else {
                    //Error Handling?!
                }
            } else {
                //Error Handling?!
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

    }

    private fun startRound() {
        for ((_, value) in clients) {
            try {
                value.out!!.writeObject(Message(GameState.ROUND, "Welches dieser Michprodukte ist das Festigste;Butter;Milch;Sahne"))
            } catch (e: IOException) {
                e.printStackTrace()
            }

            try {
                val input = value.`in`!!.readObject()
                if (input is Message) {
                    if (input.state === GameState.ROUND && input.message != null) {
                        // Punkte Veteilen

                    } else {
                        //Error Handling?!
                    }
                } else {
                    //Error Handling?!
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }

        }
    }

    private fun distributeResult() {
        for ((_, value) in clients) {
            try {
                value.out!!.writeObject(Message(GameState.ROUND_RESULT, players))
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    private fun wait(time: Int) {
        try {
            TimeUnit.SECONDS.sleep(time.toLong())
        } catch (e: InterruptedException) {
            println("Interrupted wait")
        }

    }

    private inner class Client(var socket: Socket?, var `in`: ObjectInputStream?, var out: ObjectOutputStream?)

    companion object {

        val PORT = 4444
    }

}

