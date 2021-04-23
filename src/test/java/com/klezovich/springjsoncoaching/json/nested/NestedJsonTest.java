package com.klezovich.springjsoncoaching.json.nested;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.klezovich.springjsoncoaching.util.FileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Based on https://www.baeldung.com/jackson-nested-values
public class NestedJsonTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
    }

    @Test
    void testCanDeserealiseNestedUsingAnnotation() throws JsonProcessingException {
        var product = mapper.readValue(FileReader.get("json/nested/product.json"), Product.class);

        assertCorrectlyDeserealised(product);
    }

    @Test
    void testCanDeserealizeUsingJsonNode() throws JsonProcessingException {
        var jsonNode = mapper.readTree(FileReader.get("json/nested/product.json"));

        var product = new Product();

        product.setId(jsonNode.get("id").asText());
        product.setName(jsonNode.get("name").asText());
        product.setBrandName(jsonNode.get("brand").get("name").asText());
        product.setOwnerName(jsonNode.get("brand").get("owner").get("name").asText());

        assertCorrectlyDeserealised(product);
    }

    @Test
    void testCanDeserealiseUsingCustomDeserealiser() throws JsonProcessingException {

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Product.class, new ProductDeserializer());
        mapper.registerModule(module);

        var product = mapper.readValue(FileReader.get("json/nested/product.json"),Product.class);

        assertCorrectlyDeserealised(product);
    }

    static class ProductDeserializer extends StdDeserializer<Product> {

        public ProductDeserializer() {
            this(null);
        }

        public ProductDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Product deserialize(JsonParser jp, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {

            JsonNode productNode = jp.getCodec().readTree(jp);
            Product product = new Product();
            product.setId(productNode.get("id").textValue());
            product.setName(productNode.get("name").textValue());
            product.setBrandName(productNode.get("brand")
                    .get("name").textValue());
            product.setOwnerName(productNode.get("brand").get("owner")
                    .get("name").textValue());
            return product;
        }
    }


    private void assertCorrectlyDeserealised(Product product) {
        assertEquals("957c43f2-fa2e-42f9-bf75-6e3d5bb6960a", product.getId());
        assertEquals("The Best Product", product.getName());
        assertEquals("ACME Products", product.getBrandName());
        assertEquals("Ultimate Corp, Inc.", product.getOwnerName());
    }

}
