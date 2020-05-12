package com.Mtkn.umutt.eruyemekhane

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.Mtkn.umutt.eruyemekhane.library.Constants
import com.Mtkn.umutt.eruyemekhane.library.Utility
import kotlinx.android.synthetic.main.tab1_2_main.*

class TabOgrPer : Fragment() {

    private val tabKey = "myTab"

    companion object {
        fun newInstance(myTab: Int) = TabOgrPer().apply {
            this.retainInstance = true
            arguments = Bundle().apply { putInt(tabKey, myTab) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tab1_2_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val myTab = arguments?.getInt(tabKey) ?: Constants.ogrType

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        btn_open_web_page.setOnClickListener {
            Utility.openListWebSite(requireContext())
        }

        mainViewModel.getFoods(myTab).observe(requireActivity(), Observer {
            selectDisplayedChild(it)
            recyclerView.adapter = YemekAdapter(it)
        })
    }

    private fun selectDisplayedChild(list: List<YemekModel>) {
        viewSwitcher.displayedChild = if (list.isEmpty()) 0 else 1
    }
}