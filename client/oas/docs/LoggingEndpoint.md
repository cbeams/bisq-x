# LoggingEndpoint

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getLoggingCategories**](LoggingEndpoint.md#getLoggingCategories) | **GET** /logging/categories |  |
| [**getLoggingCategory**](LoggingEndpoint.md#getLoggingCategory) | **GET** /logging/categories/{name} |  |
| [**updateLoggingCategory**](LoggingEndpoint.md#updateLoggingCategory) | **PUT** /logging/categories |  |


<a id="getLoggingCategories"></a>
# **getLoggingCategories**
> List&lt;LoggingCategory&gt; getLoggingCategories()



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
      List<LoggingCategory> result = apiInstance.getLoggingCategories();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling LoggingEndpoint#getLoggingCategories");
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

[**List&lt;LoggingCategory&gt;**](LoggingCategory.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | getLoggingCategories 200 response |  -  |

<a id="getLoggingCategory"></a>
# **getLoggingCategory**
> LoggingCategory getLoggingCategory(name)



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
      LoggingCategory result = apiInstance.getLoggingCategory(name);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling LoggingEndpoint#getLoggingCategory");
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

[**LoggingCategory**](LoggingCategory.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | getLoggingCategory 200 response |  -  |

<a id="updateLoggingCategory"></a>
# **updateLoggingCategory**
> updateLoggingCategory(updateLoggingCategoryRequest)



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
    UpdateLoggingCategoryRequest updateLoggingCategoryRequest = new UpdateLoggingCategoryRequest(); // UpdateLoggingCategoryRequest | 
    try {
      apiInstance.updateLoggingCategory(updateLoggingCategoryRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling LoggingEndpoint#updateLoggingCategory");
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
| **updateLoggingCategoryRequest** | [**UpdateLoggingCategoryRequest**](UpdateLoggingCategoryRequest.md)|  | |

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
| **200** | updateLoggingCategory 200 response |  -  |

