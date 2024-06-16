# NodeInfoOperations

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getNodeInfo**](NodeInfoOperations.md#getNodeInfo) | **GET** /info | 


<a id="getNodeInfo"></a>
# **getNodeInfo**
> NodeInfo getNodeInfo()



### Example
```kotlin
// Import classes:
//import bisq.client.kotlin.infrastructure.*
//import bisq.client.kotlin.models.*

val apiInstance = NodeInfoOperations()
try {
    val result : NodeInfo = apiInstance.getNodeInfo()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling NodeInfoOperations#getNodeInfo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling NodeInfoOperations#getNodeInfo")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**NodeInfo**](NodeInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

