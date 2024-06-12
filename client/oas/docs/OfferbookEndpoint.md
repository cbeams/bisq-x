# OfferbookEndpoint

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addOffer**](OfferbookEndpoint.md#addOffer) | **POST** /trading/offerbook |  |
| [**getOffer**](OfferbookEndpoint.md#getOffer) | **GET** /trading/offerbook/{offerId} |  |
| [**getOffers**](OfferbookEndpoint.md#getOffers) | **GET** /trading/offerbook |  |
| [**removeOffer**](OfferbookEndpoint.md#removeOffer) | **DELETE** /trading/offerbook/{offerId} |  |


<a id="addOffer"></a>
# **addOffer**
> Object addOffer(addOfferRequest)



### Example
```java
// Import classes:
import bisq.client.oas.ApiClient;
import bisq.client.oas.ApiException;
import bisq.client.oas.Configuration;
import bisq.client.oas.models.*;
import bisq.client.oas.endpoint.OfferbookEndpoint;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    OfferbookEndpoint apiInstance = new OfferbookEndpoint(defaultClient);
    AddOfferRequest addOfferRequest = new AddOfferRequest(); // AddOfferRequest | 
    try {
      Object result = apiInstance.addOffer(addOfferRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OfferbookEndpoint#addOffer");
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
import bisq.client.oas.ApiClient;
import bisq.client.oas.ApiException;
import bisq.client.oas.Configuration;
import bisq.client.oas.models.*;
import bisq.client.oas.endpoint.OfferbookEndpoint;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    OfferbookEndpoint apiInstance = new OfferbookEndpoint(defaultClient);
    String offerId = "offerId_example"; // String | 
    try {
      Offer result = apiInstance.getOffer(offerId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OfferbookEndpoint#getOffer");
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
import bisq.client.oas.ApiClient;
import bisq.client.oas.ApiException;
import bisq.client.oas.Configuration;
import bisq.client.oas.models.*;
import bisq.client.oas.endpoint.OfferbookEndpoint;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    OfferbookEndpoint apiInstance = new OfferbookEndpoint(defaultClient);
    try {
      List<Offer> result = apiInstance.getOffers();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OfferbookEndpoint#getOffers");
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
import bisq.client.oas.ApiClient;
import bisq.client.oas.ApiException;
import bisq.client.oas.Configuration;
import bisq.client.oas.models.*;
import bisq.client.oas.endpoint.OfferbookEndpoint;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    OfferbookEndpoint apiInstance = new OfferbookEndpoint(defaultClient);
    String offerId = "offerId_example"; // String | 
    try {
      Offer result = apiInstance.removeOffer(offerId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OfferbookEndpoint#removeOffer");
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

