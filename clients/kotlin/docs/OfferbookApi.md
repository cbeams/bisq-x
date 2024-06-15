# OfferbookApi

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addOffer**](OfferbookApi.md#addOffer) | **POST** /trading/offerbook | 
[**getOffer**](OfferbookApi.md#getOffer) | **GET** /trading/offerbook/{offerId} | 
[**getOffers**](OfferbookApi.md#getOffers) | **GET** /trading/offerbook | 
[**removeOffer**](OfferbookApi.md#removeOffer) | **DELETE** /trading/offerbook/{offerId} | 


<a id="addOffer"></a>
# **addOffer**
> kotlin.Any addOffer(addOfferRequest)



### Example
```kotlin
// Import classes:
//import bisq.client.kotlin.infrastructure.*
//import bisq.client.kotlin.models.*

val apiInstance = OfferbookApi()
val addOfferRequest : AddOfferRequest =  // AddOfferRequest | 
try {
    val result : kotlin.Any = apiInstance.addOffer(addOfferRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OfferbookApi#addOffer")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OfferbookApi#addOffer")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **addOfferRequest** | [**AddOfferRequest**](AddOfferRequest.md)|  |

### Return type

[**kotlin.Any**](kotlin.Any.md)

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

val apiInstance = OfferbookApi()
val offerId : kotlin.String = offerId_example // kotlin.String | 
try {
    val result : Offer = apiInstance.getOffer(offerId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OfferbookApi#getOffer")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OfferbookApi#getOffer")
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

val apiInstance = OfferbookApi()
try {
    val result : kotlin.collections.List<Offer> = apiInstance.getOffers()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OfferbookApi#getOffers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OfferbookApi#getOffers")
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

val apiInstance = OfferbookApi()
val offerId : kotlin.String = offerId_example // kotlin.String | 
try {
    val result : Offer = apiInstance.removeOffer(offerId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OfferbookApi#removeOffer")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OfferbookApi#removeOffer")
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

