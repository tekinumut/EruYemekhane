package com.Mtkn.umutt.eruyemekhane.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.Mtkn.umutt.eruyemekhane.Library
import com.Mtkn.umutt.eruyemekhane.R
import com.Mtkn.umutt.eruyemekhane.YemekAdapter
import com.Mtkn.umutt.eruyemekhane.YemekModel
import com.Mtkn.umutt.eruyemekhane.room.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.tab_1_2_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements


class TabOgrenciPersonel : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        fun newInstance(myTab: Int, tabPosition: Int) = TabOgrenciPersonel().apply {
            this.retainInstance = true
            arguments = Bundle(2).apply {
                putInt("myTab", myTab)
                putInt("tabPosition", tabPosition)
            }
        }
    }

    private var hashYemek: HashMap<Int, YemekModel> = HashMap()
    private var myTab = 0
    private var tabPosition = 0
    private var tarihPath = ".ListeSatirOgr >.YemekTarih"
    private var yemekPath = ".ListeSatirOgr >.YemekListe"
    private var yemekEachPath = ">ul >li"
    private lateinit var loadingDialog: AlertDialog
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.tab_1_2_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        refreshLayout.setOnRefreshListener(this)

        loadingDialog = AlertDialog.Builder(context!!).setCancelable(false).setView(R.layout.dialog_loading).create()
        myTab = arguments?.getInt("myTab") ?: 0
        tabPosition = arguments?.getInt("tabPosition") ?: 0

        if (myTab == 1) {
            tarihPath = ".Personel > .ListeSatir > .YemekTarih"
            yemekPath = ".Personel > .ListeSatir > .YemekListe"
        }

        go_web_site.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Library.URL)))
        }

        if (savedInstanceState == null) getValues()

        mainViewModel.getFoods(myTab, requireContext()).observe(this.requireActivity(), Observer { result ->
            result.onSuccess {
                recyclerView.adapter = YemekAdapter(it)
                if (recyclerView.adapter?.itemCount == 0)
                    viewSwitcher.displayedChild = 1
                else viewSwitcher.displayedChild = 0
            }
        })

    }

    private fun getValues() {
        if (!Library.checkInternetConnection(requireContext())) {
            refreshLayout.isRefreshing = false
            Snackbar.make(view!!, getString(R.string.no_connection), 5000)
                .setAction("Web \nsitesine\ngit") {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Library.URL)))
                }.setActionTextColor(ContextCompat.getColor(context!!, R.color.whiteNight)).show()

        } else {
            var doc: Document = Jsoup.parse("")
            loadingDialog.show()

            CoroutineScope(IO).launch {
                @Suppress("BlockingMethodInNonBlockingContext")
                try {
                    doc = Jsoup.connect(Library.URL).timeout(5000).get()
                } catch (e: Exception) {
                    Snackbar.make(view!!, getString(R.string.no_access_web_page), 10000)
                        .setAction("Web \nsitesine\ngit") {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Library.URL)))
                        }.setActionTextColor(ContextCompat.getColor(context!!, R.color.whiteNight)).show()
                    this.cancel("catch")
                }
            }.invokeOnCompletion {

                if (it?.message == "catch") {
                    CoroutineScope(Main).launch {
                        loadingDialog.dismiss()
                    }
                    refreshLayout.isRefreshing = false
                    return@invokeOnCompletion
                }

                CoroutineScope(Main).launch {
                    val ogrTarihValue: Elements = doc.select(tarihPath)
                    val ogrYemekValue: Elements = doc.select(yemekPath)
                    val yemekBuilder = StringBuilder()
                    for (i in 0 until ogrTarihValue.size) {
                        yemekBuilder.clear()
                        for (yemek in ogrYemekValue[i].select(yemekEachPath)) {
                            hashYemek[i] = YemekModel(tarih = ogrTarihValue[i].text(),
                                yemekler = yemekBuilder.append("\u2022 ").append(yemek.text()).append("\n").toString(),
                                type = myTab)
                        }
                    }
                    try {
                        recyclerView.adapter = YemekAdapter(hashYemek.values.toList())

                        if (recyclerView.adapter?.itemCount == 0)
                            viewSwitcher.displayedChild = 1
                        else
                            viewSwitcher.displayedChild = 0

                        loadingDialog.dismiss()
                        refreshLayout.isRefreshing = false

                        mainViewModel.updateFoods(myTab, hashYemek.values.toList(), requireContext())

                    } catch (e: Exception) {

                    }
                }

            }
        }
    }

    override fun onPause() {
        super.onPause()
        loadingDialog.dismiss()
    }

    override fun onRefresh() {
        refreshLayout.post { getValues() }
    }
}