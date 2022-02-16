package chess

fun main() {
    val gameManager = GameManager()
    Prompter.printTitle()
    val players = Prompter.getPlayersNames()
    val playerOne = players[0]
    val playerTwo = players[1]
    var activePlayer: Player = playerOne
    gameManager.playingColor = activePlayer.playingColor
    gameManager.board.printBoard()
    Prompter.playersMove(activePlayer)
    var command = Prompter.askForInput()

    while (command != "exit") {
        if (!CommandReader.checkMoveFormat(command)) {
            Prompter.invalidMove()
            Prompter.playersMove(activePlayer)
            command = Prompter.askForInput()
            continue
        }
        if (gameManager.executeMove(command)) {
            if (WinnerChecker.checkLastRows(gameManager.matrixBoard) ||
                WinnerChecker.enemyGone(gameManager.matrixBoard)
            ) {
                Prompter.win(activePlayer.playingColor.fullName)
                break
            }
            if (WinnerChecker.checkIfStalemate(
                    gameManager.matrixBoard,
                    gameManager.figurePlaying
                )
            ) {
                Prompter.stalemate()
                break
            }
            activePlayer = if (activePlayer == playerOne) {
                playerTwo
            } else {
                playerOne
            }
            gameManager.playingColor = activePlayer.playingColor
        }

        Prompter.playersMove(activePlayer)
        command = Prompter.askForInput()
    }

    Prompter.exitProgram()

}