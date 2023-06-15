package com.example.thindie.astonrickandmorty.ui.basis.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class FragmentRecycler<Fragment>(
    private val context: Context,
    private val supplier: FragmentViewHolderParamSupplier,
) :
    ListAdapter<FragmentInstanceBundleSupplier, FragmentRecyclerViewHolder>(
        FragmentRecyclerDiffUtilCallBack()
    )
        where Fragment : RecycleViewed, Fragment : androidx.fragment.app.Fragment {

    private val activity: AppCompatActivity by lazy { context as AppCompatActivity }

    override fun onBindViewHolder(holder: FragmentRecyclerViewHolder, position: Int) {
        val item = getItem(position)
        val containerId: Int = holder.itemView.id
        val oldFragment: Fragment = activity
            .supportFragmentManager
            .findFragmentById(containerId) as Fragment

        activity
            .supportFragmentManager
            .beginTransaction()
            .remove(oldFragment)
            .commit()
        val newContainerId = View.generateViewId()
        holder.itemView.id = newContainerId

        val fragment: Fragment = RecycleViewed.getInstance(item.get())
        activity
            .supportFragmentManager
            .beginTransaction()
            .replace(newContainerId, fragment)
            .commit()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FragmentRecyclerViewHolder {
        return FragmentRecyclerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(supplier.fragmentContainer, parent, false)
                .apply {
                    id = View.generateViewId()
                }
        )
    }

}

class FragmentRecyclerDiffUtilCallBack :
    DiffUtil.ItemCallback<FragmentInstanceBundleSupplier>() {
    override fun areItemsTheSame(
        oldItem: FragmentInstanceBundleSupplier,
        newItem: FragmentInstanceBundleSupplier
    ): Boolean {
        return false
    }

    override fun areContentsTheSame(
        oldItem: FragmentInstanceBundleSupplier,
        newItem: FragmentInstanceBundleSupplier
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}


class FragmentRecyclerViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
}