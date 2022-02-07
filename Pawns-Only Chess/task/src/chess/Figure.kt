package chess

import kotlin.math.abs

abstract class Figure(val color: String) {
    abstract fun move(coordinates: Coordinates): Boolean
}

data class EmptyFigure(val emptyColor: String = " ") : Figure(emptyColor) {
    override fun move(coordinates: Coordinates): Boolean {
        TODO("Not yet implemented")
    }
}

data class Pawn(val pawnColor: String, val name: String = " P ") : Figure(pawnColor) {
    var firstMove = true
    var secondMove = false
    var enPassantAvlb = false

    override fun move(coordinates: Coordinates): Boolean {
//        if(!firstMove &&abs(coordinates.rowFrom - coordinates.rowTo) == 1 &&
//            coordinates.colFrom != coordinates.colTo && !isMoveBackwards(coordinates)
//                ){
//            return true
//        }
//        else if(firstMove &&
//            abs(coordinates.rowFrom - coordinates.rowTo) == 1 ||
//            abs(coordinates.rowFrom - coordinates.rowTo) == 2 &&
//            coordinates.colFrom != coordinates.colTo && !isMoveBackwards(coordinates)
//        ){
//            return true
//        }else{
//            return false
//        }
        return if (coordinates.colFrom != coordinates.colTo) {
            false
        } else if (isMoveBackwards(coordinates)) {
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

