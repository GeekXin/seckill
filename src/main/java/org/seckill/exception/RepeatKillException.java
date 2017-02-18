package org.seckill.exception;

/**
 * @author Aomr
 * 重复秒杀异常
 */
@SuppressWarnings("serial")
public class RepeatKillException extends SeckillException{
	public RepeatKillException(String message){
		super(message);
	}
	public RepeatKillException(String message,Throwable cause){
		super(message,cause);
	}
}
