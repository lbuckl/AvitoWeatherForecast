package com.avito.avitoweatherforecast.domain

import android.icu.text.SimpleDateFormat
import android.icu.util.GregorianCalendar
import android.icu.util.LocaleData
import android.util.Log
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FormatDate {
    private var simpleDateFormatDash: SimpleDateFormat = SimpleDateFormat("E-dd-MMM")
    private val gcalendar = GregorianCalendar()

    fun getCustomDateFormat(date: String):String{
        val splitDate = date.split("-")
        gcalendar.set(splitDate[0].toInt(),splitDate[1].toInt(),splitDate[2].toInt())
        simpleDateFormatDash.calendar = gcalendar
        return simpleDateFormatDash.format(gcalendar.time)
    }
}