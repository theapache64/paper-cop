package com.theapache64.papercop.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by theapache64 : Dec 12 Sat,2020 @ 16:49
 */
@Parcelize
class Role(
    val name: String,
    val point: Int,
    val emoji: String
) : Parcelable