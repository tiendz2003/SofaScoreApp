package com.example.sofascoreapp.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sofascoreapp.data.model.Event
import com.example.sofascoreapp.data.model.Tournament
import com.example.sofascoreapp.data.networking.Network
import kotlin.math.abs

class TournamentEventsPagingSource(private val tournamentID: Int) : PagingSource<Int, Event>() {

    override fun getRefreshKey(state: PagingState<Int, Event>): Int {
        return (state.anchorPosition ?: 0) / state.config.pageSize
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Event> {
        val key = params.key ?: 0
        val span = if (key > 0) "next" else "last"

        val response = Network().getService().getTournamentEventsPage(tournamentID, span, abs(key))
        val allEvents = response.body() ?: emptyList()

        //val groupedMatches = allEvents.groupBy { it.round }.flatMap { listOf(it.key) + it.value }
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