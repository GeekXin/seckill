package org.seckill.service.Impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Component
public class SeckillServiceImpl implements SeckillService{
	@Resource
	private SeckillDao seckillDao;
	@Resource
	private SuccessKilledDao successKilledDao;
	
	private final String slat="djfaoeur83roq*(y98qtaijfa";
	
	/* (non-Javadoc)
	 * @see org.seckill.service.SeckillService#querySeckillList(long)
	 * 获取秒杀商品列表 分页查询
	 */
	@Override
	public List<Seckill> querySeckillList() {
		return seckillDao.queryAll(0, 5);
	}

	
	/* (non-Javadoc)
	 * @see org.seckill.service.SeckillService#getById(long)
	 *  根据id查询商品信息
	 */
	@Override
	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	@Override
	@Transactional
	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill=seckillDao.queryById(seckillId);
		if(seckill==null){
			return new Exposer(false,seckillId);
		}
		Date startTime=seckill.getStartTime();
		Date endTime=seckill.getEndTime();
		Date nowTime=new Date();
		if(nowTime.getTime()<=startTime.getTime()||nowTime.getTime()>=endTime.getTime()){
			return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
		}
		String md5=getMD5(seckillId);
		Exposer exposer=new Exposer(true,md5,seckillId);
		return exposer;
	}
	private String getMD5(long seckillId){
		String base=seckillId+"/"+slat;
		String md5=DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	@Override
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, SeckillCloseException, RepeatKillException {
		if(md5==null||!md5.equals(getMD5(seckillId))){
			//接口被篡改
			throw new SeckillException("接口被篡改");
		}
		//执行业务逻辑，减少库存，增加购买明细
		Date killTime=new Date();
		try{
			int updateCount=seckillDao.reduceNumber(seckillId, killTime);
			if(updateCount<=0){
				throw new SeckillCloseException("秒杀结束");
			}else{
				//记录购买行为
				int insertCount=successKilledDao.insertSuccessKilled(seckillId, userPhone);
				if(insertCount<=0){
					throw new RepeatKillException("重复秒杀");
				}else{
					//秒杀成功
					SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId,SeckillStateEnum.SUCCESS,successKilled);
				}
			}
		}catch(SeckillCloseException e1){
			throw e1;
		}catch(RepeatKillException e2){
			throw e2;
		}catch(Exception e){
			throw new SeckillException(e.getMessage());
		}
	}

}

