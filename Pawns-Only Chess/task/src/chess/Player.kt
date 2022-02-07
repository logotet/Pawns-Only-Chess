package chess

class Player(val name: String, val playingColor: Color) {

    var played = false
    var canPlay = true

    fun move(fromTo: String) {
        played = CommandReader.checkMoveFormat(fromTo)
    }
}