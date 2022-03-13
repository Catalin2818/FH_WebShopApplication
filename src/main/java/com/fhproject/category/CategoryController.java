package com.fhproject.category;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value="/getAllCategories")
    public String shoeCategoryList(){
        List<Category> listCategory = categoryService.listAll();

        return getJsonObject(listCategory);
    }

    @PostMapping(value = "/addCategories",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveCategory(@RequestBody CategoryDto categoryDto){
        Category category = Category.of(categoryDto);
        categoryService.save(category);

        return "New Category added";
    }

    @PostMapping(value = "/updateCategory",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateCategory(@RequestBody CategoryDto categoryDto){

        try {
            Category category = Category.of(categoryDto);

            Category categoryUpdate = categoryService.get(category.getID());
            categoryUpdate.setID(category.getID());
            categoryUpdate.setCategoryName(category.getCategoryName());

            categoryService.save(categoryUpdate);
            return "Update was successful.";

        } catch (CategoryNotFoundExeption e) {
            e.printStackTrace();
            return "Update was not successful.";
        }
    }

    @GetMapping(value = "/getSpecificCetagory/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getSpecificCategory(@PathVariable("id") int specificId){

        Category category = null;
        try {
            category = categoryService.get(specificId);
            return getJsonObject(List.of(category));
        } catch (CategoryNotFoundExeption e) {
            e.printStackTrace();
            return "Couldn't find specific category";
        }
    }

    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable("id") int deleteId){
        try {
            categoryService.delete(deleteId);
            return "Delete category was successful.";
        } catch (CategoryNotFoundExeption e) {
            e.printStackTrace();
            return "Deleting category was not successful.";
        }
    }

    @NotNull
    private String getJsonObject(List<Category> categoryUpdate){
        List<CategoryDto> categoryDto = categoryUpdate.stream().map(CategoryDto::of).collect(Collectors.toList());
        JSONObject jsonCategoryList = new JSONObject();
        JSONArray jsonArray = new JSONArray(categoryDto);
        jsonCategoryList.put("category",jsonArray);

        System.out.println(jsonCategoryList);
        return jsonCategoryList.toString();
    }


}
