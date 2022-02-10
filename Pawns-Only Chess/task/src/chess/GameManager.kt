package chess

import kotlin.math.abs

class GameManager {
    var board:Board = Board()
    var figurePlaying: Figure? = null
    var currentPlayer: Player? = null
    val prompter = Prompter()
    val matrixBoard = board.createMatrixBoar22()

    fun executeMove(fromTo: String = "c2c4"): Boolean {
        figurePlaying = null
        val coords = CommandReader.getCoordsFromCommand(fromTo)
        pickCurrentFigure(coords.rowFrom, coords.colFrom)
        val valid = checkIfMoveIsValid(coords)
        if (!valid) {
            return false
        }
        val command = CommandReader.checkCommand((figurePlaying as Pawn), coords)
        return when (command) {
            "move" -> {
                if (!isCellWithEnemy(coords.rowTo, coords.colTo)) {
                    executePawnMove(coords)
                    true
                } else {
                    prompter.invalidMove()
                    false
                }

            }
            "capture" -> {
                if (isCellWithEnemy(coords.rowTo, coords.colTo)) {
                    executeCapture(coords)
                    true
                } else if (matrixBoard[coords.rowFrom][coords.colTo] is Pawn && (matrixBoard[coords.rowFrom][coords.colTo] as Pawn).enPassantAvlb &&
                        isCellEmpty(coords.rowTo, coords.colTo) && coords.rowFrom == Row.getMatrixRow("5") ||
                    coords.rowFrom == Row.getMatrixRow("4")
                ) {
                    executeEnPassant(coords)
                    true
                } else {
                    prompter.invalidMove()
                    false
                }
            }
            else -> {
                prompter.invalidMove()
                false
            }
        }
    }

    private fun executeEnPassant(coords: Coordinates) {
        matrixBoard[coords.rowTo][coords.colTo] = figurePlaying!!
        matrixBoard[coords.rowFrom][coords.colFrom] = EmptyFigure()
        matrixBoard[coords.rowFrom][coords.colTo] = EmptyFigure()
        board.printBoard()
    }

    private fun executeCapture(coords: Coordinates) {
        matrixBoard[coords.rowTo][coords.colTo] = figurePlaying!!
        matrixBoard[coords.rowFrom][coords.colFrom] = EmptyFigure()
        (figurePlaying as Pawn).secondMove = (figurePlaying as Pawn).firstMove
        (figurePlaying as Pawn).firstMove = false
        disableEnPassantOption()
        board.printBoard()
    }

    private fun executePawnMove(coords: Coordinates) {
        matrixBoard[coords.rowTo][coords.colTo] = figurePlaying!!
        matrixBoard[coords.rowFrom][coords.colFrom] = EmptyFigure()
        (figurePlaying as Pawn).secondMove = (figurePlaying as Pawn).firstMove
        (figurePlaying as Pawn).firstMove = false
        disableEnPassantOption()
        if (abs(coords.rowFrom - coords.rowTo) == 2) {
            (figurePlaying as Pawn).enPassantAvlb = true
        }
        board.printBoard()
    }

    private fun checkIfMoveIsValid(coords: Coordinates): Boolean {
        return if (figurePlaying == null) {
            false
        } else if (figurePlaying !is Pawn) {
            prompter.noAvlbFigure(
                currentPlayer!!.playingColor.fullName, Column.getPrintableColumn(coords.colFrom) +
                        Row.getPrintableRow(coords.rowFrom)
            )
            Column.getPrintableColumn(coords.colFrom)
            false
        } else if (figurePlaying!!.color != currentPlayer!!.playingColor.letter) {
            prompter.noAvlbFigure(
                currentPlayer!!.playingColor.fullName, Column.getPrintableColumn(coords.colFrom) +
                        Row.getPrintableRow(coords.rowFrom)
            )
            Column.getPrintableColumn(coords.colFrom)
            false
        } else {
            true
        }
    }

    private fun isCellEmpty(row: Int, col: Int) = matrixBoard[row][col] == EmptyFigure()

    private fun isAllowedFigure(coords: Coordinates): Boolean {
        return if (isCellEmpty(coords.rowFrom, coords.colFrom) ||
            currentPlayer!!.playingColor.letter.trim() != figurePlaying!!.color.trim()
        ) {
            println(
                "No ${currentPlayer!!.playingColor.fullName} pawn at " +
                        "${Column.getPrintableColumn(coords.colFrom)}${Row.getPrintableRow(coords.rowFrom)}"
            )
            Column.getPrintableColumn(coords.colFrom)
            false
        } else {
            true
        }
    }

    fun isCellWithEnemy(row: Int, col: Int) =
        matrixBoard[row][col].color != figurePlaying?.color && !isCellEmpty(row, col)

    fun pickCurrentFigure(row: Int, col: Int) {
        figurePlaying = matrixBoard[row][col]
    }

    fun getFigure(row: Int, col: Int): Figure = matrixBoard[row][col]

    fun disableEnPassantOption() {
        disableEnpassantForRow("5")
        disableEnpassantForRow("4")
    }

    private fun disableEnpassantForRow(row: String) {
        for (piece in matrixBoard[Row.getMatrixRow(row)]) {
            if (piece is Pawn) {
                piece.enPassantAvlb = false
            }
        }
    }
}

