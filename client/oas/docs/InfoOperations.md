# InfoOperations

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getInfo**](InfoOperations.md#getInfo) | **GET** /info |  |


<a id="getInfo"></a>
# **getInfo**
> Info getInfo()



### Example
```java
// Import classes:
import bisq.client.oas.ApiClient;
import bisq.client.oas.ApiException;
import bisq.client.oas.Configuration;
import bisq.client.oas.models.*;
import bisq.client.oas.operations.InfoOperations;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    InfoOperations apiInstance = new InfoOperations(defaultClient);
    try {
      Info result = apiInstance.getInfo();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling InfoOperations#getInfo");
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

[**Info**](Info.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | getInfo 200 response |  -  |

