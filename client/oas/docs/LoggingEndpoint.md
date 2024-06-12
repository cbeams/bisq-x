# LoggingEndpoint

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getCategories**](LoggingEndpoint.md#getCategories) | **GET** /logging/categories |  |
| [**getCategory**](LoggingEndpoint.md#getCategory) | **GET** /logging/categories/{name} |  |
| [**updateCategory**](LoggingEndpoint.md#updateCategory) | **PUT** /logging/categories |  |


<a id="getCategories"></a>
# **getCategories**
> List&lt;LoggingCategory&gt; getCategories()



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
      List<LoggingCategory> result = apiInstance.getCategories();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling LoggingEndpoint#getCategories");
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
| **200** | getCategories 200 response |  -  |

<a id="getCategory"></a>
# **getCategory**
> LoggingCategory getCategory(name)



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
      LoggingCategory result = apiInstance.getCategory(name);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling LoggingEndpoint#getCategory");
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
| **200** | getCategory 200 response |  -  |

<a id="updateCategory"></a>
# **updateCategory**
> updateCategory(updateCategoryRequest)



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
    UpdateCategoryRequest updateCategoryRequest = new UpdateCategoryRequest(); // UpdateCategoryRequest | 
    try {
      apiInstance.updateCategory(updateCategoryRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling LoggingEndpoint#updateCategory");
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
| **updateCategoryRequest** | [**UpdateCategoryRequest**](UpdateCategoryRequest.md)|  | |

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
| **200** | updateCategory 200 response |  -  |

