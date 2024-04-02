package com.bundev.gexplorer_mobile.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun <T, K : Comparable<K>> GroupingList(
    items: List<T>,
    groupBy: (T) -> K,
    title: (K) -> String,
    childFactory: @Composable (T) -> Unit
) {
    val grouped = items.sortedByDescending(groupBy).groupBy(groupBy)
    for ((key, groupItems) in grouped) {
        GroupingListGroup(groupItems, title(key), childFactory)
    }
}

@Composable
fun <T> GroupingListGroup(items: List<T>, title: String, childFactory: @Composable (T) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 7.5.dp),
            text = title,
            fontWeight = FontWeight.Bold
        )
        for (item in items) {
            childFactory(item)
        }
    }
}