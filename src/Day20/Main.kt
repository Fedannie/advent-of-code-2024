package Day20

import allPairs
import at
import bfs
import bfsSteps
import isInsideStr
import next
import plus
import println
import readInput
import kotlin.math.abs
import kotlin.math.min

fun main() {
    fun solve(input: List<String>, cheatLength: Int, diff: Int): Int {
        var result = 0
        val forDebug = List(input.size) { MutableList(input[0].length) { 0 } }

        val road = allPairs(input.size, input[0].length).filter { input.at(it) != '#' }
        for (cheatStart in road) {
            for (di in 0 .. cheatLength) {
                for (dj in -(cheatLength - di) ..  cheatLength - di) {
                    val cheatEnd = cheatStart + (di to dj)
                    if (((di + abs(dj)) !in 1 .. cheatLength) || (di == 0 && dj <= 0) || (!input.isInsideStr(cheatEnd)) || (input.at(cheatEnd) == '#')) {
                        continue
                    }
                    val normalRoad = bfs(cheatStart, { it == cheatEnd }, { input.isInsideStr(it) && input.at(it) != '#' }, { next(it) })
                    if (normalRoad - diff >= di + abs(dj)) {
                        result++
                        forDebug[cheatStart.first][cheatStart.second]++
                    }
                }
            }
        }
        return result
    }

    val testInput = readInput(20, true)
    check(solve(testInput, 2, 20) == 5)
    check(solve(testInput, 20, 74) == 7)
    check(solve(testInput, 20, 72) == 29)
    check(solve(testInput, 20, 70) == 41)
    check(solve(testInput, 20, 50) == 285)

    val input = readInput(20)
    solve(input, 2, 100).println()
    solve(input, 20, 100).println()
}