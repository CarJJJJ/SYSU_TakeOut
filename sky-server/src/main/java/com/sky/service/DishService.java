package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    public Dish getById(Long id);

    /**
     * 新增菜品
     * @param dishDTO
     */
    public void save(DishDTO dishDTO);

    /**
     * 菜品分页查询
     */
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    public void startOrStop(int status,Long id);

    public void deleteBatch(List<Long> ids);

    public DishVO getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDTO dishDTO);

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);
}
