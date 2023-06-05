package com.example.thindie.astonrickandmorty.domain.personages

import com.example.thindie.astonrickandmorty.domain.BaseProvider
import com.example.thindie.astonrickandmorty.domain.filtering.CharacterFilter

interface PersonageProvider : BaseProvider<PersonageDomain, CharacterFilter>