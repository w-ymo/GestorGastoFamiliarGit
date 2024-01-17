package com.example.gestorgastofamiliar.utils

import java.text.SimpleDateFormat
import java.util.Date

class DateUtils {
    companion object {
        val formato: SimpleDateFormat = SimpleDateFormat("dddd, dd/MMMM/yyyy")
        fun dateToString(date: Date): String {
            return formato.format(date)
        };
    }


}