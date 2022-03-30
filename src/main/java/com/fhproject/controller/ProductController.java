package com.fhproject.controller;

import com.fhproject.dto.ProductDto;
import com.fhproject.entity.Product;
import com.fhproject.errorHandling.ProductNotFoundExeption;
import com.fhproject.service.ProductService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin // CQRS ( = Command-Query-Responsibility-Segregation) is a Database design pattern for Database query.
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired private ProductService service;

    @GetMapping(value = "/getAllProducts")
    public String showProductList(){
        List<Product> listProducts = service.listAll();
        List<ProductDto> newListProducts =
                listProducts
                        .stream()
                        .map(product -> new ProductDto(product.getId(), product.getProductName(),
                                product.getProductOrigin(), product.getProductDesc(), product.getProductAllergens(), product.getProductPrice(),
                                product.getProductQuantity(), product.getProductCategory(), product.getProductPickedUp(),
                                service.encodeBase64(product.getImage())))
                        .collect(Collectors.toList());

       return getJsonObjectOfDto(newListProducts);
    }

    @GetMapping(value = "/admin/getAllProducts")
    public String showProductListAdmin(){
        List<Product> listProducts = service.listAll();
        List<ProductDto> newListProducts =
                listProducts
                        .stream()
                        .map(product -> new ProductDto(product.getId(), product.getProductName(),
                                product.getProductOrigin(), product.getProductDesc(), product.getProductAllergens(), product.getProductPrice(),
                                product.getProductQuantity(), product.getProductCategory(), product.getProductPickedUp(),
                                product.getImage()))
                        .collect(Collectors.toList());

        return getJsonObjectOfDto(newListProducts);
    }

    @GetMapping(value = "/getAllProductsOfCategory/{category}")
    public ResponseEntity<String> showProductListOfCategory(@PathVariable("category") String category) {
        try {
            List<Product> productList = service.getProductsOfCategory(category);
            List<ProductDto> newListProducts =
                    productList
                            .stream()
                            .map(product -> new ProductDto(product.getId(), product.getProductName(),
                                    product.getProductOrigin(), product.getProductDesc(), product.getProductAllergens(), product.getProductPrice(),
                                    product.getProductQuantity(), product.getProductCategory(), product.getProductPickedUp(),
                                    service.encodeBase64(product.getImage())))
                            .collect(Collectors.toList());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(getJsonObjectOfDto(newListProducts));

        } catch (ProductNotFoundExeption e) {
            System.out.println("Could not find any products of category " + category);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Could not find any products of category");
    }

    @PostMapping(value = "/addProducts",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveProduct(@RequestBody ProductDto productDto){
        Product product = Product.of(productDto);
        service.save(product);

        return "New Product added";
    }

    @PostMapping(value = "/updateProduct", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateProduct(@RequestBody ProductDto productDto){
        try {
            Product product = Product.of(productDto);

            Product productUpdate = service.get(product.getId());
            productUpdate.setId(product.getId());
            productUpdate.setProductName(product.getProductName());
            productUpdate.setProductOrigin(product.getProductOrigin());
            productUpdate.setProductDesc(product.getProductDesc());
            productUpdate.setProductAllergens(product.getProductAllergens());
            productUpdate.setProductPrice(product.getProductPrice());
            productUpdate.setProductQuantity(product.getProductQuantity());
            productUpdate.setProductCategory(product.getProductCategory());
            productUpdate.setProductPickedUp(product.getProductPickedUp());
            productUpdate.setImage(product.getImage());

            service.save(productUpdate);
            return "Update was seccessful.";

        } catch (ProductNotFoundExeption e) {
            e.printStackTrace();
            return "Update was not successful.";
        }

    }


    @GetMapping(value="/getSpecificProduct/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getSpecificProduct(@PathVariable("id") int specificId){
        try {
            Product product = service.get(specificId);
            product.setImage(service.encodeBase64(product.getImage()));
            return getJsonObject(List.of(product));

        } catch (ProductNotFoundExeption e) {
            e.printStackTrace();
            return "Couldn't find specific product.";
        }
    }



    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") int deleteId){
        try {
            service.delete(deleteId);
            return "Delete product was successful";
        } catch (ProductNotFoundExeption e) {
            e.printStackTrace();
            return "Delete was not successful.";
        }
    }


    @NotNull
    private String getJsonObject(List<Product> productUpdate) {
        List<ProductDto> productDto = productUpdate.stream().map(ProductDto::of).collect(Collectors.toList());
        JSONObject jsonProductList = new JSONObject();
        JSONArray jsonArray = new JSONArray(productDto);
        jsonProductList.put("product", jsonArray);

        //System.out.println(jsonProductList);
        return jsonProductList.toString();
    }

    @NotNull
    private String getJsonObjectOfDto(List<ProductDto> productUpdate) {
        JSONObject jsonProductList = new JSONObject();
        JSONArray jsonArray = new JSONArray(productUpdate);
        jsonProductList.put("product", jsonArray);

        //System.out.println(jsonProductList);
        return jsonProductList.toString();
    }
}
