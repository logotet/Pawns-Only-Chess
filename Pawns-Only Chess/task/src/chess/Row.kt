package chess

enum class Row(val printableVal: Int, val matrixVal: Int) {
    ROW_1(1, 7),
    ROW_2(2, 6),
    ROW_3(3, 5),
    ROW_4(4, 4),
    ROW_5(5, 3),
    ROW_6(6, 2),
    ROW_7(7, 1),
    ROW_8(8, 0);

    companion object {
        fun getMatrixRow(pritableValue: String): Int {
            for (r in values()) {
                if (r.printableVal == pritableValue.toInt()) {
                    return r.matrixVal
                }
            }
            return -1
        }

        fun getPrintableRow(matrixValue: Int): String {
            for (r in values()) {
                if (r.matrixVal == matrixValue) {
                    return r.printableVal.toString()
                }
            }
            return "-1"
        }
    }
}