package chess

fun main() {
    val gameManager = GameManager()
    Prompter.printTitle()
    val players = Prompter.getPlayersNames()
    val playerOne = players[0]
    val playerTwo = players[1]
    var activePlayer: Player = playerOne
    gameManager.currentPlayer = activePlayer
    gameManager.board.printBoard()
    Prompter.playersMove(activePlayer)
    var command = Prompter.askForInput()

    while (command != "exit") {
        activePlayer.move(command)
        if (gameManager.executeMove(command)) {
            if (activePlayer.played) {

                if (WinnerChecker.checkLastRows(gameManager.matrixBoard) ||
                    WinnerChecker.enemyGone(gameManager.matrixBoard)
                ) {
                    Prompter.win(activePlayer.playingColor.fullName)
                    break
                }
                activePlayer = if (activePlayer == playerOne) {
                    playerTwo
                } else {
                    playerOne
                }
            } else {
                Prompter.invalidMove()
            }
            gameManager.currentPlayer = activePlayer
        }
        if (WinnerChecker.checkIfStalemate(
                gameManager.matrixBoard,
                gameManager.figurePlaying
            )
        ) {
            Prompter.stalemate()
            break
        }
        Prompter.playersMove(activePlayer)
        command = Prompter.askForInput()
    }

    Prompter.exitProgram()

}