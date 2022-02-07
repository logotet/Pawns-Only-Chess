package chess

class Prompter {

    fun printTitle() {
        println("Pawns-Only Chess")
    }

    fun askForInput(): String {
        print("> ")
        return readLine()!!
    }

    fun getPlayersNames(): MutableList<Player> {
        println("First Player's name:")
        val firstPlayer = Player(askForInput(), Color.WHITE)
        println("Second Player's name:")
        val secondPlayer = Player(askForInput(), Color.BLACK)
        return mutableListOf(firstPlayer, secondPlayer)
    }

    fun playersMove(player: Player) {
        println("${player.name}'s turn:")
    }

    fun invalidMove() {
        println("Invalid Input")
    }

    fun noAvlbFigure(color: String, cell: String){
        println(
                "No $color pawn at " +
                        "$cell"
            )
    }

    fun exitProgram() {
        println("Bye!")
    }

}