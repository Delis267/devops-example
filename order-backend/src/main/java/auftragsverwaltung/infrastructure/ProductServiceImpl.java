package auftragsverwaltung.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ProductServiceImpl(RestTemplate restTemplate, @Value("${product.api.base-url:http://localhost:8081/product-api}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public ProductDTO getProductById(Integer id) {
        String url = baseUrl + "/product-api/products/{id}";
        return restTemplate.getForObject(url, ProductDTO.class, id);
    }
}
