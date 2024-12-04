import kotlin.math.abs

fun main() {
    fun part1(locations: List<String>): Int {
        val (left, right) = locations.map { location ->
            val left = location.substringBefore(" ").toInt()
            val right = location.substringAfterLast(" ").toInt()
            left to right
        }.unzip()

        val distance = left.sorted().zip(right.sorted()).map { (left, right) ->
            abs(left-right)
        }.sum()
        return distance
    }

    fun part2(locations: List<String>): Int {
        val (left, right) = locations.map { location ->
            location.split("""\s+""".toRegex()).let {
                it[0].toInt() to it[1].toInt()
            }
        }.unzip()

        val frequencies = right.groupingBy { it }.eachCount()
        val similarityScore = left.fold(0)  { sum, value -> sum + value * frequencies.getOrDefault(value, 0)}
        return similarityScore
    }

    // Test if implementation meets criteria from the description (for single line input), like:
    check(part1(listOf("3   4")) == 1)
    check(part2(listOf("3   4")) == 0)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
