package com.klezovich.springjsoncoaching.json.nested;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    private String id;
    private String name;
    //key is .brand.name
    private String brandName;
    //key is .owner.name
    private String ownerName;

    @JsonProperty("brand")
    private void unpackNested(Map<String,Object> brand) {
        this.brandName = (String)brand.get("name");
        Map<String,String> owner = (Map<String,String>)brand.get("owner");
        this.ownerName = owner.get("name");
    }
}