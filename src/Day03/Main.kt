package Day03

import println
import readInput

fun main() {
    val regex = """mul\((\d+),(\d+)\)""".toRegex()

    fun part1(input: List<String>): Long {
        return input.sumOf { input -> regex.findAll(input).sumOf { it.groupValues[1].toLong() * it.groupValues[2].toLong() } }
    }

    fun part2(input: List<String>): Long {
        val inputStr = input.reduce(String::plus)
        fun isEnabled(index: Int): Boolean {
            return inputStr.substring(0, index).lastIndexOf("do()") > inputStr.substring(0, index).lastIndexOf("don't()") || inputStr.substring(0, index).lastIndexOf("don't()") == -1
        }
        return regex.findAll(inputStr).filter { isEnabled(it.range.first) }.sumOf { it.groupValues[1].toLong() * it.groupValues[2].toLong() }
    }

    val testInput = readInput(3, true)
    check(part1(testInput) == 161L)
    check(part2(testInput) == 48L)

    val input = readInput(3)
    part1(input).println()
    part2(input).println()
}