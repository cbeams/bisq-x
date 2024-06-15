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

package bisq.client.kotlin.operations

import java.io.IOException
import okhttp3.OkHttpClient
import okhttp3.HttpUrl

import bisq.client.kotlin.models.LoggingCategory
import bisq.client.kotlin.models.UpdateLoggingCategoryRequest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import bisq.client.kotlin.infrastructure.ApiClient
import bisq.client.kotlin.infrastructure.ApiResponse
import bisq.client.kotlin.infrastructure.ClientException
import bisq.client.kotlin.infrastructure.ClientError
import bisq.client.kotlin.infrastructure.ServerException
import bisq.client.kotlin.infrastructure.ServerError
import bisq.client.kotlin.infrastructure.MultiValueMap
import bisq.client.kotlin.infrastructure.PartConfig
import bisq.client.kotlin.infrastructure.RequestConfig
import bisq.client.kotlin.infrastructure.RequestMethod
import bisq.client.kotlin.infrastructure.ResponseType
import bisq.client.kotlin.infrastructure.Success
import bisq.client.kotlin.infrastructure.toMultiValue

class LoggingOperations(basePath: kotlin.String = defaultBasePath, client: OkHttpClient = ApiClient.defaultClient) : ApiClient(basePath, client) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty(ApiClient.baseUrlKey, "http://localhost")
        }
    }

    /**
     * 
     * 
     * @return kotlin.collections.List<LoggingCategory>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getLoggingCategories() : kotlin.collections.List<LoggingCategory> {
        val localVarResponse = getLoggingCategoriesWithHttpInfo()

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as kotlin.collections.List<LoggingCategory>
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()} ${localVarError.body}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
     * 
     * 
     * @return ApiResponse<kotlin.collections.List<LoggingCategory>?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun getLoggingCategoriesWithHttpInfo() : ApiResponse<kotlin.collections.List<LoggingCategory>?> {
        val localVariableConfig = getLoggingCategoriesRequestConfig()

        return request<Unit, kotlin.collections.List<LoggingCategory>>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getLoggingCategories
     *
     * @return RequestConfig
     */
    fun getLoggingCategoriesRequestConfig() : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/logging/categories",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }

    /**
     * 
     * 
     * @param name 
     * @return LoggingCategory
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getLoggingCategory(name: kotlin.String) : LoggingCategory {
        val localVarResponse = getLoggingCategoryWithHttpInfo(name = name)

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as LoggingCategory
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()} ${localVarError.body}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
     * 
     * 
     * @param name 
     * @return ApiResponse<LoggingCategory?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun getLoggingCategoryWithHttpInfo(name: kotlin.String) : ApiResponse<LoggingCategory?> {
        val localVariableConfig = getLoggingCategoryRequestConfig(name = name)

        return request<Unit, LoggingCategory>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getLoggingCategory
     *
     * @param name 
     * @return RequestConfig
     */
    fun getLoggingCategoryRequestConfig(name: kotlin.String) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/logging/categories/{name}".replace("{"+"name"+"}", encodeURIComponent(name.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }

    /**
     * 
     * 
     * @param updateLoggingCategoryRequest 
     * @return void
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun updateLoggingCategory(updateLoggingCategoryRequest: UpdateLoggingCategoryRequest) : Unit {
        val localVarResponse = updateLoggingCategoryWithHttpInfo(updateLoggingCategoryRequest = updateLoggingCategoryRequest)

        return when (localVarResponse.responseType) {
            ResponseType.Success -> Unit
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()} ${localVarError.body}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
     * 
     * 
     * @param updateLoggingCategoryRequest 
     * @return ApiResponse<Unit?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Throws(IllegalStateException::class, IOException::class)
    fun updateLoggingCategoryWithHttpInfo(updateLoggingCategoryRequest: UpdateLoggingCategoryRequest) : ApiResponse<Unit?> {
        val localVariableConfig = updateLoggingCategoryRequestConfig(updateLoggingCategoryRequest = updateLoggingCategoryRequest)

        return request<UpdateLoggingCategoryRequest, Unit>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation updateLoggingCategory
     *
     * @param updateLoggingCategoryRequest 
     * @return RequestConfig
     */
    fun updateLoggingCategoryRequestConfig(updateLoggingCategoryRequest: UpdateLoggingCategoryRequest) : RequestConfig<UpdateLoggingCategoryRequest> {
        val localVariableBody = updateLoggingCategoryRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        
        return RequestConfig(
            method = RequestMethod.PUT,
            path = "/logging/categories",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }


    private fun encodeURIComponent(uriComponent: kotlin.String): kotlin.String =
        HttpUrl.Builder().scheme("http").host("localhost").addPathSegment(uriComponent).build().encodedPathSegments[0]
}
