package chess

import java.lang.reflect.Constructor

class Coordinates(
    val colFrom: Int,
    var rowFrom: Int,
    var colTo: Int,
    var rowTo: Int,
){
    fun getAllPossibleMoves() = mutableListOf<Coordinates>(
        Coordinates(this.colFrom, this.rowFrom, this.colFrom+1, this.rowFrom),
        Coordinates(this.colFrom, this.rowFrom, this.colFrom, this.rowFrom +1),
        Coordinates(this.colFrom, this.rowFrom, this.colFrom+1, this.rowFrom +1),
        Coordinates(this.colFrom, this.rowFrom, this.colFrom+1, this.rowFrom-1),
        Coordinates(this.colFrom, this.rowFrom, this.colFrom, this.rowFrom-1),
        Coordinates(this.colFrom, this.rowFrom, this.colFrom-1, this.rowFrom-1),
        Coordinates(this.colFrom, this.rowFrom, this.colFrom, this.rowFrom+2),
        Coordinates(this.colFrom, this.rowFrom, this.colFrom, this.rowFrom-2),
    )

}