# UserOperations

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create**](UserOperations.md#create) | **POST** /identities | 


<a id="create"></a>
# **create**
> kotlin.String create(createRequest)



### Example
```kotlin
// Import classes:
//import bisq.client.kotlin.infrastructure.*
//import bisq.client.kotlin.models.*

val apiInstance = UserOperations()
val createRequest : CreateRequest =  // CreateRequest | 
try {
    val result : kotlin.String = apiInstance.create(createRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UserOperations#create")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UserOperations#create")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **createRequest** | [**CreateRequest**](CreateRequest.md)|  |

### Return type

**kotlin.String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

