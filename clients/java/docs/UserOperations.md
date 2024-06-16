# UserOperations

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**create**](UserOperations.md#create) | **POST** /identities |  |


<a id="create"></a>
# **create**
> Object create(createRequest)



### Example
```java
// Import classes:
import bisq.client.java.infrastructure.ApiClient;
import bisq.client.java.infrastructure.ApiException;
import bisq.client.java.infrastructure.Configuration;
import bisq.client.java.infrastructure.models.*;
import bisq.client.java.operations.UserOperations;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    UserOperations apiInstance = new UserOperations(defaultClient);
    CreateRequest createRequest = new CreateRequest(); // CreateRequest | 
    try {
      Object result = apiInstance.create(createRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserOperations#create");
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
| **createRequest** | [**CreateRequest**](CreateRequest.md)|  | |

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
| **200** | create 200 response |  -  |

