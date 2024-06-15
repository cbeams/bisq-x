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

import bisq.client.kotlin.models.Info

import com.squareup.moshi.Json

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

class InfoApi(basePath: kotlin.String = defaultBasePath, client: OkHttpClient = ApiClient.defaultClient) : ApiClient(basePath, client) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty(ApiClient.baseUrlKey, "http://localhost")
        }
    }

    /**
     * 
     * 
     * @return Info
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getInfo() : Info {
        val localVarResponse = getInfoWithHttpInfo()

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as Info
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
     * @return ApiResponse<Info?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    fun getInfoWithHttpInfo() : ApiResponse<Info?> {
        val localVariableConfig = getInfoRequestConfig()

        return request<Unit, Info>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getInfo
     *
     * @return RequestConfig
     */
    fun getInfoRequestConfig() : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/info",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }


    private fun encodeURIComponent(uriComponent: kotlin.String): kotlin.String =
        HttpUrl.Builder().scheme("http").host("localhost").addPathSegment(uriComponent).build().encodedPathSegments[0]
}