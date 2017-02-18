package org.seckill.service;

import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

/**
 * @author Aomr
 *
 */
public interface SeckillService {
	
	/**
	 * @param seckillId
	 * @return List<Seckill>
	 * 返回所有的秒杀产品
	 */
	List<Seckill> querySeckillList();
	
	/**
	 * @param seckillId
	 * @return Seckill
	 * 根据id返回单条秒杀产品
	 */
	Seckill getById(long seckillId);
	
	/**
	 * @param seckillId
	 * 秒杀开启时暴露秒杀接口
	 * 否则输出开始时间和当前时间
	 */
	Exposer exportSeckillUrl(long seckillId);

	/**
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 */
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5)
			throws SeckillException,SeckillCloseException,RepeatKillException;
}

