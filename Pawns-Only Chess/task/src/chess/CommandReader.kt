package chess

class CommandReader {
    companion object {
        val movePattern = "[a-h][1-8][a-h][1-8]".toRegex()
        fun checkMoveFormat(move: String): Boolean = move.matches(movePattern)

        fun getCoordsFromCommand(command: String): Coordinates {
            return Coordinates(
                Column.getMatrixColumn(command[0].toString()),
                Row.getMatrixRow(command[1].toString()),
                Column.getMatrixColumn(command[2].toString()),
                Row.getMatrixRow(command[3].toString())
            )
        }

        fun checkCommand(pawn: Pawn, coordinates: Coordinates): String {
            if (pawn.move(coordinates)) {
                return "move"
            }
            if (pawn.capture(coordinates)) {
                return "capture"
            }
            return ""
        }
    }


}