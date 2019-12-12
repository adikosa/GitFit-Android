package com.gitfit.android.ui.home.journal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gitfit.android.R
import kotlinx.android.synthetic.main.fragment_journal.*

class JournalFragment : Fragment() {

    private lateinit var dashboardViewModel: JournalViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(JournalViewModel::class.java)
        return inflater.inflate(R.layout.fragment_journal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = MyAdapter((1..20).toList())
    }

    private inner class MyAdapter constructor(private val devices: List<Int>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        private inner class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
            val textView: TextView = item.findViewById(R.id.date_time_text)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.coffee_activity_card_view, parent, false)

            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.textView.text = devices[position].toString()
        }

        override fun getItemCount(): Int {
            return devices.size
        }

    }
}