package Day01

import println
import readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val fst = input.map { it.split("   ")[0].toInt() }.sorted()
        val snd = input.map { it.split("   ")[1].toInt() }.sorted()
        return fst.mapIndexed { index, i -> i to snd[index] }.sumOf { abs(it.first - it.second) }
    }

    fun part2(input: List<String>): Int {
        val fst = input.map { it.split("   ")[0].toInt() }
        val snd = input.map { it.split("   ")[1].toInt() }.groupingBy { it }.eachCount()
        return fst.sumOf { it * (snd[it] ?: 0) }
    }

    val testInput = readInput(1, true)
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readInput(1)
    part1(input).println()
    part2(input).println()
}