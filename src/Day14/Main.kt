package Day14

import println
import readInput

class Robot(var position: Pair<Int, Int>, private val speed: Pair<Int, Int>, private val fieldSize: Pair<Int, Int>) {
    fun move() {
        position = (position.first + speed.first + fieldSize.first) % fieldSize.first to (position.second + speed.second + fieldSize.second) % fieldSize.second
    }
}

fun main() {
    fun countScore(robots: List<Robot>, fieldSize: Pair<Int, Int>): Int {
        val a = robots.count { it.position.first < fieldSize.first / 2 && it.position.second < fieldSize.second / 2 }
        val b = robots.count { it.position.first > fieldSize.first / 2 && it.position.second < fieldSize.second / 2 }
        val c = robots.count { it.position.first < fieldSize.first / 2 && it.position.second > fieldSize.second / 2 }
        val d = robots.count { it.position.first > fieldSize.first / 2 && it.position.second > fieldSize.second / 2 }
        return a * b * c * d
    }

    fun printRobots(robots: List<Robot>, fieldSize: Pair<Int, Int>) {
        for (x in 0 ..< fieldSize.first) {
            for (y in 0 ..< fieldSize.second) {
                if (robots.none { it.position == x to y }) {
                    print(".")
                } else {
                    print(robots.count() { it.position == x to y })
                }
            }
            println()
        }
        println()
        println()
    }

    fun isPotentialChristmasTree(robots: List<Robot>): Boolean {
        for (robot in robots) {
            var suitable = true
            for (x in -4..4) {
                suitable = suitable && robots.any { it.position == robot.position.first + x to robot.position.second }
            }
            if (suitable) {
                return true
            }
        }
        return false
    }

    fun part1(input: List<String>, fieldSize: Pair<Int, Int>): Int {
        val robotRegex = """p=([^ ]+),([^ ]+) v=([^ ]+),([^ ]+)""".toRegex()
        val robots = input.map { robotRegex.matchEntire(it)!!.destructured }.map { (x, y, vx, vy) -> Robot(y.toInt() to x.toInt(), vy.toInt() to vx.toInt(), fieldSize) }
        for (i in 0 until 100) {
            robots.forEach { it.move() }
        }
        return countScore(robots, fieldSize)
    }

    fun part2(input: List<String>, fieldSize: Pair<Int, Int>): Int {
        val robotRegex = """p=([^ ]+),([^ ]+) v=([^ ]+),([^ ]+)""".toRegex()
        val robots = input.map { robotRegex.matchEntire(it)!!.destructured }.map { (x, y, vx, vy) -> Robot(y.toInt() to x.toInt(), vy.toInt() to vx.toInt(), fieldSize) }
        var i = 0
        while (true) {
            i++
            robots.forEach { it.move() }
            if (isPotentialChristmasTree(robots)) {
                printRobots(robots, fieldSize)
                return i
            }
        }
    }

    val testInput = readInput(14, true)
    check(part1(testInput, 7 to 11) == 12)

    val input = readInput(14)
    part1(input, 103 to 101).println()
    part2(input, 103 to 101).println()
}