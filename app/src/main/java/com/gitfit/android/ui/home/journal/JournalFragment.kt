package com.gitfit.android.ui.home.journal

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gitfit.android.AppConstants.Companion.ACTIVITY_CODE_ADDITION
import com.gitfit.android.AppConstants.Companion.ACTIVITY_COFFEE
import com.gitfit.android.AppConstants.Companion.ACTIVITY_GAME_CONSOLE_BREAK
import com.gitfit.android.AppConstants.Companion.ACTIVITY_TABLE_TENNIS
import com.gitfit.android.AppConstants.Companion.ACTIVITY_TYPES
import com.gitfit.android.R
import com.gitfit.android.data.local.db.entity.Activity
import com.gitfit.android.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_journal.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle


class JournalFragment : BaseFragment(), JournalNavigator, Toolbar.OnMenuItemClickListener {

    private val journalViewModel: JournalViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        journalViewModel.setNavigator(this)

        journalViewModel.mIsLoading.observe(this, Observer {
            setLoading(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.journal_menu, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_journal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        journalViewModel.activities.observe(this, Observer {
            it?.let { setRecyclerView(it) }
        })

        toolbar.setOnMenuItemClickListener(this)

        //To avoid displaying error "No adapter attached skipping layout"
        setRecyclerView(emptyList())
    }

    private fun setRecyclerView(activities: List<Activity>) {
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = ActivitiesAdapter(activities.sortedByDescending { it.timestamp })
    }

    private inner class ActivitiesAdapter(private val activities: List<Activity>) :
        RecyclerView.Adapter<ActivitiesAdapter.MyViewHolder>() {
        val dateTimeFormatter: DateTimeFormatter =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)

        private inner class MyViewHolder(item: View) : RecyclerView.ViewHolder(item),
            View.OnClickListener {
            val dateTime: TextView = item.findViewById(R.id.date_time_text)
            val points: TextView = item.findViewById(R.id.points_number_text)
            val value: TextView = item.findViewById(R.id.value_number_text)

            init {
                item.setOnClickListener(this)
            }

            override fun onClick(v: View?) {
                if (activities[adapterPosition].type != ACTIVITY_CODE_ADDITION) {
                    findNavController().navigate(
                        R.id.action_navigation_journal_to_navigation_edit_activity,
                        bundleOf("activityId" to activities[adapterPosition].id)
                    )
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return when (viewType) {
                ACTIVITY_TYPES.indexOf(ACTIVITY_COFFEE) -> MyViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.card_view_activity_coffee, parent, false)
                )
                ACTIVITY_TYPES.indexOf(ACTIVITY_TABLE_TENNIS) -> MyViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.card_view_activity_table_tennis, parent, false)
                )
                ACTIVITY_TYPES.indexOf(ACTIVITY_GAME_CONSOLE_BREAK) -> MyViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.card_view_activity_console, parent, false)
                )
                ACTIVITY_TYPES.indexOf(ACTIVITY_CODE_ADDITION) -> MyViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.card_view_activity_code, parent, false)
                )
                else -> throw Exception("Unknown activity!")
            }

        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val activity = activities[position]
            //todo rename duration field to value field - in api and in android
            holder.dateTime.text = activity.timestamp.format(dateTimeFormatter)
            holder.points.text = activity.points.toString()//it's ok
            //todo uncomment text below and delete when
//            holder.value.text = activity.duration.toString()
            when (activity.type) {
                ACTIVITY_CODE_ADDITION -> holder.value.text = activity.points.toString()
                ACTIVITY_COFFEE -> holder.value.text = "1"
                ACTIVITY_TABLE_TENNIS -> holder.value.text = activity.duration.toString()
                ACTIVITY_GAME_CONSOLE_BREAK -> holder.value.text = activity.duration.toString()
            }
        }

        override fun getItemCount(): Int {
            return activities.size
        }

        override fun getItemViewType(position: Int): Int {
            return ACTIVITY_TYPES.indexOf(activities[position].type)
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_sync) {
            journalViewModel.onActionSyncClick()
            return true
        }
        return false
    }
}