package chess

fun main() {
    val gameManager = GameManager()
    val prompter = Prompter()
    prompter.printTitle()
    val players = prompter.getPlayersNames()
    val playerOne = players[0]
    val playerTwo = players[1]
    var activePlayer: Player = playerOne
    gameManager.currentPlayer = activePlayer
    gameManager.board.printBoard()
    prompter.playersMove(activePlayer)
    var command = prompter.askForInput()

    while (command != "exit") {
        activePlayer.move(command)
        if (gameManager.executeMove(command)) {
            if (activePlayer.played) {

                if (WinnerChecker.checkLastRows(gameManager.matrixBoard) ||
                    WinnerChecker.enemyGone(gameManager.matrixBoard)
                ) {
                    prompter.win(activePlayer.playingColor.fullName)
                    break
                }
                activePlayer = if (activePlayer == playerOne) {
                    playerTwo
                } else {
                    playerOne
                }
            } else {
                prompter.invalidMove()
            }
            gameManager.currentPlayer = activePlayer
        }
        if (WinnerChecker.checkIfStalemate(
                gameManager.matrixBoard,
                gameManager.figurePlaying!!
            )
        ) {
            prompter.stalemate()
            break
        }
        prompter.playersMove(activePlayer)
        command = prompter.askForInput()
    }

    prompter.exitProgram()

}