package chess

fun main() {

    val board = Board()
    val prompter = Prompter()
    prompter.printTitle()
    val players = prompter.getPlayersNames()
    val playerOne = players[0]
    val playerTwo = players[1]
    var activePlayer: Player = playerOne
    board.currentPlayer = activePlayer
    board.printBoard()
    val matrixBoard = board.createMatrixBoard()

    prompter.playersMove(activePlayer)
    var command = prompter.askForInput()

    while (command != "exit") {
        activePlayer.move(command)
        if(board.executeMove(command)) {
            if (activePlayer.played) {
                activePlayer = if (activePlayer == playerOne) {
                    playerTwo
                } else {
                    playerOne
                }
            } else {
                prompter.invalidMove()
            }
            board.currentPlayer = activePlayer
        }
        prompter.playersMove(activePlayer)
        command = prompter.askForInput()
    }

    prompter.exitProgram()

}