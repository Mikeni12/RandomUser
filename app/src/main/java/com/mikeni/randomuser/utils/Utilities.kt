package com.mikeni.randomuser.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

fun formatDate(date: String): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val formatter = SimpleDateFormat("d-MMMM-yyyy HH:mm:ss", Locale("es", "MX"))
    return formatter.format(parser.parse(date))
}

class MarginItemDecorator(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            top = spaceHeight
            left = spaceHeight
            right = spaceHeight
            bottom = spaceHeight
        }
    }
}