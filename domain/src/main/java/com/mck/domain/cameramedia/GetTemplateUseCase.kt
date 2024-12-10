package com.mck.domain.cameramedia

import com.mck.data.model.TemplateModel
import com.mck.data.repository.TemplateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTemplateUseCase @Inject constructor(
    private val templateRepository: TemplateRepository
) {
    operator fun invoke(): Flow<List<TemplateModel>> {
        return templateRepository.getTemplates()
    }
}