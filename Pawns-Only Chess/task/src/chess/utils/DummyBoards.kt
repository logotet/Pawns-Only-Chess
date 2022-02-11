package chess.utils

import chess.EmptyCell
import chess.Figure
import chess.Pawn

class DummyBoards {
    companion object{
        fun createMatrixBoardStalemateCapture(): MutableList<MutableList<Figure>> {
            return mutableListOf(
                MutableList(8) { EmptyCell() },

                MutableList(8) { EmptyCell() },
                MutableList(8) { EmptyCell() },
                MutableList(8) { Pawn("B") },
                mutableListOf(
                    Pawn("W"), Pawn("W"), Pawn("W"), Pawn("W"),
                    Pawn("W"), Pawn("W"), Pawn("W"), EmptyCell()
                ),
                mutableListOf(
                    EmptyCell(), EmptyCell(), EmptyCell(), EmptyCell(),
                    EmptyCell(), EmptyCell(), EmptyCell(), Pawn("W")
                ),
                MutableList(8) { EmptyCell() },
                MutableList(8) { EmptyCell() },
            )
        }

        fun createMatrixBoardEnpassant(): MutableList<MutableList<Figure>> {
            return mutableListOf(
                MutableList(8) { EmptyCell() },
                mutableListOf(
                    EmptyCell(), EmptyCell(), EmptyCell(), EmptyCell(),
                    EmptyCell(), EmptyCell(), EmptyCell(), Pawn("B")
                ),
                mutableListOf(
                    EmptyCell(), EmptyCell(), EmptyCell(), EmptyCell(),
                    EmptyCell(), EmptyCell(), Pawn("B"), Pawn("W")
                ),
                MutableList(8) { EmptyCell() },
                MutableList(8) { EmptyCell() },
                MutableList(8) { EmptyCell() },
                MutableList(8) { Pawn("W") },
                MutableList(8) { EmptyCell() },
            )
        }

        fun createMatrixBoardWin(): MutableList<MutableList<Figure>> {
            return mutableListOf(
                MutableList(8) { EmptyCell() },
                mutableListOf(
                    EmptyCell(), Pawn("W"), EmptyCell(), EmptyCell(),
                    EmptyCell(), EmptyCell(), EmptyCell(), Pawn("B")
                ),
                mutableListOf(
                    EmptyCell(), EmptyCell(), EmptyCell(), EmptyCell(),
                    EmptyCell(), EmptyCell(), Pawn("W"), Pawn("W")
                ),
                MutableList(8) { EmptyCell() },
                MutableList(8) { EmptyCell() },
                MutableList(8) { EmptyCell() },
                mutableListOf(
                    EmptyCell(), EmptyCell(), EmptyCell(), EmptyCell(),
                    EmptyCell(), EmptyCell(), Pawn("W"), Pawn("W")
                ),
                MutableList(8) { EmptyCell() },
            )
        }

        fun createMatrixBoardStalemate(): MutableList<MutableList<Figure>> {
            return mutableListOf(
                MutableList(8) { EmptyCell() },
                mutableListOf(
                    EmptyCell(), EmptyCell(), EmptyCell(), EmptyCell(),
                    EmptyCell(), EmptyCell(), Pawn("B"), Pawn("B")
                ),
                MutableList(8) { EmptyCell() },
                mutableListOf(
                    EmptyCell(), EmptyCell(), EmptyCell(), EmptyCell(),
                    EmptyCell(), EmptyCell(), Pawn("B"), EmptyCell()
                ),
                mutableListOf(
                    EmptyCell(), EmptyCell(), EmptyCell(), EmptyCell(),
                    EmptyCell(), EmptyCell(), EmptyCell(), Pawn("W")
                ),
                MutableList(8) { EmptyCell() },
                MutableList(8) { EmptyCell() },
                MutableList(8) { EmptyCell() },
            )
        }
    }
}