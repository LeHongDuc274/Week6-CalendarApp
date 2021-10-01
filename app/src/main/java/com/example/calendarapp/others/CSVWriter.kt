package com.example.calendarapp.others

import java.io.IOException
import java.io.PrintWriter
import java.io.Writer

class CSVWriter constructor(writer: Writer) {
    private val separator: Char = ','
    private val lineEnd: String = "\n"
    private val pw = PrintWriter(writer)

    fun writerNext(nextLine: Array<String?>?) {
        if (nextLine == null)
            return
        val sb = StringBuffer()
        for (i in nextLine.indices) {
            if (i != 0) {
                sb.append(separator)
            }
            val nextElement = nextLine[i] ?: continue
            sb.append(wrapElement(nextElement))
        }
        sb.append(lineEnd)
        pw.write(sb.toString())
    }
    private fun wrapElement(nextElement:String):String{
        val sb2 = StringBuffer()
        for(element in nextElement){
            if(element =='\n'){
                sb2.append("\\n")
            } else sb2.append(element)
        }
        return "\"" + sb2 + "\""
    }
    @Throws(IOException::class)
    fun close() {
        pw.flush()
        pw.close()
    }
    @Throws(IOException::class)
    fun flush() {
        pw.flush()
    }
}