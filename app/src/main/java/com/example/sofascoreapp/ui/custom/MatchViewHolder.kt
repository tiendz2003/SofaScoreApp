package com.example.sofascoreapp.ui.custom

import android.content.Context
import android.content.Intent
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.sofascoreapp.MatchDetailActivity
import com.example.sofascoreapp.R
import com.example.sofascoreapp.data.model.Event
import com.example.sofascoreapp.data.model.EventStatusEnum
import com.example.sofascoreapp.data.model.UiModel
import com.example.sofascoreapp.data.model.WinnerCode
import com.example.sofascoreapp.databinding.MatchListItemBinding
import com.example.sofascoreapp.utils.Preferences
import com.example.sofascoreapp.utils.Utilities

class MatchViewHolder(private val binding: MatchListItemBinding, val context: Context) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(event: UiModel.Event) {

        Preferences.initialize(context)

        with(binding) {

            homeTeamLayout.teamName.text = event.homeTeam.name
            awayTeamLayout.teamName.text = event.awayTeam.name

            when (event.status) {
                EventStatusEnum.INPROGRESS -> {
                    homeScore.text = event.homeScore.total.toString()
                    awayScore.text = event.awayScore.total.toString()
                }

                EventStatusEnum.FINISHED -> {
                    homeScore.text = event.homeScore.total.toString()
                    awayScore.text = event.awayScore.total.toString()


                    when (event.winnerCode) {
                        WinnerCode.HOME -> {
                            Utilities().setWinningTint(context, homeTeamLayout.teamName, homeScore)
                        }

                        WinnerCode.AWAY -> {
                            Utilities().setWinningTint(context, awayTeamLayout.teamName, awayScore)
                        }

                        else -> {}
                    }

                    timeLayout.currentMinute.text = context.getString(R.string.ft)
                    if (Preferences.getSavedDateFormat()) {
                        timeLayout.timeOfMatch.text =
                            Utilities().getAvailableDateShort(event.startDate!!)
                    } else {
                        timeLayout.timeOfMatch.text =
                            Utilities().getInvertedAvailableDateShort(event.startDate!!)
                    }
                }

                else -> {
                    if (Utilities().isToday(event.startDate!!)) {
                        timeLayout.currentMinute.text = "-"
                        timeLayout.timeOfMatch.text =
                            event.startDate.let { Utilities().getMatchHour(it) }
                    } else if (Utilities().isTomorrow(event.startDate)) {
                        timeLayout.currentMinute.text =
                            event.startDate.let { Utilities().getMatchHour(it) }
                        timeLayout.timeOfMatch.text = context.getString(R.string.tomorrow)
                    } else {

                        timeLayout.currentMinute.text =
                            event.startDate.let { Utilities().getMatchHour(it) }

                        if (Preferences.getSavedDateFormat()) {
                            timeLayout.timeOfMatch.text =
                                Utilities().getAvailableDateShort(event.startDate)
                        } else {
                            timeLayout.timeOfMatch.text =
                                Utilities().getInvertedAvailableDateShort(event.startDate)
                        }
                    }
                }
            }

            homeTeamLayout.clubIcon.load(
                context.getString(
                    R.string.team_icon_url,
                    event.homeTeam.id
                )
            )
            awayTeamLayout.clubIcon.load(
                context.getString(
                    R.string.team_icon_url,
                    event.awayTeam.id
                )
            )

            layout.setOnClickListener {
                val intent = Intent(context, MatchDetailActivity::class.java)
                intent.putExtra("matchID", event.id)
                context.startActivity(intent)
            }
        }

    }

    fun bind(event: Event) {
        with(binding) {

            homeTeamLayout.teamName.text = event.homeTeam.name
            awayTeamLayout.teamName.text = event.awayTeam.name

            when (event.status) {
                EventStatusEnum.INPROGRESS -> {
                    homeScore.text = event.homeScore.total.toString()
                    awayScore.text = event.awayScore.total.toString()
                }

                EventStatusEnum.FINISHED -> {
                    homeScore.text = event.homeScore.total.toString()
                    awayScore.text = event.awayScore.total.toString()

                    when (event.winnerCode) {
                        WinnerCode.HOME -> {
                            Utilities().setWinningTint(context, homeTeamLayout.teamName, homeScore)
                        }

                        WinnerCode.AWAY -> {
                            Utilities().setWinningTint(context, awayTeamLayout.teamName, awayScore)
                        }

                        else -> {}
                    }

                    timeLayout.currentMinute.text = context.getString(R.string.ft)
                    if (Preferences.getSavedDateFormat()) {
                        timeLayout.timeOfMatch.text =
                            Utilities().getAvailableDateShort(event.startDate!!)
                    } else {
                        timeLayout.timeOfMatch.text =
                            Utilities().getInvertedAvailableDateShort(event.startDate!!)
                    }
                }

                else -> {
                    if (Utilities().isToday(event.startDate!!)) {
                        timeLayout.currentMinute.text = "-"
                        timeLayout.timeOfMatch.text =
                            event.startDate.let { Utilities().getMatchHour(it) }
                    } else if (Utilities().isTomorrow(event.startDate)) {
                        timeLayout.currentMinute.text =
                            event.startDate.let { Utilities().getMatchHour(it) }
                        timeLayout.timeOfMatch.text = context.getString(R.string.tomorrow)
                    } else {

                        timeLayout.currentMinute.text =
                            event.startDate.let { Utilities().getMatchHour(it) }

                        if (Preferences.getSavedDateFormat()) {
                            timeLayout.timeOfMatch.text =
                                Utilities().getAvailableDateShort(event.startDate)
                        } else {
                            timeLayout.timeOfMatch.text =
                                Utilities().getInvertedAvailableDateShort(event.startDate)
                        }
                    }
                }
            }

            homeTeamLayout.clubIcon.load(
                context.getString(
                    R.string.team_icon_url,
                    event.homeTeam.id
                )
            )
            awayTeamLayout.clubIcon.load(
                context.getString(
                    R.string.team_icon_url,
                    event.awayTeam.id
                )
            )

            layout.setOnClickListener {
                val intent = Intent(context, MatchDetailActivity::class.java)
                intent.putExtra("matchID", event.id)
                context.startActivity(intent)
            }
        }

    }
}