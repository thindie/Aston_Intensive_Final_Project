package com.example.thindie.astonrickandmorty.domain.locations

import com.example.thindie.astonrickandmorty.domain.BaseProvider
import com.example.thindie.astonrickandmorty.domain.filtering.LocationsFilter

interface LocationProvider : BaseProvider<LocationDomain, LocationsFilter>