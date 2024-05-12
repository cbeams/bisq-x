# OfferEndpoint

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**add**](OfferEndpoint.md#add) | **POST** /trade/offers |  |
| [**delete**](OfferEndpoint.md#delete) | **DELETE** /trade/offers/{id} |  |
| [**listAll**](OfferEndpoint.md#listAll) | **GET** /trade/offers |  |
| [**show**](OfferEndpoint.md#show) | **GET** /trade/offers/{id} |  |


<a id="add"></a>
# **add**
> Object add(addRequest)



### Example
```java
// Import classes:
import bisq.client.openapi.ApiClient;
import bisq.client.openapi.ApiException;
import bisq.client.openapi.Configuration;
import bisq.client.openapi.models.*;
import bisq.client.openapi.endpoint.OfferEndpoint;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    OfferEndpoint apiInstance = new OfferEndpoint(defaultClient);
    AddRequest addRequest = new AddRequest(); // AddRequest | 
    try {
      Object result = apiInstance.add(addRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OfferEndpoint#add");
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
| **addRequest** | [**AddRequest**](AddRequest.md)|  | |

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
| **200** | add 200 response |  -  |

<a id="delete"></a>
# **delete**
> Offer delete(id)



### Example
```java
// Import classes:
import bisq.client.openapi.ApiClient;
import bisq.client.openapi.ApiException;
import bisq.client.openapi.Configuration;
import bisq.client.openapi.models.*;
import bisq.client.openapi.endpoint.OfferEndpoint;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    OfferEndpoint apiInstance = new OfferEndpoint(defaultClient);
    String id = "id_example"; // String | 
    try {
      Offer result = apiInstance.delete(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OfferEndpoint#delete");
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
| **id** | **String**|  | |

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
| **200** | delete 200 response |  -  |

<a id="listAll"></a>
# **listAll**
> List&lt;Offer&gt; listAll()



### Example
```java
// Import classes:
import bisq.client.openapi.ApiClient;
import bisq.client.openapi.ApiException;
import bisq.client.openapi.Configuration;
import bisq.client.openapi.models.*;
import bisq.client.openapi.endpoint.OfferEndpoint;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    OfferEndpoint apiInstance = new OfferEndpoint(defaultClient);
    try {
      List<Offer> result = apiInstance.listAll();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OfferEndpoint#listAll");
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
| **200** | listAll 200 response |  -  |

<a id="show"></a>
# **show**
> Offer show(id)



### Example
```java
// Import classes:
import bisq.client.openapi.ApiClient;
import bisq.client.openapi.ApiException;
import bisq.client.openapi.Configuration;
import bisq.client.openapi.models.*;
import bisq.client.openapi.endpoint.OfferEndpoint;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    OfferEndpoint apiInstance = new OfferEndpoint(defaultClient);
    String id = "id_example"; // String | 
    try {
      Offer result = apiInstance.show(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OfferEndpoint#show");
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
| **id** | **String**|  | |

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
| **200** | show 200 response |  -  |

