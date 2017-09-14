package com.github.maxxkrakoa

import com.fasterxml.jackson.annotation.JsonPropertyOrder

/**
 * Created by mv on 2017-09-13.
 */


// Tried to use this instead of ToodledoItemJava.java but data ends up in the wrong fields when using Jackson CSV
/*
@JsonPropertyOrder("TASK", "FOLDER", "CONTEXT", "GOAL", "LOCATION", "STARTDATE", "STARTTIME", "DUEDATE", "DUETIME", "REPEAT",
        "LENGTH", "TIMER", "PRIORITY", "TAG", "STATUS", "STAR", "NOTE")
class ToodledoItem() {
    var TASK = ""
    var FOLDER = ""
    var CONTEXT = ""
    var GOAL = ""
    var LOCATION = ""
    var STARTDATE = ""
    var STARTTIME = ""
    var DUEDATE = ""
    var DUETIME = ""
    var REPEAT = ""
    var LENGTH = ""
    var TIMER = ""
    var PRIORITY = ""
    var TAG = ""
    var STATUS = ""
    var STAR = ""
    var NOTE = ""
}
*/

fun main(args: Array<String>) {
    println("Toodledo2Todoist")
}
