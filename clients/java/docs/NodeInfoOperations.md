# NodeInfoOperations

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getNodeInfo**](NodeInfoOperations.md#getNodeInfo) | **GET** /info |  |


<a id="getNodeInfo"></a>
# **getNodeInfo**
> NodeInfo getNodeInfo()



### Example
```java
// Import classes:
import bisq.client.java.ApiClient;
import bisq.client.java.ApiException;
import bisq.client.java.Configuration;
import bisq.client.java.models.*;
import bisq.client.java.operations.NodeInfoOperations;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    NodeInfoOperations apiInstance = new NodeInfoOperations(defaultClient);
    try {
      NodeInfo result = apiInstance.getNodeInfo();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling NodeInfoOperations#getNodeInfo");
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

[**NodeInfo**](NodeInfo.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | getNodeInfo 200 response |  -  |

