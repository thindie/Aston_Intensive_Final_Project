package com.example.thindie.astonrickandmorty.domain.episodes

import com.example.thindie.astonrickandmorty.domain.BaseProvider
import com.example.thindie.astonrickandmorty.domain.filtering.EpisodeFilter

interface EpisodeProvider : BaseProvider<EpisodeDomain, EpisodeFilter>