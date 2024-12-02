package Day02

import println
import readInput

fun main() {
    fun countErrors(report: List<Int>, increasing: Boolean): Int {
        var errors = 0
        for (i in report.indices) {
            if (i > 0 && increasing && report[i] !in report[i - 1] + 1..report[i - 1] + 3) errors++
            else if (i > 0 && !increasing && report[i] !in report[i - 1] - 3..report[i - 1] - 1) errors++
        }
        return errors
    }

    fun isSafe(reports: List<Int>): Boolean {
        return countErrors(reports, true) == 0 || countErrors(reports, false) == 0
    }

    fun part1(input: List<String>): Int {
        return input.map { it.split(" ").map { it.toInt() } }.count { isSafe(it) }
    }

    fun part2(input: List<String>): Int {
        fun isSafeLoose(reports: List<Int>): Boolean {
            for (i in reports.indices) {
                val newReports = reports.toMutableList()
                newReports.removeAt(i)
                if (isSafe(newReports)) return true
            }
            return false
        }

        return input.map { it.split(" ").map { it.toInt() } }.count { isSafeLoose(it) }
    }

    val testInput = readInput(2, true)
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput(2)
    part1(input).println()
    part2(input).println()
}