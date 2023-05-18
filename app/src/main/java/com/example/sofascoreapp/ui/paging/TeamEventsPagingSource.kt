package com.example.sofascoreapp.ui.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sofascoreapp.data.model.Event
import com.example.sofascoreapp.data.networking.Network
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.math.abs

class TeamEventsPagingSource(private val teamID: Int) : PagingSource<Int, Event>() {

    override fun getRefreshKey(state: PagingState<Int, Event>): Int {
        return (state.anchorPosition ?: 0) / state.config.pageSize
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Event> {
        val key = params.key ?: 0
        val span = if (key > 0) "next" else "last"

        val response = Network().getService().getTeamEventsPage(teamID, span, abs(key))
        val allEvents = response.body() ?: emptyList()

        val sortedMatches = allEvents.sortedBy { it.round }

        val prevKey = if (allEvents.isNotEmpty()) key - 1 else null
        val nextKey = if (allEvents.isNotEmpty()) key + 1 else null

        return LoadResult.Page(
            data = sortedMatches,
            prevKey = prevKey,
            nextKey = nextKey
        )
    }


}