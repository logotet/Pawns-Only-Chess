package chess

class WinnerChecker {
    companion object {
        private val moveValidator = MoveValidator()
        /*
        Checks if there are any enemies left on the board.
         */
        fun enemyGone(board: MutableList<MutableList<Figure>>): Boolean {
            var blacks = 0
            var whites = 0
            for (row in board) {
                for (piece in row) {
                    if (piece.color.trim() == Color.WHITE.letter.trim()) {
                        whites++
                    }
                    if (piece.color.trim() == Color.BLACK.letter.trim()) {
                        blacks++
                    }
                }
            }
            if (blacks == 0 || whites == 0) {
                return true
            }
            return false
        }

        /*
        Checks the last rows for enemy figure.
         */
        fun checkLastRows(board: MutableList<MutableList<Figure>>): Boolean {
            for (piece in board[0]) {
                if (piece is Pawn && piece.color.trim() == Color.WHITE.letter.trim()) {
                    return true
                }
            }
            for (piece in board[7]) {
                if (piece is Pawn && piece.color.trim() == Color.BLACK.letter.trim()) {
                    return true
                }
            }
            return false
        }

        /*
        Checks every piece on the board with all possible moves.
        False returns there is at least one possibility for a move.
        True means stalemate.
         */
        fun checkIfStalemate(board: MutableList<MutableList<Figure>>, pawn: Pawn?): Boolean {
            if(pawn == null){
                return false
            }
            for (row in board.indices) {
                for (col in board.indices) {
                    val piece = board[row][col]
                    if (piece !is EmptyCell && piece.color != pawn.color) {
                        val allPossibleMoves = getAllPossibleMoves(row, col)
                        for (coords in allPossibleMoves) {
                            if (moveValidator.isValidPawnMove(coords, (piece as Pawn)) &&
                                coords.colTo < 8 &&
                                coords.rowTo < 8 &&
                                board[coords.rowTo][coords.colTo] !is Pawn
                            ) {
                                return false
                            }
                            if (moveValidator.isValidPawnCapture(coords,piece)&&
                                coords.colTo < 8 &&
                                    coords.rowTo < 8
                            ) {
                                return false
                            }
                        }
                    }
                }
            }
            return true
        }

        private fun getAllPossibleMoves(row: Int, col: Int) = mutableListOf<Coordinates>(
            Coordinates(col, row, col + 1, row),
            Coordinates(col, row, col, row + 1),
            Coordinates(col, row, col + 1, row + 1),
            Coordinates(col, row, col + 1, row - 1),
            Coordinates(col, row, col, row - 1),
            Coordinates(col, row, col - 1, row - 1),
            Coordinates(col, row, col, row + 2),
            Coordinates(col, row, col, row - 2),
        )

    }
}
