/*
 * bisq-openapi
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 2.1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package bisq.client.oas.endpoint;

import bisq.client.oas.ApiCallback;
import bisq.client.oas.ApiClient;
import bisq.client.oas.ApiException;
import bisq.client.oas.ApiResponse;
import bisq.client.oas.Configuration;
import bisq.client.oas.Pair;
import bisq.client.oas.ProgressRequestBody;
import bisq.client.oas.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import bisq.client.oas.model.LoggingCategory;
import bisq.client.oas.model.UpdateCategoryRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoggingEndpoint {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public LoggingEndpoint() {
        this(Configuration.getDefaultApiClient());
    }

    public LoggingEndpoint(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public int getHostIndex() {
        return localHostIndex;
    }

    public void setHostIndex(int hostIndex) {
        this.localHostIndex = hostIndex;
    }

    public String getCustomBaseUrl() {
        return localCustomBaseUrl;
    }

    public void setCustomBaseUrl(String customBaseUrl) {
        this.localCustomBaseUrl = customBaseUrl;
    }

    /**
     * Build call for getCategories
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> getCategories 200 response </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getCategoriesCall(final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/logging/categories";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getCategoriesValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return getCategoriesCall(_callback);

    }

    /**
     * 
     * 
     * @return List&lt;LoggingCategory&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> getCategories 200 response </td><td>  -  </td></tr>
     </table>
     */
    public List<LoggingCategory> getCategories() throws ApiException {
        ApiResponse<List<LoggingCategory>> localVarResp = getCategoriesWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * 
     * 
     * @return ApiResponse&lt;List&lt;LoggingCategory&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> getCategories 200 response </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<List<LoggingCategory>> getCategoriesWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = getCategoriesValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<List<LoggingCategory>>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     *  (asynchronously)
     * 
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> getCategories 200 response </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getCategoriesAsync(final ApiCallback<List<LoggingCategory>> _callback) throws ApiException {

        okhttp3.Call localVarCall = getCategoriesValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<List<LoggingCategory>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getCategory
     * @param name  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> getCategory 200 response </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getCategoryCall(String name, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/logging/categories/{name}"
            .replace("{" + "name" + "}", localVarApiClient.escapeString(name.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getCategoryValidateBeforeCall(String name, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'name' is set
        if (name == null) {
            throw new ApiException("Missing the required parameter 'name' when calling getCategory(Async)");
        }

        return getCategoryCall(name, _callback);

    }

    /**
     * 
     * 
     * @param name  (required)
     * @return LoggingCategory
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> getCategory 200 response </td><td>  -  </td></tr>
     </table>
     */
    public LoggingCategory getCategory(String name) throws ApiException {
        ApiResponse<LoggingCategory> localVarResp = getCategoryWithHttpInfo(name);
        return localVarResp.getData();
    }

    /**
     * 
     * 
     * @param name  (required)
     * @return ApiResponse&lt;LoggingCategory&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> getCategory 200 response </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<LoggingCategory> getCategoryWithHttpInfo(String name) throws ApiException {
        okhttp3.Call localVarCall = getCategoryValidateBeforeCall(name, null);
        Type localVarReturnType = new TypeToken<LoggingCategory>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     *  (asynchronously)
     * 
     * @param name  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> getCategory 200 response </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getCategoryAsync(String name, final ApiCallback<LoggingCategory> _callback) throws ApiException {

        okhttp3.Call localVarCall = getCategoryValidateBeforeCall(name, _callback);
        Type localVarReturnType = new TypeToken<LoggingCategory>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateCategory
     * @param updateCategoryRequest  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> updateCategory 200 response </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateCategoryCall(UpdateCategoryRequest updateCategoryRequest, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = updateCategoryRequest;

        // create path and map variables
        String localVarPath = "/logging/categories";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(basePath, localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateCategoryValidateBeforeCall(UpdateCategoryRequest updateCategoryRequest, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'updateCategoryRequest' is set
        if (updateCategoryRequest == null) {
            throw new ApiException("Missing the required parameter 'updateCategoryRequest' when calling updateCategory(Async)");
        }

        return updateCategoryCall(updateCategoryRequest, _callback);

    }

    /**
     * 
     * 
     * @param updateCategoryRequest  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> updateCategory 200 response </td><td>  -  </td></tr>
     </table>
     */
    public void updateCategory(UpdateCategoryRequest updateCategoryRequest) throws ApiException {
        updateCategoryWithHttpInfo(updateCategoryRequest);
    }

    /**
     * 
     * 
     * @param updateCategoryRequest  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> updateCategory 200 response </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> updateCategoryWithHttpInfo(UpdateCategoryRequest updateCategoryRequest) throws ApiException {
        okhttp3.Call localVarCall = updateCategoryValidateBeforeCall(updateCategoryRequest, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     *  (asynchronously)
     * 
     * @param updateCategoryRequest  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> updateCategory 200 response </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateCategoryAsync(UpdateCategoryRequest updateCategoryRequest, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateCategoryValidateBeforeCall(updateCategoryRequest, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
}
