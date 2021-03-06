package chess

class Board {

    private val emptySpace = "   "
    private val horLinePattern = "  " + "+---".repeat(8) + "+"
    private val fieldPattern = "|"
    private var bottomLine = getBottomLinePattern()
    val matrixBoard = createMatrixBoard()

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

    private fun getRow(line: Int): String {
        var row = ""
        for (element in matrixBoard[line]) {
            row = row.plus("$fieldPattern ${element.color} ")
        }
        row = row.plus(fieldPattern)
        return row
    }

    private fun createMatrixBoard(): MutableList<MutableList<Figure>> {
        return mutableListOf(
            MutableList(8) { EmptyCell() },
            MutableList(8) { Pawn("B") },
            MutableList(8) { EmptyCell() },
            MutableList(8) { EmptyCell() },
            MutableList(8) { EmptyCell() },
            MutableList(8) { EmptyCell() },
            MutableList(8) { Pawn("W") },
            MutableList(8) { EmptyCell() },
        )
    }

    private fun getBottomLinePattern(): String {
        var line = "$emptySpace "
        for (c in 'a'..'h') {
            line += c + emptySpace
        }
        return line
    }
}