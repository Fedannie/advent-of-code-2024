import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(day: Int, test: Boolean = false) = Path("src/Day${day.toString().padStart(2, '0')}/input${if (test) "_test" else ""}.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun extractTwoDividers(line: String, divider1: Char, divider2: Char): List<String> {
    return line.split(divider1).flatMap { it.split(divider2) }
}

fun <T> bfsWithPath(start: T, isEnd: (T) -> Boolean, isValid: (T) -> Boolean, next: (T) -> List<T>): Int {
    val queue = mutableListOf(start to listOf<T>())
    val visited = mutableSetOf(start)

    while (queue.isNotEmpty()) {
        val size = queue.size
        for (i in 0 ..< size) {
            val current = queue.removeFirst()
            if (isEnd(current.first)) {
                current.second.forEach { println(it); println() }
                return current.second.size
            }

            for (neighbor in next(current.first)) {
                if (neighbor !in visited && isValid(neighbor) && neighbor !in queue.map { it.first }) {
                    visited.add(neighbor)
                    queue.add(neighbor to current.second + neighbor)
                }
            }
        }
    }

    return -1
}

fun <T> bfs(start: T, isEnd: (T) -> Boolean, isValid: (T) -> Boolean, next: (T) -> List<T>): Int {
    val queue = mutableListOf(start)
    val visited = mutableSetOf(start)
    var steps = 0

    while (queue.isNotEmpty()) {
        val size = queue.size
        for (i in 0 ..< size) {
            val current = queue.removeFirst()
            if (isEnd(current)) {
                return steps
            }

            for (neighbor in next(current)) {
                if (neighbor !in visited && isValid(neighbor) && neighbor !in queue.map { it }) {
                    visited.add(neighbor)
                    queue.add(neighbor)
                }
            }
        }
        steps++
    }

    return -1
}

fun <T> bfsSteps(start: T, stepsMax: Int, isValid: (T) -> Boolean, next: (T) -> List<T>): Set<T> {
    val queue = mutableListOf(start)
    val visited = mutableSetOf(start)
    var steps = 0

    while (queue.isNotEmpty()) {
        val size = queue.size
        for (i in 0 ..< size) {
            val current = queue.removeFirst()

            for (neighbor in next(current)) {
                if (neighbor !in visited && isValid(neighbor) && neighbor !in queue.map { it }) {
                    visited.add(neighbor)
                    queue.add(neighbor)
                }
            }
        }
        steps++
        if (steps == stepsMax) {
            return visited
        }
    }

    return setOf()
}

fun List<String>.at(i: Pair<Int, Int>): Char {
    return this[i.first][i.second]
}

fun <T> List<List<T>>.at(i: Pair<Int, Int>): T {
    return this[i.first][i.second]
}

fun List<String>.isInsideStr(i: Pair<Int, Int>): Boolean {
    return i.first in this.indices && i.second in this[0].indices
}

fun List<List<Int>>.isInside(i: Pair<Int, Int>): Boolean {
    return i.first in this.indices && i.second in this[0].indices
}

fun IntRange.length(): Int = last - first + 1

fun next(current: Pair<Int, Int>) = listOf(current.first - 1 to current.second, current.first + 1 to current.second, current.first to current.second - 1, current.first to current.second + 1)

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second

operator fun Pair<Int, Int>.times(other: Int) = first * other to second * other
