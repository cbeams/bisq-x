# LoggingOperations

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getLoggingCategories**](LoggingOperations.md#getLoggingCategories) | **GET** /logging/categories |  |
| [**getLoggingCategory**](LoggingOperations.md#getLoggingCategory) | **GET** /logging/categories/{name} |  |
| [**updateLoggingCategory**](LoggingOperations.md#updateLoggingCategory) | **PUT** /logging/categories |  |


<a id="getLoggingCategories"></a>
# **getLoggingCategories**
> List&lt;LoggingCategory&gt; getLoggingCategories()



### Example
```java
// Import classes:
import bisq.client.java.ApiClient;
import bisq.client.java.ApiException;
import bisq.client.java.Configuration;
import bisq.client.java.models.*;
import bisq.client.java.operations.LoggingOperations;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    LoggingOperations apiInstance = new LoggingOperations(defaultClient);
    try {
      List<LoggingCategory> result = apiInstance.getLoggingCategories();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling LoggingOperations#getLoggingCategories");
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
import bisq.client.java.ApiClient;
import bisq.client.java.ApiException;
import bisq.client.java.Configuration;
import bisq.client.java.models.*;
import bisq.client.java.operations.LoggingOperations;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    LoggingOperations apiInstance = new LoggingOperations(defaultClient);
    String name = "name_example"; // String | 
    try {
      LoggingCategory result = apiInstance.getLoggingCategory(name);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling LoggingOperations#getLoggingCategory");
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
import bisq.client.java.ApiClient;
import bisq.client.java.ApiException;
import bisq.client.java.Configuration;
import bisq.client.java.models.*;
import bisq.client.java.operations.LoggingOperations;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    LoggingOperations apiInstance = new LoggingOperations(defaultClient);
    UpdateLoggingCategoryRequest updateLoggingCategoryRequest = new UpdateLoggingCategoryRequest(); // UpdateLoggingCategoryRequest | 
    try {
      apiInstance.updateLoggingCategory(updateLoggingCategoryRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling LoggingOperations#updateLoggingCategory");
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

