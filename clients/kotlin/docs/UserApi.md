# UserApi

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create**](UserApi.md#create) | **POST** /identities | 


<a id="create"></a>
# **create**
> kotlin.Any create(createRequest)



### Example
```kotlin
// Import classes:
//import org.openapitools.client.infrastructure.*
//import org.openapitools.client.models.*

val apiInstance = UserApi()
val createRequest : CreateRequest =  // CreateRequest | 
try {
    val result : kotlin.Any = apiInstance.create(createRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserApi#create")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserApi#create")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **createRequest** | [**CreateRequest**](CreateRequest.md)|  |

### Return type

[**kotlin.Any**](kotlin.Any.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

