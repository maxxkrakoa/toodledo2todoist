package com.github.maxxkrakoa

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.sun.xml.internal.bind.v2.TODO
import java.io.File
import kotlin.system.exitProcess

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
    if (args.size != 2) {
        println("Toodledo2Todoist <input-toodledo.csv> <output directory>")
        exitProcess(0)
    }

    println("Loading and converting " + args[0] + " -> " + args[1])

    val f = File(args[0])
    val toodledoItems = loadToodledoItems(f)

    // TODO: should convert to a Map of Lists of TodoistItems - mapping from Toodledo folder to items
    // TODO: export should write one file per Map entry for easy import into Todoist
    val todoistItems = convertToTodoist(toodledoItems)
    val outF = File(args[1] + "todoist.csv")
    saveTodoistItems(todoistItems, outF)
}

private fun saveTodoistItems(todoistItems: MutableList<TodoistItem>, outF: File) {
    val mapper = CsvMapper()
    val schema = mapper.schemaFor(TodoistLineItemJava::class.java).withColumnSeparator(',').withUseHeader(true)
    val writer = mapper.writerFor(TodoistLineItemJava::class.java).with(schema)

    val lineItems = mutableListOf<TodoistLineItemJava>()
    for (todoistItem in todoistItems) {
        lineItems.add(todoistItem.taskLineItem)
        if (todoistItem.noteLineItem != null) {
            lineItems.add(todoistItem.noteLineItem!!)
        }
        lineItems.add(emptyTodoistLineItem())
    }
    writer.writeValues(outF).writeAll(lineItems)
}

fun emptyTodoistLineItem(): TodoistLineItemJava {
    val retval = TodoistLineItemJava();
    retval.TYPE = ""
    retval.CONTENT = ""
    retval.PRIORITY = ""
    retval.INDENT = ""
    retval.AUTHOR = ""
    retval.RESPONSIBLE = ""
    retval.DATE = ""
    retval.DATE_LANG = ""
    retval.TIMEZONE = ""
    return retval
}

fun convertToTodoist(toodledoItems: MutableList<ToodledoItemJava>): MutableList<TodoistItem> {
    val items = mutableListOf<TodoistItem>()

    for (toodledoItem in toodledoItems) {
        items.add(TodoistItem(toodledoItem.TASK, // TODO: add tags
                convertToodledoPriority(toodledoItem.PRIORITY),
                toodledoItem.DUEDATE + " " + toodledoItem.REPEAT,
                toodledoItem.NOTE))
    }

    return items
}

fun convertToodledoPriority(priority: String?): String {
    var retval = "4"

    if (priority == "1") {
        retval = "3"
    }
    if (priority == "2") {
        retval = "2"
    }
    if (priority == "3") {
        retval = "1"
    }

    return retval
}

class TodoistItem(content: String, priority: String, date: String, note: String) {
    val taskLineItem = TodoistLineItemJava()
    var noteLineItem: TodoistLineItemJava? = null
    init {
        taskLineItem.TYPE = "task"
        taskLineItem.CONTENT = content
        taskLineItem.PRIORITY = priority
        taskLineItem.INDENT = "1"
        taskLineItem.AUTHOR = ""
        taskLineItem.RESPONSIBLE = ""
        taskLineItem.DATE = date
        taskLineItem.DATE_LANG = "en"
        taskLineItem.TIMEZONE = "Europe/Copenhagen"

        if (note != "") {
            noteLineItem = TodoistLineItemJava()
            noteLineItem!!.TYPE = "note"
            noteLineItem!!.CONTENT = note
            noteLineItem!!.PRIORITY = ""
            noteLineItem!!.INDENT = ""
            noteLineItem!!.AUTHOR = ""
            noteLineItem!!.RESPONSIBLE = ""
            noteLineItem!!.DATE = ""
            noteLineItem!!.DATE_LANG = ""
            noteLineItem!!.TIMEZONE = ""
        }
    }
}

private fun loadToodledoItems(f: File): MutableList<ToodledoItemJava> {
    val mapper = CsvMapper()
    val schema = mapper.schemaFor(ToodledoItemJava::class.java).withColumnSeparator(',').withUseHeader(true)
    val iter: Iterator<ToodledoItemJava> = mapper.readerFor(ToodledoItemJava::class.java).with(schema).readValues(f)

    val items: MutableList<ToodledoItemJava> = mutableListOf()

    for (value in iter) {
        items.add(value)
    }

    return items
}
