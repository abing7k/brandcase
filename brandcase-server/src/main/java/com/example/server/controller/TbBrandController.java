package com.example.server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.pojo.TbBrand;
import com.example.server.service.ITbBrandService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hanbing
 * @since 2022-10-29
 */
@RestController
@CrossOrigin
@RequestMapping("/")
public class TbBrandController {
    @Autowired
    ITbBrandService tbBrandService;


    @ApiOperation(value = "查询所有角色")
    @GetMapping("/selectAll")
    public List<TbBrand> selectAll() {
        return tbBrandService.list();
    }

    @ApiOperation(value = "条件查询")
    @PostMapping("/selectQuery")
    public List<TbBrand> selectQuery(@RequestBody TbBrand tbBrand) {
        System.out.println(tbBrand.toString());
        QueryWrapper<TbBrand> queryWrapper = new QueryWrapper<>();
        if (tbBrand != null) {
            if (tbBrand.getStatus() != null) {
                queryWrapper.eq("status", tbBrand.getStatus());
            }
            if (tbBrand.getCompanyName() != null && tbBrand.getCompanyName().length() > 0) {
                queryWrapper.eq("company_name", tbBrand.getCompanyName());
            }
            if (tbBrand.getBrandName() != null && tbBrand.getBrandName().length() > 0) {
                queryWrapper.eq("brand_name", tbBrand.getBrandName());
            }
        }
        return tbBrandService.list(queryWrapper);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/deleteById")
    public Boolean deleteById(@RequestBody Integer id) {
        System.out.println(id);
        return tbBrandService.removeById(id);
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping("/deleteByIds")
    @Transactional
    public Boolean deleteByIds(@RequestBody Integer[] ids) {
        System.out.println(ids);
        Boolean result = false;
        for (int i = 0; i < ids.length; i++) {
            result = tbBrandService.removeById(ids[i]);
        }
        return result;
    }

    @ApiOperation(value = "修改")
    @PostMapping("/updateById")
    public Boolean updateById(@RequestBody TbBrand tbBrand) {
        return tbBrandService.updateById(tbBrand);
    }


}
