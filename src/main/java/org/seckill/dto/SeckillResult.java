package org.seckill.dto;

public class SeckillResult<T> {
	public boolean success;
	public T data;
	public String error;
	public boolean isSuccess() {
		return success;
	}
	//秒杀成功
	public SeckillResult(boolean success, T data) {
		super();
		this.success = success;
		this.data = data;
	}
	
	//秒杀失败
	public SeckillResult(boolean success, String error) {
		super();
		this.success = success;
		this.error = error;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
}

