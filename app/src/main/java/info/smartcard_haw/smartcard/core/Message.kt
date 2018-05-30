package info.smartcard_haw.smartcard.core

import java.io.Serializable

class Message @JvmOverloads constructor(var state: GameState?, var message: String? = null, var players: Map<String, Player>? = null) : Serializable {

    constructor(state: GameState, players: Map<String, Player>) : this(state, null, players) {}

}