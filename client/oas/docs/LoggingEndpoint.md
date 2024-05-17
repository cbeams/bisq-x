# LoggingEndpoint

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**get**](LoggingEndpoint.md#get) | **GET** /logs |  |
| [**get1**](LoggingEndpoint.md#get1) | **GET** /logs/{name} |  |
| [**put**](LoggingEndpoint.md#put) | **PUT** /logs/{name} |  |


<a id="get"></a>
# **get**
> List&lt;LogConfig&gt; get()



### Example
```java
// Import classes:
import bisq.client.oas.ApiClient;
import bisq.client.oas.ApiException;
import bisq.client.oas.Configuration;
import bisq.client.oas.models.*;
import bisq.client.oas.endpoint.LoggingEndpoint;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    LoggingEndpoint apiInstance = new LoggingEndpoint(defaultClient);
    try {
      List<LogConfig> result = apiInstance.get();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling LoggingEndpoint#get");
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

[**List&lt;LogConfig&gt;**](LogConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | get 200 response |  -  |

<a id="get1"></a>
# **get1**
> LogConfig get1(name)



### Example
```java
// Import classes:
import bisq.client.oas.ApiClient;
import bisq.client.oas.ApiException;
import bisq.client.oas.Configuration;
import bisq.client.oas.models.*;
import bisq.client.oas.endpoint.LoggingEndpoint;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    LoggingEndpoint apiInstance = new LoggingEndpoint(defaultClient);
    String name = "name_example"; // String | 
    try {
      LogConfig result = apiInstance.get1(name);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling LoggingEndpoint#get1");
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
| **name** | **String**|  | |

### Return type

[**LogConfig**](LogConfig.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | get_1 200 response |  -  |

<a id="put"></a>
# **put**
> put(name, putRequest)



### Example
```java
// Import classes:
import bisq.client.oas.ApiClient;
import bisq.client.oas.ApiException;
import bisq.client.oas.Configuration;
import bisq.client.oas.models.*;
import bisq.client.oas.endpoint.LoggingEndpoint;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    LoggingEndpoint apiInstance = new LoggingEndpoint(defaultClient);
    String name = "name_example"; // String | 
    PutRequest putRequest = new PutRequest(); // PutRequest | 
    try {
      apiInstance.put(name, putRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling LoggingEndpoint#put");
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
| **name** | **String**|  | |
| **putRequest** | [**PutRequest**](PutRequest.md)|  | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | put 200 response |  -  |

