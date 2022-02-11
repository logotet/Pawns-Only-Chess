package chess

import java.lang.ClassCastException
import kotlin.math.abs

class GameManager {
    var board: Board = Board()
    var figurePlaying: Pawn? = null
    var currentPlayer: Player? = null
    val prompter = Prompter()
    val matrixBoard = board.matrixBoard

    //TODO Refactor the move execution as now is too complex and the logice is scattered
    fun executeMove(fromTo: String): Boolean {
        figurePlaying = null
        val coords = CommandReader.getCoordsFromCommand(fromTo)
        pickCurrentFigure(coords.rowFrom, coords.colFrom)
        val valid = checkIfMoveIsValid(coords)
        if (!valid) {
            return false
        }
        val command = CommandReader.checkCommand(figurePlaying, coords)
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
        matrixBoard[coords.rowFrom][coords.colFrom] = EmptyCell()
        matrixBoard[coords.rowFrom][coords.colTo] = EmptyCell()
        board.printBoard()
    }

    private fun executeCapture(coords: Coordinates) {
        matrixBoard[coords.rowTo][coords.colTo] = figurePlaying!!
        matrixBoard[coords.rowFrom][coords.colFrom] = EmptyCell()
        figurePlaying!!.secondMove = figurePlaying!!.firstMove
        figurePlaying!!.firstMove = false
        disableEnPassantOption()
        board.printBoard()
    }

    private fun executePawnMove(coords: Coordinates) {
        matrixBoard[coords.rowTo][coords.colTo] = figurePlaying!!
        matrixBoard[coords.rowFrom][coords.colFrom] = EmptyCell()
        figurePlaying!!.secondMove = figurePlaying!!.firstMove
        figurePlaying!!.firstMove = false
        disableEnPassantOption()
        if (abs(coords.rowFrom - coords.rowTo) == 2) {
            figurePlaying!!.enPassantAvlb = true
        }
        board.printBoard()
    }

    private fun checkIfMoveIsValid(coords: Coordinates): Boolean {
        return if (figurePlaying == null) {
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

    private fun isCellEmpty(row: Int, col: Int) = matrixBoard[row][col] == EmptyCell()

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

    private fun isCellWithEnemy(row: Int, col: Int) =
        matrixBoard[row][col].color != figurePlaying?.color && !isCellEmpty(row, col)

    private fun pickCurrentFigure(row: Int, col: Int) {
        try {
            figurePlaying = matrixBoard[row][col] as Pawn
        } catch (e: ClassCastException) {
            prompter.noAvlbFigure(
                currentPlayer!!.playingColor.fullName, Column.getPrintableColumn(col) +
                        Row.getPrintableRow(row)
            )
            Column.getPrintableColumn(col)
        }

    }

    fun getFigure(row: Int, col: Int): Figure = matrixBoard[row][col]

    private fun disableEnPassantOption() {
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

