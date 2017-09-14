package com.github.maxxkrakoa

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.junit.Assert
import org.junit.Test as test

/**
 * Created by mv on 2017-09-13.
 */
class Toodledo2TodoistTest {

    @test fun testLoadToodledo() {
        val csvStream = javaClass.classLoader.getResourceAsStream("toodledo-sample.csv")

        val mapper = CsvMapper()

        //val schema = mapper.schemaFor(ToodledoItem::class.java).withColumnSeparator(',').withUseHeader(true)
        //val iter: Iterator<ToodledoItem> = mapper.readerFor(ToodledoItem::class.java).with(schema).readValues(csvStream)

        val schema = mapper.schemaFor(ToodledoItemJava::class.java).withColumnSeparator(',').withUseHeader(true)
        val iter: Iterator<ToodledoItemJava> = mapper.readerFor(ToodledoItemJava::class.java).with(schema).readValues(csvStream)

        val items: MutableList<ToodledoItemJava> = mutableListOf()

        for (value in iter) {
            items.add(value)
        }

        Assert.assertEquals(4, items.size)
        Assert.assertEquals("This is a short note", items[1].NOTE)
        Assert.assertEquals("tag1, tag2", items[3].TAG)
        Assert.assertEquals("1", items[3].PRIORITY)
        Assert.assertEquals("2017-09-14", items[2].DUEDATE)
    }
}

