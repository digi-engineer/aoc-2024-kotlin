import kotlin.math.abs
import kotlin.math.sign

fun main() {
    val MIN_ABS_DIFF = 1
    val MAX_ABS_DIFF = 3

    fun convertReportsToLevelsList(reports: List<String>): List<List<Int>> {
        return reports.map { report ->
            report.split(" ").map { it.toInt() }
        }
    }

    fun isMonotonic(levels: List<Int>): Boolean {
        val diffs = levels.zipWithNext { first, second -> second - first }
        val firstNumberSign = sign(diffs[0].toFloat())
        return diffs.all { abs(it) in MIN_ABS_DIFF..MAX_ABS_DIFF && sign(it.toFloat()) == firstNumberSign }
    }

    fun part1(reports: List<String>): Int {
        val levelsList = convertReportsToLevelsList(reports)
        val result = levelsList.map { levels ->
            if (isMonotonic(levels)) 1 else 0
        }.sum()
        return result
    }

    fun part2(reports: List<String>): Int {
        val levelsList = convertReportsToLevelsList(reports)
        val result = levelsList.map { levels ->
            if (isMonotonic(levels)) {
                return@map 1
            }
            levels.indices.forEach inner@{ indexToRemove ->
                val dampenedLevels = levels.filterIndexed { index, _ -> index != indexToRemove }
                if (isMonotonic(dampenedLevels))
                    return@map 1
            }
            return@map 0
        }.sum()
        return result
    }

    // Test if implementation meets criteria from the description (for single line input), like:
    check(part1(listOf("7 6 4 2 1")) == 1)
    check(part1(listOf("1 2 7 8 9")) == 0)
    check(part2(listOf("9 7 6 2 1")) == 0)
    check(part2(listOf("8 6 4 4 1")) == 1)

    // Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day02.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
