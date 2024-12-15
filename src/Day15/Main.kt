package Day15

import at
import plus
import println
import readInput
import times

fun main() {
    fun part1(input: List<String>): Long {
        val field = input.subList(0, input.indexOf("")).map { it.toMutableList() }

        fun move(robot: Pair<Int, Int>, direction: Pair<Int, Int>): Pair<Int, Int> {
            var i = 1
            while (field.at(robot + direction * i) == 'O') {
                i++
            }
            if (field.at(robot + direction * i) == '.') {
                field[robot.first + direction.first][robot.second + direction.second] = '.'
                if (i > 1) {
                    field[robot.first + direction.first * i][robot.second + direction.second * i] = 'O'
                }
                return robot + direction
            }
            return robot
        }

        val commands = input.subList(input.indexOf("") + 1, input.size).joinToString().map {
            when (it) {
                '^' -> -1 to 0
                '<' -> 0 to -1
                '>' -> 0 to 1
                'v' -> 1 to 0
                else -> 0 to 0
            }
        }.toList()

        var current = 0 to 0
        field.forEachIndexed { i, row -> row.forEachIndexed { j, cell -> if (cell == '@') {
            field[i][j] = '.'
            current = i to j
        } } }

        for (command in commands) {
            current = move(current, command)
        }

        return field.flatMapIndexed { i, row -> row.mapIndexed { j, cell -> if (cell == 'O') i.toLong() to j.toLong() else null } }.filterNotNull().sumOf { it.first * 100 + it.second }
    }

    fun part2(input: List<String>): Long {
        val field = mutableListOf<MutableList<Char>>()
        var current = 0 to 0
        for (i in input.subList(0, input.indexOf("")).indices) {
            field.add(mutableListOf())
            for (j in input[i].indices) {
                when (input[i][j]) {
                    '@' -> {
                        field.last().add('.')
                        field.last().add('.')
                        current = i to j * 2
                    }

                    '.' -> {
                        field.last().add('.')
                        field.last().add('.')
                    }

                    '#' -> {
                        field.last().add('#')
                        field.last().add('#')
                    }

                    'O' -> {
                        field.last().add('[')
                        field.last().add(']')
                    }

                    else -> field.add(mutableListOf(input[i][j]))
                }
            }
        }

        fun canMove(robot: Pair<Int, Int>, direction: Pair<Int, Int>): Boolean {
            when (field.at(robot + direction)) {
                '.' -> return true
                '#' -> return false
                '[' -> return canMove(robot + direction + (0 to 1), direction) && if (direction != 0 to 1) canMove(robot + direction, direction) else true
                ']' -> return canMove(robot + direction + (0 to -1), direction) && if (direction != 0 to -1) canMove(robot + direction, direction) else true
            }
            return false
        }

        fun move(robot: Pair<Int, Int>, direction: Pair<Int, Int>): Pair<Int, Int> {
            when (field.at(robot + direction)) {
                '.' -> {
                    field[robot.first + direction.first][robot.second + direction.second] = field.at(robot)
                    field[robot.first][robot.second] = '.'
                    return robot + direction
                }
                '#' -> {
                    return robot
                }
                '[' -> {
                    move(robot + direction + (0 to 1), direction)
                    if (direction != 0 to 1) {
                        move(robot + direction, direction)
                    }
                    field[robot.first + 2 * direction.first][robot.second + 2 * direction.second + 1] = ']'
                    field[robot.first + 2 * direction.first][robot.second + 2 * direction.second] = '['
                    field[robot.first][robot.second] = '.'
                    return robot + direction
                }
                ']' -> {
                    move(robot + direction + (0 to -1), direction)
                    if (direction != 0 to -1) {
                        move(robot + direction, direction)
                    }
                    field[robot.first + 2 * direction.first][robot.second + 2 * direction.second - 1] = '['
                    field[robot.first + 2 * direction.first][robot.second + 2 * direction.second] = ']'
                    field[robot.first][robot.second] = '.'
                    return robot + direction
                }
            }
            return robot
        }

        val commands = input.subList(input.indexOf("") + 1, input.size).joinToString("").map {
            when (it) {
                '^' -> -1 to 0
                '<' -> 0 to -1
                '>' -> 0 to 1
                'v' -> 1 to 0
                else -> 0 to 0
            }
        }.toList()

        for (command in commands) {
            current = if (canMove(current, command)) move(current, command) else current
            field[current.first][current.second] = '.'
        }

        return field.flatMapIndexed { i, row -> row.mapIndexed { j, cell -> if (cell == '[') i.toLong() to j.toLong() else null } }
            .filterNotNull().sumOf { it.first * 100 + it.second }
    }

    val testInput = readInput(15, true)
    check(part1(testInput) == 10092L)
    check(part2(testInput) == 9021L)

    val input = readInput(15)
    part1(input).println()
    part2(input).println()
}