package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.sky.result.Result;
import java.util.*;

import java.util.List;

@RestController("adminDishController")
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品相关接口")
public class DishController {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping
    @ApiOperation(value = "新增菜品")
    public Result save(@RequestBody DishDTO dishDTO){
        //先更新数据库，再删除缓存
        log.info("新增菜品，参数为:{},dishDTO");
        dishService.save(dishDTO);

        //清理缓存数据
        String key = "dish_" + dishDTO.getCategoryId();
        cleanCache(key);
        return Result.success();
    }
    //无缓存逻辑
//    @PostMapping
//    @ApiOperation(value = "新增菜品")
//    public Result save(@RequestBody DishDTO dishDTO){
//        log.info("新增菜品，参数为:{}",dishDTO);
//        dishService.save(dishDTO);
//        return Result.success();
//    }

    @GetMapping("/page")
    @ApiOperation(value = "菜品分页查询接口")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询，参数为:{}",dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    @ApiOperation(value = "菜品启用停售接口")
    public Result startOrStop(@PathVariable int status,Long id){
        log.info("菜品启用或停售，参数为:{},{}",status,id);
        dishService.startOrStop(status,id);
        cleanCache("dish_*");
        return Result.success();

    }
    //无缓存逻辑
//    @PostMapping("/status/{status}")
//    @ApiOperation(value = "菜品启用停售接口")
//    public Result startOrStop(@PathVariable int status,Long id){
//        log.info("菜品启用或停售，参数为:{},{}",status,id);
//        dishService.startOrStop(status,id);
//        return Result.success();
//    }

    @DeleteMapping
    @ApiOperation("菜品批量删除")
    public Result delete(@RequestParam List<Long> ids){
        log.info("菜品批量删除:{}",ids);
        dishService.deleteBatch(ids);
        //批量删除就删掉所有菜品缓存
        cleanCache("dish_*");
        return Result.success();
    }
    //无缓存逻辑
//    @DeleteMapping
//    @ApiOperation("菜品批量删除")
//    public Result delete(@RequestParam List<Long> ids){
//        log.info("菜品批量删除：{}",ids);
//        dishService.deleteBatch(ids);
//        return Result.success();
//    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品和关联的口味数据")
    public Result<DishVO> getById(@PathVariable Long id){
        return Result.success(dishService.getByIdWithFlavor(id));
    }

    @PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("修改菜品：{}",dishDTO);
        dishService.updateWithFlavor(dishDTO);
        cleanCache("dish_*");
        return Result.success();
    }
    //无缓存逻辑
//    @PutMapping
//    @ApiOperation("修改菜品")
//    public Result update(@RequestBody DishDTO dishDTO){
//        log.info("修改菜品：{}",dishDTO);
//        dishService.updateWithFlavor(dishDTO);
//        return Result.success();
//    }


    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

}
