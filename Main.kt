package tictactoe
import java.util.Scanner

const val GRIDSIZE = 3
val SCANNER = Scanner(System.`in`)
var userInput = mutableListOf<MutableList<Char>>()

fun playGame() {
    creatingGrid(userInput)
    var userString:String = ""
    do {
        userString = requestSymbols()
    } while(!checkSymbols(userString))
    receiveSymbols(userString)
    printingGrid()
    // printGameResult()
    userMove()
    printingGrid()
}

// Create the list according to GRIDSIZE
fun printingGrid() {
    println("---------")
    for(i in 0 until GRIDSIZE) {
        print("| ")
        for(j in 0 until GRIDSIZE) {
            print(if(userInput[i][j] == '_')"  " else "${userInput[i][j]} ")
        }
        println("|")
    }
    println("---------")
}

fun printGameResult() {
    val countX = symbolCount('X')
    val countO = symbolCount('O')
    val countBlank = symbolCount('_')

    val rowX = checkRow('X')
    val rowO = checkRow('O')
    val columnX= checkColumn('X')
    val columnO = checkColumn('O')
    val diagX = checkDiagonal('X')
    val diagO = checkDiagonal('O')

    if(countX - countO >= 2 ||countO - countX >= 2) {
        println("Impossible")
    }
    else if ((rowO && rowX) || (columnO && columnX) || (diagO && diagX)) {
        println("Impossible")
    }
    else if (rowX || columnX || diagX) {
        println("X wins")
    }
    else if (rowO || columnO || diagO) {
        println("O wins")
    }
    else if (countBlank == 0) {
        println("Draw")
    }
    else
        println("Game not finished")
}

// Counting symbols X - O - _
fun symbolCount(symbol: Char):Int {
    var count:Int = 0
    for (i in 0 until GRIDSIZE) {
        for (j in 0 until GRIDSIZE) {
            if(userInput[i][j] == symbol)
                count++
        }
    }
    return count
}

// Check row win condition
fun checkRow(symbol:Char):Boolean {
    if(userInput.any { sublist -> sublist.all { it == symbol } })
        return true
    return false
}

// Check column win condition
fun checkColumn(symbol:Char):Boolean {

    for(i in 0 until GRIDSIZE) {
        var count:Int = 0
        for (j in 0 until GRIDSIZE) {
            if (userInput[j][i] == symbol)
                count++
        }
        if(count == GRIDSIZE)
            return true
    }
    return false
}

fun checkDiagonal(symbol:Char) : Boolean {
    // Check left to right diagonal
    var count:Int = 0
    for (i in 0 until GRIDSIZE) {
        if (userInput[i][i] == symbol)
            count++
    }
    if(count == GRIDSIZE)
        return true
    count = 0
    // Check right to left diagonal
    var tempIndex = 0
    for (i in GRIDSIZE - 1 downTo 0) {
        if(userInput[tempIndex][i] == symbol) {
            count++
            tempIndex++
        }
    }
    if(count == GRIDSIZE)
        return true
    return false
}

// Creates the list and sublist following GRIDSIZE
fun creatingGrid(inputList: MutableList<MutableList<Char>>) {
    for (i in 0 until GRIDSIZE) {
        val sublistToAdd = mutableListOf<Char>()
        for(i in 0 until GRIDSIZE)
            sublistToAdd.add(' ')
        userInput.add(sublistToAdd)
    }
}

// String input to grid list, this fill with '_' if dont have the necessary length.
fun receiveSymbols(inputString:String) {
    var temporalIndex = 0
    for (i in 0 until GRIDSIZE) {
        for (j in 0 until GRIDSIZE) {
            userInput[i][j] = if(temporalIndex >= inputString.length) '_' else inputString[temporalIndex]
            temporalIndex++
        }
    }
}

// Recive the string and check it. it can contain 'X' 'O' || '_'
fun checkSymbols(input:String) = input.all { it in "XO_" }

// Request the user the input
fun requestSymbols():String {
    val input = SCANNER.nextLine()
    return input
}

fun userMove():List<String> {
    var move:List<String>
    do {
        move = readln().split(" ").toList()
    } while(!checkUserMove(move))
    return move
}

fun checkUserMove(move:List<String>):Boolean {
    var first = move[0].toIntOrNull()
    var second = move[1].toIntOrNull()

    if(first == null || second == null) {
        println("You should enter numbers!")
        return false
    }
    else {
        first -= 1
        second -= 1
        if(first >= GRIDSIZE || second >= GRIDSIZE) {
            println("Coordinates should be from 1 to 3!")
            return false
        }
        else if(userInput[first][second] != '_') {
            println("This cell is occupied! Choose another one!")
            return false
        }
        else {
            userInput[first][second] = 'X'
            return true
        }
    }
}

fun main() {
    playGame()
}
