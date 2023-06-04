package com.dicoding.newsapp.util

import org.junit.Assert
import org.junit.Test


class DateFormatterTest {
    @Test
    fun `given correct ISO 8601 format then should format correctly`() {
        val currentDate = "2022-02-02T10:10:10Z"
        Assert.assertEquals("02 Feb 2022 | 17:10", DateFormatter.formatDate(currentDate, "Asia/Jakarta"))
        Assert.assertEquals("02 Feb 2022 | 18:10", DateFormatter.formatDate(currentDate, "Asia/Makassar"))
        Assert.assertEquals("02 Feb 2022 | 19:10", DateFormatter.formatDate(currentDate, "Asia/Jayapura"))
    }
}