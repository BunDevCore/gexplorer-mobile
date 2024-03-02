package com.example.gexplorer_mobile

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.gexplorer_mobile.icons.slicons.Filled
import com.example.gexplorer_mobile.icons.slicons.Outlined
import kotlin.collections.List as ____KtList

public object SLIcons

private var __AllIcons: ____KtList<ImageVector>? = null

public val SLIcons.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons = listOf(Filled, Outlined)
    return __AllIcons!!
  }
