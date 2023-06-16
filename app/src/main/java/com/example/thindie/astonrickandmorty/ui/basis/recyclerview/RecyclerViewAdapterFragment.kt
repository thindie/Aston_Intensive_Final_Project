package com.example.thindie.astonrickandmorty.ui.basis.recyclerview


import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.W
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble

class RecyclerViewAdapterFragment
<Model : SearchAble, ViewHolder, Fragments>
constructor(
    private val clazz : Class<Fragments>,
    private val viewHolderIdSupplier: ViewHolderIdSupplier,
    private val onClickedViewHolder: (Model) -> Unit
) :
    RecyclerViewAdapterMediatorScroll<Model, ViewHolder>(viewHolderIdSupplier, onClickedViewHolder)
        where ViewHolder : RecyclerView.ViewHolder,
              ViewHolder : SearchAbleAdapted,
              Fragments : Fragment,
              Fragments : RecycleViewed? {

    private val activity: AppCompatActivity by lazy { viewHolderIdSupplier.context as AppCompatActivity }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.getHeadline(viewHolderIdSupplier.majorChild).text = item.getMajorComponent()
        holder.getTitle(viewHolderIdSupplier.titleChild).text = item.getMajorComponent1()
        holder.getBody(viewHolderIdSupplier.lesserChild).text = item.getMajorComponent2()
        val isExpanded = item.hasMinorComponents()
        if (isExpanded) {
            holder.activateMinorComponent(
                item.getMinorComponent(),
                viewHolderIdSupplier.imageChild!!,
                viewHolderIdSupplier.expandedChild!!
            )
            holder.activateMinorComponent(
                item.getMinorComponent1(),
                viewHolderIdSupplier.imageChild,
                viewHolderIdSupplier.expandedChild
            )
        }
        if (viewHolderIdSupplier.isExtraContent) {
            val container = holder.getFragmentContainer(
                viewHolderIdSupplier.extraChild!!,
                viewHolderIdSupplier.context
            )

            requireNotNull(container)
                W { "adapter crushes :( " }


            val containerId: Int = container.id
            val oldFragment: Fragments? = activity
                .supportFragmentManager
                .findFragmentById(containerId) as Fragments

            if (oldFragment != null) {
                activity
                    .supportFragmentManager
                    .beginTransaction()
                    .remove(oldFragment)
                    .commit()
            }


              val newContainerId = View.generateViewId()
            holder.itemView.id = newContainerId

            val fragment =  RecycleViewed.getInstance(clazz,item.getExtraComponent())
            activity
                .supportFragmentManager
                .beginTransaction()
                .replace(containerId, fragment)
                .commit()

        }

        holder.itemView.setOnClickListener {
            onClickedViewHolder(item)
        }
    }

    companion object {
        const val POOL = 15
    }

}