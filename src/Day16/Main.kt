package Day16

import at
import isInsideStr
import plus
import println
import readInput
import java.util.PriorityQueue

class State(var position: Pair<Int, Int>, var direction: Pair<Int, Int>, var score: Long, var history: List<State>)

fun next(state: State): List<State> {
    val history = state.history.toMutableList()
    history.add(state)
    return listOf(
        State(state.position + state.direction, state.direction, state.score + 1, history),
        State(state.position, -1 to 0, state.score + 1000, history),
        State(state.position, 1 to 0, state.score + 1000, history),
        State(state.position, 0 to -1, state.score + 1000, history),
        State(state.position, 0 to 1, state.score + 1000, history),
    )
}

fun main() {
    fun bfs(start: Pair<Int, Int>, end: Pair<Int, Int>, field: List<String>): Pair<Long, Int> {
        val queue = PriorityQueue<State> { a, b -> a.score.compareTo(b.score) }
        queue.add(State(start, 0 to 1, 0L, listOf()))
        val visited = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        val result = mutableSetOf<Pair<Int, Int>>()
        var score = Long.MAX_VALUE

        while (queue.isNotEmpty()) {
            val current = queue.poll()
            queue.remove(current)
            visited.add(current.position to current.direction)

            if (current.position == end) {
                if (current.score <= score) {
                    score = current.score
                    result.addAll(current.history.map { it.position })
                    continue
                }
                return score to result.size + 1
            }

            for (neighbor in next(current)) {
                if (neighbor.position to neighbor.direction !in visited && field.isInsideStr(neighbor.position) && field.at(neighbor.position) != '#') {
                    queue.add(neighbor)
                }
            }
        }

        return score to result.size + 1
    }

    fun getCoordinates(input: List<String>, c: Char): Pair<Int, Int> {
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == c) {
                    return i to j
                }
            }
        }
        return -1 to -1
    }

    fun part1(input: List<String>): Long {
        return bfs(getCoordinates(input, 'S'), getCoordinates(input, 'E'), input).first
    }

    fun part2(input: List<String>): Int {
        return bfs(getCoordinates(input, 'S'), getCoordinates(input, 'E'), input).second
    }

    val testInput = readInput(16, true)
    check(part1(testInput) == 7036L)
    check(part2(testInput) == 45)

    val input = readInput(16)
    part1(input).println()
    part2(input).println()
}