package com.avito.avitoweatherforecast.utils

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.GregorianCalendar

class FormatDate {
    @SuppressLint("SimpleDateFormat")
    private var simpleDateFormatDash: SimpleDateFormat = SimpleDateFormat("EE-dd-MMM")
    private val gcalendar = GregorianCalendar()

    fun getCustomDateFormat(date: String):String{
        val splitDate = date.split("-")
        gcalendar.set(splitDate[0].toInt(),splitDate[1].toInt()-1,splitDate[2].toInt())
        simpleDateFormatDash.calendar = gcalendar
        return simpleDateFormatDash.format(gcalendar.time)
    }
}