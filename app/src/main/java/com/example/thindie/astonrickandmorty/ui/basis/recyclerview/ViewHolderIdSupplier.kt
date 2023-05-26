package com.example.thindie.astonrickandmorty.ui.basis.recyclerview

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.bottomsheet.core.CommonBottomSheet
import com.example.thindie.astonrickandmorty.ui.episodes.EpisodesFragment
import com.example.thindie.astonrickandmorty.ui.locations.LocationsFragment
import com.example.thindie.astonrickandmorty.ui.personage.PersonagesFragment

data class ViewHolderIdSupplier(
    @LayoutRes val viewHolderLayout: Int,
    @IdRes val majorChild: Int,
    @IdRes val titleChild: Int,
    @IdRes val lesserChild: Int,
    @IdRes val expandedChild: Int? = null,
    @IdRes val imageChild: Int? = null,
    @IdRes val extraChild: Int? = null,
) {
    companion object {
        fun <T> onObtainMasterSupplier(clazz: Class<T>): ViewHolderIdSupplier {
            return when (clazz) {
                EpisodesFragment::class.java -> {
                    parentEpisodes
                }
                PersonagesFragment::class.java -> {
                    parentPersonages
                }
                LocationsFragment::class.java -> {
                    parentLocations
                }
                else -> {
                    error(" unknown consumer of ViewHolderIDSupplier ")
                }
            }
        }

        fun <T> onObtainChildSupplier(clazz: Class<T>): ViewHolderIdSupplier {
            return when (clazz) {
                EpisodesFragment::class.java -> {
                    childEpisodes
                }

                LocationsFragment::class.java -> {
                    childLocations
                }
                PersonagesFragment::class.java -> {
                    childLocations
                }
                else -> {
                    error(" unknown consumer of ViewHolderIDSupplier ")
                }
            }
        }

        inline fun <reified R : CommonBottomSheet<*>> onObtainCommonBottomSheet(clazz: Class<R>): ViewHolderIdSupplier {
            return when (clazz) {
                R::class.java -> {
                    ViewHolderIdSupplier(
                        viewHolderLayout = com.example.thindie.astonrickandmorty.R.layout.bottom_sheet_layout,
                        majorChild = com.example.thindie.astonrickandmorty.R.id.bottom_sheet_title,
                        titleChild = com.example.thindie.astonrickandmorty.R.id.bottom_sheet_component1,
                        lesserChild = com.example.thindie.astonrickandmorty.R.id.bottom_sheet_button,
                        expandedChild = null,
                        imageChild = null,
                        extraChild = com.example.thindie.astonrickandmorty.R.id.bottom_sheet_recycler,
                    )
                }
                else -> {
                    error(" unknown consumer of ViewHolderIDSupplier ")
                }
            }
        }

        private val childEpisodes: ViewHolderIdSupplier =
            ViewHolderIdSupplier(
                viewHolderLayout = R.layout.item_grid_personage_child_mode,
                majorChild = R.id.item_grid_personages_name_child,
                titleChild = R.id.item_grid_personages_status_child,
                lesserChild = R.id.item_grid_personages_species_child,
                expandedChild = null,
                imageChild = null,
                extraChild = null,
            )

        private val childLocations = childEpisodes

        private val parentEpisodes = ViewHolderIdSupplier(
            viewHolderLayout = R.layout.item_grid_episodes,
            majorChild = R.id.item_grid_episodes_name,
            titleChild = R.id.item_grid_episodes_episode,
            lesserChild = R.id.item_grid_episodes_air_date,
            expandedChild = null,
            imageChild = null,
            extraChild = R.id.item_episodes_slave_recycler_view,
        )

        private val parentLocations = ViewHolderIdSupplier(
            viewHolderLayout = R.layout.item_grid_locations,
            majorChild = R.id.item_grid_locations_name,
            titleChild = R.id.item_grid_locations_type,
            lesserChild = R.id.item_grid_locations_dimension,
            expandedChild = null,
            imageChild = null,
            extraChild = R.id.item_locations_slave_recycler_view,
        )

        private val parentPersonages = ViewHolderIdSupplier(
            viewHolderLayout = R.layout.item_grid_personages,
            majorChild = R.id.item_grid_personages_name,
            titleChild = R.id.item_grid_personages_status,
            lesserChild = R.id.item_grid_personages_species,
            expandedChild = R.id.item_grid_personages_gender,
            imageChild = R.id.item_grid_personages_image,
            extraChild = null,
        )
    }
}
