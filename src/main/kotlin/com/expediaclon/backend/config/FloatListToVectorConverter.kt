package com.expediaclon.backend.config

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.postgresql.util.PGobject

@Converter(autoApply = false)
class FloatListToVectorConverter : AttributeConverter<List<Float>?, PGobject?> {

    override fun convertToDatabaseColumn(attribute: List<Float>?): PGobject? {
        if (attribute == null) return null

        val pgObject = PGobject()
        pgObject.type = "vector"
        pgObject.value = attribute.joinToString(
            prefix = "[",
            postfix = "]",
            separator = ","
        )

        return pgObject
    }

    override fun convertToEntityAttribute(dbData: PGobject?): List<Float>? {
        if (dbData?.value == null) return null

        val cleaned = dbData.value!!
            .removePrefix("[")
            .removeSuffix("]")

        if (cleaned.isBlank()) return emptyList()

        return cleaned.split(",").map { it.trim().toFloat() }
    }
}
