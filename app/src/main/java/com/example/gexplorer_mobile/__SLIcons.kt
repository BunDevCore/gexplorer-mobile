package com.example.gexplorer_mobile

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.gexplorer_mobile.icons.slicons.Filled
import com.example.gexplorer_mobile.icons.slicons.Outlined
import kotlin.collections.List as ____KtList

object SLIcons

private var AllIcons: ____KtList<ImageVector>? = null

@Suppress("unused")
val SLIcons.allIcons: ____KtList<ImageVector>
  get() {
    if (AllIcons != null) {
      return AllIcons!!
    }
    AllIcons = listOf(Filled, Outlined)
    return AllIcons!!
  }
