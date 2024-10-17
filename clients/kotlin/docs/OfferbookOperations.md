# OfferbookOperations

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addOffer**](OfferbookOperations.md#addOffer) | **POST** /offerbook | 
[**getOffer**](OfferbookOperations.md#getOffer) | **GET** /offerbook/{offerId} | 
[**getOffers**](OfferbookOperations.md#getOffers) | **GET** /offerbook | 
[**removeOffer**](OfferbookOperations.md#removeOffer) | **DELETE** /offerbook/{offerId} | 


<a id="addOffer"></a>
# **addOffer**
> kotlin.String addOffer(addOfferRequest)



### Example
```kotlin
// Import classes:
//import bisq.client.kotlin.infrastructure.*
//import bisq.client.kotlin.models.*

val apiInstance = OfferbookOperations()
val addOfferRequest : AddOfferRequest =  // AddOfferRequest | 
try {
    val result : kotlin.String = apiInstance.addOffer(addOfferRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OfferbookOperations#addOffer")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OfferbookOperations#addOffer")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **addOfferRequest** | [**AddOfferRequest**](AddOfferRequest.md)|  |

### Return type

**kotlin.String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a id="getOffer"></a>
# **getOffer**
> Offer getOffer(offerId)



### Example
```kotlin
// Import classes:
//import bisq.client.kotlin.infrastructure.*
//import bisq.client.kotlin.models.*

val apiInstance = OfferbookOperations()
val offerId : kotlin.String = offerId_example // kotlin.String | 
try {
    val result : Offer = apiInstance.getOffer(offerId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OfferbookOperations#getOffer")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OfferbookOperations#getOffer")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **offerId** | **kotlin.String**|  |

### Return type

[**Offer**](Offer.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="getOffers"></a>
# **getOffers**
> kotlin.collections.List&lt;Offer&gt; getOffers()



### Example
```kotlin
// Import classes:
//import bisq.client.kotlin.infrastructure.*
//import bisq.client.kotlin.models.*

val apiInstance = OfferbookOperations()
try {
    val result : kotlin.collections.List<Offer> = apiInstance.getOffers()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OfferbookOperations#getOffers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OfferbookOperations#getOffers")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**kotlin.collections.List&lt;Offer&gt;**](Offer.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="removeOffer"></a>
# **removeOffer**
> Offer removeOffer(offerId)



### Example
```kotlin
// Import classes:
//import bisq.client.kotlin.infrastructure.*
//import bisq.client.kotlin.models.*

val apiInstance = OfferbookOperations()
val offerId : kotlin.String = offerId_example // kotlin.String | 
try {
    val result : Offer = apiInstance.removeOffer(offerId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OfferbookOperations#removeOffer")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OfferbookOperations#removeOffer")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **offerId** | **kotlin.String**|  |

### Return type

[**Offer**](Offer.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

