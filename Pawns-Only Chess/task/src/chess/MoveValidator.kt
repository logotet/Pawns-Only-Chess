package chess

import kotlin.math.abs

class MoveValidator {
    fun isPlayableFigure(coords: Coordinates, figurePlaying: Figure?, currentPlayer: Player): Boolean {
        return if (figurePlaying == null || figurePlaying.color != currentPlayer.playingColor.letter) {
            Prompter.noAvlbFigure(
                currentPlayer.playingColor.fullName, Column.getPrintableColumn(coords.colFrom) +
                        Row.getPrintableRow(coords.rowFrom)
            )
            false
        } else {
            true
        }
    }

    fun isValidPawnMove(coordinates: Coordinates, pawn: Pawn?): Boolean {
        return if (coordinates.colFrom != coordinates.colTo) {
            false
        } else if (isMoveBackwards(coordinates, pawn)) {
            false
        } else if (coordinates.colTo > 7 || coordinates.rowTo > 7) {
            false
        } else if (abs(coordinates.rowFrom - coordinates.rowTo) == 1) {
            pawn!!.enPassantAvlb = false
            true
        } else if (abs(coordinates.rowFrom - coordinates.rowTo) == 2 && pawn!!.firstMove) {
            pawn.enPassantAvlb = true
            true
        } else {
            false
        }
    }

    private fun isMoveBackwards(coordinates: Coordinates, pawn: Pawn?): Boolean {
        return if (pawn!!.pawnColor.trim() == Color.WHITE.letter.trim() && (coordinates.rowFrom - coordinates.rowTo) < 0) {
            true
        } else pawn.pawnColor.trim() == Color.BLACK.letter.trim() && (coordinates.rowFrom - coordinates.rowTo) > 0
    }

    fun isValidPawnCapture(coordinates: Coordinates, pawn: Pawn?) =
        abs(coordinates.rowFrom - coordinates.rowTo) == 1 &&
                abs(coordinates.colFrom - coordinates.colTo) == 1 &&
                !isMoveBackwards(coordinates, pawn)

    fun checkCommand(pawn: Pawn?, coordinates: Coordinates): String {
        if (isValidPawnMove(coordinates, pawn)) {
            return "move"
        }
        if (isValidPawnCapture(coordinates, pawn)) {
            return "capture"
        }
        return ""
    }


}