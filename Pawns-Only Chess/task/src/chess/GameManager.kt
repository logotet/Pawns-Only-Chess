package chess

import java.lang.ClassCastException
import kotlin.math.abs

class GameManager {
    var board: Board = Board()
    var figurePlaying: Pawn? = null
    lateinit var playingColor: Color
    val matrixBoard = board.matrixBoard
    private val moveValidator = MoveValidator()
    lateinit var coords: Coordinates

    fun executeMove(): Boolean {
        figurePlaying = null
        pickCurrentFigure(coords.rowFrom, coords.colFrom)
        val validFigure = moveValidator.isPlayableFigure(coords, figurePlaying, playingColor)
        if (!validFigure) {
            return false
        }
        when (moveValidator.checkCommand(figurePlaying, coords)) {
            "move" -> {
                if (!isCellWithEnemy(coords.rowTo, coords.colTo)) {
                    executePawnMove()
                    return true
                }
            }
            "capture" -> {
                if (isCellWithEnemy(coords.rowTo, coords.colTo)) {
                    executeCapture()
                    return true
                } else if (isValidEnPassant()) {
                    executeEnPassant()
                    return true
                }
            }
            else -> {
                Prompter.invalidMove()
                return false
            }
        }
        Prompter.invalidMove()
        return false
    }

    private fun executeEnPassant() {
        matrixBoard[coords.rowTo][coords.colTo] = figurePlaying!!
        matrixBoard[coords.rowFrom][coords.colFrom] = EmptyCell()
        matrixBoard[coords.rowFrom][coords.colTo] = EmptyCell()
        board.printBoard()
    }

    private fun executeCapture() {
        matrixBoard[coords.rowTo][coords.colTo] = figurePlaying!!
        matrixBoard[coords.rowFrom][coords.colFrom] = EmptyCell()
        figurePlaying!!.secondMove = figurePlaying!!.firstMove
        figurePlaying!!.firstMove = false
        disableEnPassantOption()
        board.printBoard()
    }

    private fun executePawnMove() {
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

    private fun isCellEmpty(row: Int, col: Int) = matrixBoard[row][col] == EmptyCell()

    private fun isCellWithEnemy(row: Int, col: Int) =
        matrixBoard[row][col].color != figurePlaying?.color && !isCellEmpty(row, col)

    private fun pickCurrentFigure(row: Int, col: Int) {
        try {
            figurePlaying = matrixBoard[row][col] as Pawn
        } catch (_: ClassCastException) {
        }
    }

    private fun isValidEnPassant(): Boolean {
        return matrixBoard[coords.rowFrom][coords.colTo] is Pawn && (matrixBoard[coords.rowFrom][coords.colTo] as Pawn).enPassantAvlb && isCellEmpty(
            coords.rowTo,
            coords.colTo
        ) && coords.rowFrom == Row.getMatrixRow("5") || coords.rowFrom == Row.getMatrixRow("4")
    }

    private fun disableEnPassantOption() {
        disableEnPassantForRow("5")
        disableEnPassantForRow("4")
    }

    private fun disableEnPassantForRow(row: String) {
        for (piece in matrixBoard[Row.getMatrixRow(row)]) {
            if (piece is Pawn) {
                piece.enPassantAvlb = false
            }
        }
    }

}

