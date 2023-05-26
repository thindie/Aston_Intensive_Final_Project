package com.example.thindie.astonrickandmorty.ui.basis.uiApi.bottomsheet.core

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.ViewHolderIdSupplier
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.bottomsheet.BottomSheetFiller
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.R as materialR

open class CommonBottomSheet<T : BottomSheetFiller>(private val supplier: ViewHolderIdSupplier) :
    BottomSheetDialogFragment(R.layout.bottom_sheet_layout) {

    open var onClickAction: (T) -> Unit = {}
    open lateinit var model: BottomSheetModel

    private val adapter: BottomSheetAdditionalListAdapter<T, BottomSheetViewHolder> =
        createAdapter()
    private lateinit var customizer: CommonBottomSheetCustomizer

    private fun createAdapter(): BottomSheetAdditionalListAdapter<T, BottomSheetViewHolder> {
        return BottomSheetAdditionalListAdapter(onClickAction, supplier)
    }

    fun submitData(models: List<T>) {
        adapter.submitList(models)
    }

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentInstance = this
        with(view) {
            val bottomSheetLayout =
                this@CommonBottomSheet.dialog?.findViewById(materialR.id.design_bottom_sheet) as FrameLayout

            if (currentInstance::model.isInitialized) {
                customizer = CommonBottomSheetCustomizer(model)
                customizer.customize(bottomSheetLayout)
            }
            /*   widGet<TextView>(requireNotNull(supplier.titleChild)).text = model.title
               widGet<RecyclerView>(requireNotNull(supplier.extraChild)).adapter = adapter*/
        }
    }
}
