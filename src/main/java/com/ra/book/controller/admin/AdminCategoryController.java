package com.ra.book.controller.admin;

import com.ra.book.model.entity.Category;
import com.ra.book.model.entity.dto.CategoryDTO;
import com.ra.book.model.service.Category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {
    public static String oldName = "";
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String index(
            @RequestParam(name = "page",defaultValue = "0") Integer page,
            @RequestParam(name = "size",defaultValue = "3") Integer size,
            Model model) {
        // search
        // sort by cate

        if(page +1 > categoryService.totalPages(size) || page < 0){
            page = (categoryService.totalPages(size) - 1);
        }
        model.addAttribute("totalPages",categoryService.totalPages(size));
        model.addAttribute("size", size);
        model.addAttribute("categories", categoryService.findAll(page, size));
        model.addAttribute("page", page);
        System.out.println((page));

        return "admin/category/index";
    }


    @GetMapping("/add")
    public String add(Model model) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setOldName("");
        model.addAttribute("categoryDTO", categoryDTO);
        return "admin/category/add";
    }

    @PostMapping("/add")
    public String create(@Valid @ModelAttribute("categoryDTO") CategoryDTO categoryDTO, BindingResult result,Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categoryDTO", categoryDTO);
            return "admin/category/add";
        } else {
            if(categoryService.create(categoryDTO)){
                return "redirect:/admin/category";
            }
                return "redirect:/admin/category/add";
            }
    }
    //
    @GetMapping ("/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        Category category = categoryService.findById(id);
        System.out.println(category.getName());
        System.out.println(category.getId());
        System.out.println(category.isStatus());
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(category.getName());
        categoryDTO.setOldName(category.getName());
        oldName = categoryDTO.getName();
        categoryDTO.setStatus(category.isStatus());
        model.addAttribute("oldName", oldName);
        model.addAttribute("categoryDTO", categoryDTO);
        return "admin/category/edit";
    }
    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") int id,
                         @Valid @ModelAttribute("categoryDTO") CategoryDTO categoryDTO,
                         BindingResult result,
                         Model model) {
        Category category = categoryService.findById(id);
        category.setId(id);
        category.setName(categoryDTO.getName());
        category.setStatus(categoryDTO.isStatus());
        if(result.hasErrors()) {
            model.addAttribute("categoryDTO", categoryDTO);
            return "admin/category/edit";
        }
        if(categoryService.update(categoryDTO, id)){
            return "redirect:/admin/category";
        }
        return "redirect:/admin/category/edit" + id;
    }
// check k ddc
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        categoryService.delete(id);
        return "redirect:/admin/category";
    }
}
