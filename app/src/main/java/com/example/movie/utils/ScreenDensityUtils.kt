package com.example.movie.utils

import android.content.res.Resources

fun Float.toDp():Float =(this / Resources.getSystem().displayMetrics.density)
fun Float.toPx():Float =(this * Resources.getSystem().displayMetrics.density)