# InfoOperations

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getInfo**](InfoOperations.md#getInfo) | **GET** /info | 


<a id="getInfo"></a>
# **getInfo**
> Info getInfo()



### Example
```kotlin
// Import classes:
//import bisq.client.kotlin.infrastructure.*
//import bisq.client.kotlin.models.*

val apiInstance = InfoOperations()
try {
    val result : Info = apiInstance.getInfo()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling InfoOperations#getInfo")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling InfoOperations#getInfo")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Info**](Info.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

