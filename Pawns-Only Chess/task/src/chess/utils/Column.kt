package chess

enum class Column(val printableVal: String, val matrixVal: Int) {
    COLUMN_1("a", 0),
    COLUMN_2("b", 1),
    COLUMN_3("c", 2),
    COLUMN_4("d", 3),
    COLUMN_5("e", 4),
    COLUMN_6("f", 5),
    COLUMN_7("g", 6),
    COLUMN_8("h", 7);

    companion object {
        fun getMatrixColumn(printableValue: String): Int {
            for (v in values()) {
                if (v.printableVal == printableValue) {
                    return v.matrixVal
                }
            }
            return -1
        }

        fun getPrintableColumn(matrixValue: Int): String {
            for (v in values()) {
                if (v.matrixVal == matrixValue) {
                    return v.printableVal
                }
            }
            return ""
        }
    }
}