package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(tags = "套餐相关接口")
public class SetMealController {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private SetMealService setMealService;


    @PostMapping
    @ApiOperation(value = "新增套餐接口")
    public Result save(@RequestBody SetmealDTO setmealDTO){
        log.info("新增套餐接口，参数为:{}",setmealDTO);
        setMealService.save(setmealDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation(value = "套餐分页查询接口")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("套餐分页查询接口，参数为:{}",setmealPageQueryDTO);
        PageResult pageResult = setMealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation(value = "批量套餐删除接口")
    public Result delete(@RequestParam List<Long> ids){
        log.info("批量套餐删除接口，参数为:{}",ids);
        setMealService.deleteBatch(ids);
        return Result.success();
    }


    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable Long id){
        SetmealVO setmealVO = setMealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }

    @PutMapping
    @ApiOperation("修改套餐")
    public Result update(@RequestBody SetmealDTO setmealDTO){
        setMealService.update(setmealDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("套餐起售停售")
    public Result startOrStop(@PathVariable Integer status,Long id){
        setMealService.startOrStop(status,id);
        return Result.success();
    }
}
