package Day11

import println
import readInput

fun main() {
    fun getStones(memory: MutableMap<Pair<Long, Int>, Long>, stone: Long, steps: Int): Long {
        return if (steps == 0) {
            1L
        } else if (memory.contains(stone to steps)) {
            memory[stone to steps]!!
        } else {
            val result = if (stone == 0L) getStones(memory, 1, steps - 1)
            else if (stone.toString().length % 2 == 0) getStones(memory, stone.toString().substring(0, stone.toString().length / 2).toLong(), steps - 1) + getStones(memory, stone.toString().substring(stone.toString().length / 2).toLong(), steps - 1)
            else getStones(memory, stone * 2024, steps - 1)
            memory[stone to steps] = result
            println("Stone $stone, steps $steps, result $result")
            result
        }
    }

    fun solve(input: List<String>, steps: Int): Long {
        val memory = mutableMapOf<Pair<Long, Int>, Long>()
        val stones = input[0].split(' ').map { it.toLong() }.toMutableList()
        return stones.sumOf { getStones(memory, it, steps) }
    }

    val testInput = readInput(11, true)
    check(solve(testInput, 25) == 55312L)

    val input = readInput(11)
    solve(input, 25).println()
    solve(input, 75).println()
}