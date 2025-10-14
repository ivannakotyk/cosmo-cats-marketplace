# ProductsApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createProduct**](ProductsApi.md#createProduct) | **POST** /api/v1/products | Create a new product |
| [**deleteProduct**](ProductsApi.md#deleteProduct) | **DELETE** /api/v1/products/{id} | Delete a product by ID |
| [**getProduct**](ProductsApi.md#getProduct) | **GET** /api/v1/products/{id} | Get a product by its ID |
| [**listProducts**](ProductsApi.md#listProducts) | **GET** /api/v1/products | Get a list of all products |
| [**updateProduct**](ProductsApi.md#updateProduct) | **PUT** /api/v1/products/{id} | Update an existing product by ID |


<a name="createProduct"></a>
# **createProduct**
> ProductDTO createProduct(productCreateDTO)

Create a new product

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProductsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ProductsApi apiInstance = new ProductsApi(defaultClient);
    ProductCreateDTO productCreateDTO = new ProductCreateDTO(); // ProductCreateDTO | 
    try {
      ProductDTO result = apiInstance.createProduct(productCreateDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsApi#createProduct");
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
| **productCreateDTO** | [**ProductCreateDTO**](ProductCreateDTO.md)|  | |

### Return type

[**ProductDTO**](ProductDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Product successfully created |  -  |
| **400** | Validation error (RFC9457) |  -  |
| **409** | Conflict — a product with the same name already exists |  -  |
| **500** | Internal server error |  -  |

<a name="deleteProduct"></a>
# **deleteProduct**
> deleteProduct(id)

Delete a product by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProductsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ProductsApi apiInstance = new ProductsApi(defaultClient);
    UUID id = UUID.randomUUID(); // UUID | Unique product identifier
    try {
      apiInstance.deleteProduct(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsApi#deleteProduct");
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
| **id** | **UUID**| Unique product identifier | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/problem+json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Product successfully deleted — no content is returned |  -  |
| **500** | Internal server error |  -  |

<a name="getProduct"></a>
# **getProduct**
> ProductDTO getProduct(id)

Get a product by its ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProductsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ProductsApi apiInstance = new ProductsApi(defaultClient);
    UUID id = UUID.randomUUID(); // UUID | Unique product identifier
    try {
      ProductDTO result = apiInstance.getProduct(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsApi#getProduct");
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
| **id** | **UUID**| Unique product identifier | |

### Return type

[**ProductDTO**](ProductDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Product found and returned |  -  |
| **404** | Product not found |  -  |
| **500** | Internal server error |  -  |

<a name="listProducts"></a>
# **listProducts**
> List&lt;ProductDTO&gt; listProducts(categoryId, minPrice, maxPrice, name)

Get a list of all products

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProductsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ProductsApi apiInstance = new ProductsApi(defaultClient);
    UUID categoryId = UUID.randomUUID(); // UUID | Filter products by category ID
    Double minPrice = 3.4D; // Double | Minimum price for filtering
    Double maxPrice = 3.4D; // Double | Maximum price for filtering
    String name = "name_example"; // String | Filter products by partial name match
    try {
      List<ProductDTO> result = apiInstance.listProducts(categoryId, minPrice, maxPrice, name);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsApi#listProducts");
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
| **categoryId** | **UUID**| Filter products by category ID | [optional] |
| **minPrice** | **Double**| Minimum price for filtering | [optional] |
| **maxPrice** | **Double**| Maximum price for filtering | [optional] |
| **name** | **String**| Filter products by partial name match | [optional] |

### Return type

[**List&lt;ProductDTO&gt;**](ProductDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/problem+json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful response with all available products |  -  |
| **500** | Internal server error |  -  |

<a name="updateProduct"></a>
# **updateProduct**
> ProductDTO updateProduct(id, productUpdateDTO)

Update an existing product by ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ProductsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ProductsApi apiInstance = new ProductsApi(defaultClient);
    UUID id = UUID.randomUUID(); // UUID | Unique product identifier
    ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO(); // ProductUpdateDTO | 
    try {
      ProductDTO result = apiInstance.updateProduct(id, productUpdateDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProductsApi#updateProduct");
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
| **id** | **UUID**| Unique product identifier | |
| **productUpdateDTO** | [**ProductUpdateDTO**](ProductUpdateDTO.md)|  | |

### Return type

[**ProductDTO**](ProductDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, application/problem+json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Product successfully updated |  -  |
| **400** | Validation error (RFC9457) |  -  |
| **404** | Product not found (RFC9457) |  -  |
| **409** | Conflict — cannot update product due to duplicate or invalid data |  -  |
| **500** | Internal server error |  -  |

