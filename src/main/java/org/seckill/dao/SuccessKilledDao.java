package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * @author Aomr
 *
 */
public interface SuccessKilledDao {
	//插入购买信息，可过滤重复
	int insertSuccessKilled(@Param(value="seckillId")long seckillId,@Param(value="userPhone")long userPhone);
	
	//根据id查询成功秒杀记录并携带秒杀产品
	SuccessKilled queryByIdWithSeckill(@Param(value="seckillId")long seckillId,@Param(value="userPhone")long userPhone);
}

