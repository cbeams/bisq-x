# InfoEndpoint

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getInfo**](InfoEndpoint.md#getInfo) | **GET** /info |  |


<a id="getInfo"></a>
# **getInfo**
> Info getInfo()



### Example
```java
// Import classes:
import bisq.client.openapi.ApiClient;
import bisq.client.openapi.ApiException;
import bisq.client.openapi.Configuration;
import bisq.client.openapi.models.*;
import bisq.client.openapi.endpoint.InfoEndpoint;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    InfoEndpoint apiInstance = new InfoEndpoint(defaultClient);
    try {
      Info result = apiInstance.getInfo();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling InfoEndpoint#getInfo");
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
