package com.example.thindie.astonrickandmorty.domain.personages

import com.example.thindie.astonrickandmorty.domain.BaseProvider
import com.example.thindie.astonrickandmorty.domain.filtering.PersonageFilter

interface PersonageProvider : BaseProvider<PersonageDomain, PersonageFilter>