package com.jonikoone.dictionaryforlearning.util

import android.content.res.ColorStateList

fun Int.replaceColor(mask: Int, colorItem: Int, shiftLeft: Int) =
    (this and mask) or (colorItem shl shiftLeft)

fun Int.replaceRed(colorItem: Int) =
    this.replaceColor(0xff00ffff.toInt(), colorItem, 16)

fun Int.replaceGreen(colorItem: Int) =
    this.replaceColor(0xffff00ff.toInt(), colorItem, 8)

fun Int.replaceBlue(colorItem: Int) =
    this.replaceColor(0xffffff00.toInt(), colorItem, 0)

fun Int.getColorRed() = (this shr 16) and 0xff
fun Int.getColorGreen() = (this shr 8) and 0xff
fun Int.getColorBlue() = this and 0xff


fun colorStatelistOf(color: Int) = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(color))
