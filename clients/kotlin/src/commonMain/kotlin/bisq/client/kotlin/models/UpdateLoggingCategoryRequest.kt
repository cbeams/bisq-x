/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package bisq.client.kotlin.models

import bisq.client.kotlin.models.LoggingCategory

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * 
 *
 * @param loggingCategory 
 */
@Serializable

data class UpdateLoggingCategoryRequest (

    @SerialName(value = "loggingCategory") val loggingCategory: LoggingCategory? = null

)

