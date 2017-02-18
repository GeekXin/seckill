package org.seckill.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

/**
 * @author Aomr
 *
 */
public interface SeckillDao {
	//减少库存
	int reduceNumber(@Param(value="seckillId")long seckillId,@Param(value="killTime")Date killTime);
	
	//根据id查询商品
	Seckill queryById(long seckillId);
	
	//查询列表 用于分页查询
	List<Seckill> queryAll(@Param(value="offet")int offet,@Param(value="limit")int limit);
}
