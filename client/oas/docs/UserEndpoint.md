# UserEndpoint

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**create**](UserEndpoint.md#create) | **POST** /identities |  |


<a id="create"></a>
# **create**
> Object create(createRequest)



### Example
```java
// Import classes:
import bisq.client.oas.ApiClient;
import bisq.client.oas.ApiException;
import bisq.client.oas.Configuration;
import bisq.client.oas.models.*;
import bisq.client.oas.endpoint.UserEndpoint;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    UserEndpoint apiInstance = new UserEndpoint(defaultClient);
    CreateRequest createRequest = new CreateRequest(); // CreateRequest | 
    try {
      Object result = apiInstance.create(createRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserEndpoint#create");
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
