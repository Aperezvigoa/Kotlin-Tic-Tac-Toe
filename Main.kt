package tictactoe
import java.util.Scanner

val scanner = Scanner(System.`in`)
const val GRIDSIZE:Int = 3

fun printGrid(player:List<List<Char>>) {
    println("---------")
    for(i in 0 until GRIDSIZE) {
        print("| ")
        for(j in 0 until GRIDSIZE) {
            print("${player[i][j]} ")
        }
        print("| \n")
    }
    println("---------")
}

fun gameStage(player: List<List<Char>>) {
    var countX:Int = 0
    var countO:Int = 0

    for(i in 0 until GRIDSIZE) {
        for(j in 0 until  GRIDSIZE) {
            if(player[i][j] == 'X') {
                countX++
            }
            else if (player[i][j] == 'O') {
                countO++
            }
        }
    }

    var rowXDetected:Boolean = findingRow(player,'X') || findingColumn(player, letter = 'X') || findingDiagonal(player, letter = 'X') || findingReversedDiagonal(player, letter = 'X')
    var rowODetected:Boolean = findingRow(player,'O') || findingColumn(player, letter = 'O') || findingDiagonal(player, letter = 'O') || findingReversedDiagonal(player, letter = 'O')

    gameResult(player, rowXDetected, rowODetected, countX, countO)
}

fun gameResult(player: List<List<Char>>, rowX:Boolean, rowO:Boolean, countX:Int, countO:Int) {
    when {
        countX - countO >= 2 ||countO - countX >= 2 -> println("Impossible")
        rowX && rowO -> println("Impossible")
        rowX -> println("X wins")
        rowO -> println("O wins")
        player.any { sublist -> sublist.any {it == '_'} } -> println("Game not finished")
        else -> println("Draw")
    }
}

fun findingRow(player: List<List<Char>>, letter:Char):Boolean {
    return (player.any { sublist -> sublist.all {it == letter} })
}

fun findingDiagonal(player: List<List<Char>>, letter: Char): Boolean {
    var count = 0
    for (i in 0 until player.size) {
        if (player[i][i] == letter) {
            count++
            if(count == GRIDSIZE)
                return true
        }
    }
    return false
}

fun findingReversedDiagonal(player: List<List<Char>>, letter: Char):Boolean {
    var count = 0
    var movingIndex = player[0].size - 1
    for(i in 0 until player.size) {
        if(player[i][movingIndex] == letter) {
            count++
            movingIndex--
            if(count == GRIDSIZE)
                return true
        }
    }
    return false
}

fun findingColumn(player: List<List<Char>>, letter: Char): Boolean {
    var count:Int = 0
    for(i in 0 until player.size) {
        for(j in 0 until player.size) {
            if(player[j][i] == letter) {
                count++
                if(count == GRIDSIZE)
                    return true
            }
        }
        count = 0
    }
    return false
}

fun validatingString():String {
    var userInput:String
    do {
        userInput = scanner.nextLine()
    } while(!userInput.all {it in "XO_"})

    return userInput
}

fun stringToList(userInput: String):List<List<Char>> {
    println(userInput)
    val multiList= mutableListOf(
        mutableListOf(' ', ' ', ' '),
        mutableListOf(' ', ' ', ' '),
        mutableListOf(' ', ' ', ' ')
    )
    var stringIndex = 0
    for(i in 0 until GRIDSIZE) {
        for(j in 0 until GRIDSIZE) {
            multiList[i][j] = userInput[stringIndex]
            stringIndex++
        }
    }
    return multiList
}


fun main() {
    // write your code here
    val grid = stringToList(validatingString())

    printGrid(grid)
    gameStage(grid)
}