package com.roblox.ipo.utils

import android.content.Context
import androidx.core.os.ConfigurationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DateConverter @Inject constructor(
    @ApplicationContext context: Context
) {
    private val locale = ConfigurationCompat.getLocales(context.resources.configuration)[0]
    private val dateFormat = SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS", locale)

    fun fromIsoStringToTimestamp(date: String?): Long? =
        if (date != null) dateFormat.parse(date)?.time
        else null

    fun fromTimestampToIsoString(timestamp: Long?): String? =
        if (timestamp != null) dateFormat.format(Date(timestamp))
        else null

}