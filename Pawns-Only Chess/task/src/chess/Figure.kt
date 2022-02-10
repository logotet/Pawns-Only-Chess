package chess

import kotlin.math.abs

abstract class Figure(val color: String)

data class EmptyCell(val emptyColor: String = " ") : Figure(emptyColor)

data class Pawn(val pawnColor: String) : Figure(pawnColor) {
    var firstMove = true
    var secondMove = false
    var enPassantAvlb = false

    fun move(coordinates: Coordinates): Boolean {
        return if (coordinates.colFrom != coordinates.colTo) {
            false
        } else if (isMoveBackwards(coordinates)) {
            false
        } else if (coordinates.colTo > 7 || coordinates.rowTo > 7) {
            false
        } else if (abs(coordinates.rowFrom - coordinates.rowTo) == 1) {
            enPassantAvlb = false
            true
        } else if (abs(coordinates.rowFrom - coordinates.rowTo) == 2 && this.firstMove) {
            enPassantAvlb = true
            true
        } else {
            false
        }
    }

    fun isMoveBackwards(coordinates: Coordinates): Boolean {
        return if (pawnColor.trim() == Color.WHITE.letter.trim() && (coordinates.rowFrom - coordinates.rowTo) < 0) {
            true
        } else pawnColor.trim() == Color.BLACK.letter.trim() && (coordinates.rowFrom - coordinates.rowTo) > 0
    }

    fun capture(coordinates: Coordinates) = abs(coordinates.rowFrom - coordinates.rowTo) == 1 &&
            abs(coordinates.colFrom - coordinates.colTo) == 1 &&
            !isMoveBackwards(coordinates)

}

