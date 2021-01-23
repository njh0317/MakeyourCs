package com.example.makeyourcs.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.makeyourcs.R
import com.example.makeyourcs.databinding.FragmentHomeBinding
import com.example.makeyourcs.ui.RecyclerFeedAdapter
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.kcontext


abstract class InjectionFragment : Fragment(), KodeinAware {

    final override val kodeinContext = kcontext<Fragment>(this)
    final override val kodein: Kodein by kodein()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        kodeinTrigger?.trigger()
    }
}


class HomeFragment : InjectionFragment(){
    private val factory : HomeViewModelFactory by instance()

    lateinit var binding:FragmentHomeBinding
    lateinit var viewmodel:HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //var view=LayoutInflater.from(activity).inflate(R.layout.fragment_home,container,false)

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        var view=binding.root
        viewmodel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)


        //recyclerview adapter, layoutmanger setting
        val adapter= RecyclerFeedAdapter(view.context, viewmodel._imgList)
        binding.recyclerView.adapter=adapter
        val lmanager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager=lmanager

        return view
    }
}