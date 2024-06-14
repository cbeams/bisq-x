# LoggingApi

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getLoggingCategories**](LoggingApi.md#getLoggingCategories) | **GET** /logging/categories | 
[**getLoggingCategory**](LoggingApi.md#getLoggingCategory) | **GET** /logging/categories/{name} | 
[**updateLoggingCategory**](LoggingApi.md#updateLoggingCategory) | **PUT** /logging/categories | 


<a id="getLoggingCategories"></a>
# **getLoggingCategories**
> kotlin.collections.List&lt;LoggingCategory&gt; getLoggingCategories()



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = LoggingApi()
try {
    val result : kotlin.collections.List<LoggingCategory> = apiInstance.getLoggingCategories()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling LoggingApi#getLoggingCategories")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling LoggingApi#getLoggingCategories")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**kotlin.collections.List&lt;LoggingCategory&gt;**](LoggingCategory.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="getLoggingCategory"></a>
# **getLoggingCategory**
> LoggingCategory getLoggingCategory(name)



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = LoggingApi()
val name : kotlin.String = name_example // kotlin.String | 
try {
    val result : LoggingCategory = apiInstance.getLoggingCategory(name)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling LoggingApi#getLoggingCategory")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling LoggingApi#getLoggingCategory")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **name** | **kotlin.String**|  |

### Return type

[**LoggingCategory**](LoggingCategory.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="updateLoggingCategory"></a>
# **updateLoggingCategory**
> updateLoggingCategory(updateLoggingCategoryRequest)



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = LoggingApi()
val updateLoggingCategoryRequest : UpdateLoggingCategoryRequest =  // UpdateLoggingCategoryRequest | 
try {
    apiInstance.updateLoggingCategory(updateLoggingCategoryRequest)
} catch (e: ClientException) {
    println("4xx response calling LoggingApi#updateLoggingCategory")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling LoggingApi#updateLoggingCategory")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **updateLoggingCategoryRequest** | [**UpdateLoggingCategoryRequest**](UpdateLoggingCategoryRequest.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

