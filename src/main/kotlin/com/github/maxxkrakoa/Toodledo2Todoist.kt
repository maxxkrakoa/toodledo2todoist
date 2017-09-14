package com.github.maxxkrakoa

import com.fasterxml.jackson.dataformat.csv.CsvMapper
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
    val todoistItemsMap = convertToTodoist(toodledoItems)
    val outDir = File(args[1])
    saveTodoistItems(todoistItemsMap, outDir)
}

private fun saveTodoistItems(todoistItemsMap: MutableMap<String, MutableList<TodoistItem>>, outDir: File) {
    val mapper = CsvMapper()
    val schema = mapper.schemaFor(TodoistLineItemJava::class.java).withColumnSeparator(',').withUseHeader(true)
    val writer = mapper.writerFor(TodoistLineItemJava::class.java).with(schema)

    for ((folder, todoistItems) in todoistItemsMap) {
        val lineItems = mutableListOf<TodoistLineItemJava>()
        for (todoistItem in todoistItems) {
            lineItems.add(todoistItem.taskLineItem)
            if (todoistItem.noteLineItem != null) {
                lineItems.add(todoistItem.noteLineItem!!)
            }
            lineItems.add(emptyTodoistLineItem())
        }
        val outF = File(outDir, folder + ".csv")
        println("Writing " + outF)
        writer.writeValues(outF).writeAll(lineItems)
    }
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

fun convertToTodoist(toodledoItems: MutableList<ToodledoItemJava>): MutableMap<String, MutableList<TodoistItem>> {
    val itemsMap = mutableMapOf<String, MutableList<TodoistItem>>()

    for (toodledoItem in toodledoItems) {
        val item = TodoistItem(convertToodledoTaskAndTags(toodledoItem.TASK, toodledoItem.TAG),
                convertToodledoPriority(toodledoItem.PRIORITY),
                toodledoItem.DUEDATE + " " + toodledoItem.REPEAT,
                toodledoItem.NOTE)

        if (!itemsMap.containsKey(toodledoItem.FOLDER)) {
            itemsMap.put(toodledoItem.FOLDER, mutableListOf<TodoistItem>())
        }
        itemsMap.get(toodledoItem.FOLDER)?.add(item)
    }

    return itemsMap
}

fun convertToodledoTaskAndTags(task: String?, tag: String?): String {
    var retval = ""

    retval += task

    if (tag != null) {
        val tags = tag.split(",")
        for (currentTag in tags) {
            retval += " @" + currentTag.trim()
        }
    }

    return retval
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
