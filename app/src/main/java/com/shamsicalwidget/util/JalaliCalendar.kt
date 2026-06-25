package com.shamsicalwidget.util

import java.util.Calendar

data class JalaliDate(
    val year: Int,
    val month: Int,
    val day: Int,
    val dayOfWeek: Int
)

object JalaliCalendar {

    val monthNames = arrayOf(
        "فروردین", "اردیبهشت", "خرداد",
        "تیر", "مرداد", "شهریور",
        "مهر", "آبان", "آذر",
        "دی", "بهمن", "اسفند"
    )

    private val dayNames = arrayOf(
        "",
        "یکشنبه",
        "دوشنبه",
        "سه‌شنبه",
        "چهارشنبه",
        "پنجشنبه",
        "جمعه",
        "شنبه"
    )

    fun today(): JalaliDate {
        val cal = Calendar.getInstance()
        return fromGregorian(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH) + 1,
            cal.get(Calendar.DAY_OF_MONTH),
            cal.get(Calendar.DAY_OF_WEEK)
        )
    }

    fun monthName(month: Int): String = monthNames[month - 1]
    fun dayName(dayOfWeek: Int): String = dayNames[dayOfWeek]

    private fun fromGregorian(gy: Int, gm: Int, gd: Int, dow: Int): JalaliDate {
        val (jy, jm, jd) = gregorianToJalali(gy, gm, gd)
        return JalaliDate(jy, jm, jd, dow)
    }

    private fun gregorianToJalali(gy: Int, gm: Int, gd: Int): Triple<Int, Int, Int> {
        val gYear = gy - 1600
        val gMonth = gm - 1
        val gDay = gd - 1

        var gDayNo = 365 * gYear + (gYear + 3) / 4 - (gYear + 99) / 100 + (gYear + 399) / 400

        val isLeap = (gy % 4 == 0 && gy % 100 != 0) || gy % 400 == 0
        val gMonthDays = intArrayOf(31, if (isLeap) 29 else 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        for (i in 0 until gMonth) gDayNo += gMonthDays[i]
        gDayNo += gDay

        var jDayNo = gDayNo - 79
        val jNp = jDayNo / 12053
        jDayNo %= 12053

        var jy = 979 + 33 * jNp + 4 * (jDayNo / 1461)
        jDayNo %= 1461

        if (jDayNo >= 366) {
            jy += (jDayNo - 1) / 365
            jDayNo = (jDayNo - 1) % 365
        }

        val jMonthDays = intArrayOf(31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29)
        var jm = 0
        var jd = 0
        for (i in jMonthDays.indices) {
            if (jDayNo >= jMonthDays[i]) {
                jDayNo -= jMonthDays[i]
            } else {
                jm = i + 1
                jd = jDayNo + 1
                break
            }
        }
        return Triple(jy, jm, jd)
    }

    fun toPersianDigits(number: Int): String = number.toString().map {
        when (it) {
            '0' -> '۰'; '1' -> '۱'; '2' -> '۲'; '3' -> '۳'; '4' -> '۴'
            '5' -> '۵'; '6' -> '۶'; '7' -> '۷'; '8' -> '۸'; '9' -> '۹'
            else -> it
        }
    }.joinToString("")
}
