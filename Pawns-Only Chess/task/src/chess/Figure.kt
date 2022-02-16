package chess

abstract class Figure(val color: String)

data class EmptyCell(val emptyColor: String = " ") : Figure(emptyColor)

data class Pawn(val pawnColor: String) : Figure(pawnColor) {
    var firstMove = true
    var secondMove = false
    var enPassantAvlb = false

}

