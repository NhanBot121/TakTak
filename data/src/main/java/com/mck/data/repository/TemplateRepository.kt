package com.mck.data.repository

import com.mck.data.model.TemplateModel
import com.mck.data.source.TemplateDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TemplateRepository @Inject constructor() {
    fun getTemplates(): Flow<List<TemplateModel>> {
        return TemplateDataSource.fetchTemplates()
    }
}