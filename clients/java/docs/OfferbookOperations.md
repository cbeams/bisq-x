# OfferbookOperations

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addOffer**](OfferbookOperations.md#addOffer) | **POST** /trading/offerbook |  |
| [**getOffer**](OfferbookOperations.md#getOffer) | **GET** /trading/offerbook/{offerId} |  |
| [**getOffers**](OfferbookOperations.md#getOffers) | **GET** /trading/offerbook |  |
| [**removeOffer**](OfferbookOperations.md#removeOffer) | **DELETE** /trading/offerbook/{offerId} |  |


<a id="addOffer"></a>
# **addOffer**
> Object addOffer(addOfferRequest)



### Example
```java
// Import classes:
import bisq.client.java.ApiClient;
import bisq.client.java.ApiException;
import bisq.client.java.Configuration;
import bisq.client.java.models.*;
import bisq.client.java.operations.OfferbookOperations;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    OfferbookOperations apiInstance = new OfferbookOperations(defaultClient);
    AddOfferRequest addOfferRequest = new AddOfferRequest(); // AddOfferRequest | 
    try {
      Object result = apiInstance.addOffer(addOfferRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OfferbookOperations#addOffer");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **addOfferRequest** | [**AddOfferRequest**](AddOfferRequest.md)|  | |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | addOffer 200 response |  -  |

<a id="getOffer"></a>
# **getOffer**
> Offer getOffer(offerId)



### Example
```java
// Import classes:
import bisq.client.java.ApiClient;
import bisq.client.java.ApiException;
import bisq.client.java.Configuration;
import bisq.client.java.models.*;
import bisq.client.java.operations.OfferbookOperations;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    OfferbookOperations apiInstance = new OfferbookOperations(defaultClient);
    String offerId = "offerId_example"; // String | 
    try {
      Offer result = apiInstance.getOffer(offerId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OfferbookOperations#getOffer");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **offerId** | **String**|  | |

### Return type

[**Offer**](Offer.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | getOffer 200 response |  -  |

<a id="getOffers"></a>
# **getOffers**
> List&lt;Offer&gt; getOffers()



### Example
```java
// Import classes:
import bisq.client.java.ApiClient;
import bisq.client.java.ApiException;
import bisq.client.java.Configuration;
import bisq.client.java.models.*;
import bisq.client.java.operations.OfferbookOperations;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    OfferbookOperations apiInstance = new OfferbookOperations(defaultClient);
    try {
      List<Offer> result = apiInstance.getOffers();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OfferbookOperations#getOffers");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;Offer&gt;**](Offer.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | getOffers 200 response |  -  |

<a id="removeOffer"></a>
# **removeOffer**
> Offer removeOffer(offerId)



### Example
```java
// Import classes:
import bisq.client.java.ApiClient;
import bisq.client.java.ApiException;
import bisq.client.java.Configuration;
import bisq.client.java.models.*;
import bisq.client.java.operations.OfferbookOperations;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    OfferbookOperations apiInstance = new OfferbookOperations(defaultClient);
    String offerId = "offerId_example"; // String | 
    try {
      Offer result = apiInstance.removeOffer(offerId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OfferbookOperations#removeOffer");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **offerId** | **String**|  | |

### Return type

[**Offer**](Offer.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | removeOffer 200 response |  -  |

