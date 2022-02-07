package chess

import kotlin.math.abs

class Board {
    val emptySpace = "   "
    val horLinePattern = "  " + "+---".repeat(8) + "+"
    val fieldPattern = "|"
    var bottomLine = getBottomLinePattern()
    val matrixBoard = createMatrixBoar22()
    var figurePlaying: Figure? = null
    var currentPlayer: Player? = null
    val prompter = Prompter()

    private fun getBottomLinePattern(): String {
        var line = "$emptySpace "
        for (c in 'a'..'h') {
            line += c + emptySpace
        }
        return line
    }

    fun printBoard() {
        for (i in 8 downTo 1) {
            println(horLinePattern)
            when (i) {
                8 -> println("$i ${getRow(0)}")
                7 -> println("$i ${getRow(1)}")
                6 -> println("$i ${getRow(2)}")
                5 -> println("$i ${getRow(3)}")
                4 -> println("$i ${getRow(4)}")
                3 -> println("$i ${getRow(5)}")
                2 -> println("$i ${getRow(6)}")
                1 -> println("$i ${getRow(7)}")
                else -> println("$i ${(fieldPattern + emptySpace).repeat(9)}")
            }
        }
        println(horLinePattern)
        println(bottomLine)
    }

    fun createMatrixBoard(): MutableList<MutableList<Figure>> {
        return mutableListOf(
            MutableList(8) { EmptyFigure() },
            MutableList(8) { Pawn("B") },
            MutableList(8) { EmptyFigure() },
            MutableList(8) { EmptyFigure() },
            MutableList(8) { EmptyFigure() },
            MutableList(8) { EmptyFigure() },
            MutableList(8) { Pawn("W") },
            MutableList(8) { EmptyFigure() },
        )
    }

    fun createMatrixBoar22(): MutableList<MutableList<Figure>> {
        return mutableListOf(
            MutableList(8) { EmptyFigure() },
            mutableListOf(EmptyFigure(), EmptyFigure(), EmptyFigure(), EmptyFigure(),
                EmptyFigure(),EmptyFigure(),EmptyFigure(),Pawn("B")),
            mutableListOf(EmptyFigure(), EmptyFigure(), EmptyFigure(), EmptyFigure(),
                EmptyFigure(),EmptyFigure(),Pawn("B"),Pawn("W")),
            MutableList(8) { EmptyFigure() },
            MutableList(8) { EmptyFigure() },
            MutableList(8) { EmptyFigure() },
            MutableList(8) { Pawn("W") },
            MutableList(8) { EmptyFigure() },
        )
    }

    fun getRow(line: Int): String {
        var row = ""
        for (element in matrixBoard[line]) {
            row = row.plus("$fieldPattern ${element.color} ")
        }
        row = row.plus(fieldPattern)
        return row
    }

    fun executeMove(fromTo: String = "c2c4"): Boolean {
        figurePlaying = null
        val coords = CommandReader.getCoordsFromCommand(fromTo)
        pickCurrentFigure(coords.rowFrom, coords.colFrom)
        val valid = checkIfMoveIsValid(coords)
        if (!valid) {
            return false
        }
        var command = CommandReader.checkCommand((figurePlaying as Pawn), coords)
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
        printBoard()
    }

    private fun executeCapture(coords: Coordinates) {
        matrixBoard[coords.rowTo][coords.colTo] = figurePlaying!!
        matrixBoard[coords.rowFrom][coords.colFrom] = EmptyFigure()
        (figurePlaying as Pawn).secondMove = (figurePlaying as Pawn).firstMove
        (figurePlaying as Pawn).firstMove = false
        disableEnPassantOption()
        printBoard()
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
        printBoard()
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
//        var totalAvlbEnP = 0
//        for (piece in matrixBoard[Row.getMatrixRow("5")]) {
//            if (piece is Pawn && (piece as Pawn).enPassantAvlb) {
//                totalAvlbEnP++
//            }
//        }
//        for (piece in matrixBoard[Row.getMatrixRow("4")]) {
//            if (piece is Pawn && (piece as Pawn).enPassantAvlb) {
//                totalAvlbEnP++
//            }
//        }
//        println(totalAvlbEnP)
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

//private fun executeEnPassant(coords: Coordinates) {
//    if((matrixBoard[coords.rowFrom][coords.colTo] as Pawn).enPassantAvlb) {
//        matrixBoard[coords.rowTo][coords.colTo] = figurePlaying!!
//        matrixBoard[coords.rowFrom][coords.colFrom] = EmptyFigure()
//        matrixBoard[coords.rowFrom][coords.colTo] = EmptyFigure()
//        printBoard()
//    }else{
//        prompter.invalidMove()
//    }
//}