package chess

class WinnerChecker {
    companion object {
        fun enemyGone(board: MutableList<MutableList<Figure>>): Boolean {
            var blacks = 0
            var whites = 0
            for (row in board){
                for (piece in row){
                    if(piece.color.trim() == Color.WHITE.letter.trim()){
                        whites++
                    }
                    if(piece.color.trim() == Color.BLACK.letter.trim()){
                        blacks++
                    }
                }
            }
            if(blacks == 0 || whites == 0){
                return true
            }
            return false
        }

        fun checkLastRow(board: MutableList<MutableList<Figure>>):Boolean{
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


        fun staleMateCheck(){
            TODO()
        }

    }
}