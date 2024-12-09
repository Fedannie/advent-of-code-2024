package Day09

import length
import println
import readInput

class Segment(var range: IntRange, var value: Long?) {
    fun isEmpty(): Boolean {
        return value == null
    }
}

fun main() {
    fun readInput(input: List<String>): MutableList<Segment> {
        var i = 0
        var empty = true
        var id = 0L
        val segments = input[0]
            .map { it - '0' }
            .mapIndexed { index, it ->
                i += it
                empty = empty.not()
                if (!empty) id++
                return@mapIndexed Segment((i - it) ..< i, if (empty) null else id - 1)
            }
            .toMutableList()
        if (segments.last().isEmpty()) segments.removeLast()
        return segments
    }

    fun moveSegment(segments: MutableList<Segment>, i: Int, j: Int): Int {
        if (segments[i].range.length() == segments[j].range.length()) {
            segments[i].value = segments[j].value
            segments[j].value = null
            return 2
        } else if (segments[i].range.length() < segments[j].range.length()) {
            segments[i].value = segments[j].value
            segments[j].range = segments[j].range.first .. segments[j].range.last - segments[i].range.length()
            return 2
        } else {
            val newSegment = Segment(segments[i].range.first ..< segments[i].range.first + segments[j].range.length(), segments[j].value)
            segments[i].range = newSegment.range.last + 1 .. segments[i].range.last
            segments[j].value = null
            segments.add(i, newSegment)
            return 1
        }
    }

    fun clean(segments: MutableList<Segment>) {
        while (segments.last().isEmpty()) segments.removeLast()
        while (true) {
            for (i in segments.indices) {
                if (i > 0 && segments[i].value == segments[i - 1].value) {
                    segments[i - 1].range = segments[i - 1].range.first..segments[i].range.last
                    segments.removeAt(i)
                    break
                }
            }
            break
        }
    }

    fun part1(input: List<String>): Long {
        val segments = readInput(input)
        var i = 0
        while (i < segments.size) {
            if (!segments[i].isEmpty()) {
                i++
                continue
            }
            if (i == segments.size - 1) {
                break
            }
            i += moveSegment(segments, i, segments.size - 1)
            clean(segments)
        }
        return segments.sumOf { (it.value ?: 0L) * it.range.last.toLong() * (it.range.last + 1L) / 2L - (it.value ?: 0L) * it.range.first.toLong() * (it.range.first - 1L) / 2L }
    }

    fun part2(input: List<String>): Long {
        val segments = readInput(input)
        var i = segments.size - 1
        while (i > 0) {
            if (segments[i].isEmpty()) {
                i--
                continue
            }
            for (j in 0 ..< i) {
                if (segments[j].isEmpty() && segments[j].range.length() >= segments[i].range.length()) {
                    if (moveSegment(segments, j, i) == 2) {
                        i--
                    }
                    break
                }
            }
            i--
            clean(segments)
        }
        return segments.sumOf { (it.value ?: 0L) * it.range.last.toLong() * (it.range.last + 1L) / 2L - (it.value ?: 0L) * it.range.first.toLong() * (it.range.first - 1L) / 2L }
    }

    val testInput = readInput(9, true)
    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)

    val input = readInput(9)
    part1(input).println()
    part2(input).println()
}