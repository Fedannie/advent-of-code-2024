package Day12

import at
import isInsideStr
import next
import println
import readInput

fun main() {
    fun getPerimeter(current: Pair<Int, Int>, field: List<String>): MutableList<Pair<Pair<Int, Int>, Pair<Int, Int>>> {
        val result = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        for (neighbor in next(current)) {
            if (!field.isInsideStr(neighbor) || field.at(current) != field.at(neighbor)) {
                result.add(current to neighbor)
            }
        }
        return result
    }

    fun dfs(current: Pair<Int, Int>, field: List<String>, visited: List<MutableList<Boolean>>): Pair<Int, MutableList<Pair<Pair<Int, Int>, Pair<Int, Int>>>> {
        var area = 1
        val perimeter = getPerimeter(current, field)
        visited[current.first][current.second] = true
        for (neighbor in next(current)) {
            if (field.isInsideStr(neighbor) && field.at(neighbor) == field.at(current) && !visited[neighbor.first][neighbor.second]) {
                val (a, p) = dfs(neighbor, field, visited)
                area += a
                perimeter.addAll(p)
            }
        }
        return area to perimeter
    }

    fun countEdges(edges: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Int {
        var result = 0
        val visited = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        val horizontalEdges = edges.filter { it.first.second == it.second.second }.sortedBy { it.first.second }
        for (edge in horizontalEdges) {
            if (edge !in visited) {
                result++
                visited.add(edge)
                var i = 1
                while (true) {
                    if (horizontalEdges.contains((edge.first.first to edge.first.second + i) to (edge.second.first to edge.second.second + i))) {
                        visited.add((edge.first.first to edge.first.second + i) to (edge.second.first to edge.second.second + i))
                    } else {
                        break
                    }
                    i++
                }
            }
        }

        val verticalEdges = edges.filter { it.first.first == it.second.first }.sortedBy { it.first.first }
        for (edge in verticalEdges) {
            if (edge !in visited) {
                result++
                visited.add(edge)
                var i = 1
                while (true) {
                    if (verticalEdges.contains((edge.first.first + i to edge.first.second) to (edge.second.first + i to edge.second.second))) {
                        visited.add((edge.first.first + i to edge.first.second) to (edge.second.first + i to edge.second.second))
                    } else {
                        break
                    }
                    i++
                }
            }
        }

        return result
    }

    fun part1(input: List<String>): Int {
        val visited = List(input.size) { MutableList(input[0].length) { false } }
        var result = 0
        for (i in input.indices) {
            for (j in input[0].indices) {
                if (!visited.at(i to j)) {
                    val (area, perimeter) = dfs(i to j, input, visited)
                    result += area * perimeter.size
                }
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        val visited = List(input.size) { MutableList(input[0].length) { false } }
        var result = 0
        for (i in input.indices) {
            for (j in input[0].indices) {
                if (!visited.at(i to j)) {
                    val (area, perimeter) = dfs(i to j, input, visited)
                    result += area * countEdges(perimeter)
                }
            }
        }
        return result
    }

    val testInput = readInput(12, true)
    check(part1(testInput) == 1930)
    check(part2(testInput) == 1206)

    val input = readInput(12)
    part1(input).println()
    part2(input).println()
}