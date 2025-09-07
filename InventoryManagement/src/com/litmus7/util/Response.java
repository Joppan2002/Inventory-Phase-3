package com.litmus7.util;

public class Response<T,U,V> 
{
	
	T data;
	U applicationStatus;
	V message;
	
	public Response(T data,U applicationStatus,V message)
	{
		this.data=data;
		this.applicationStatus=applicationStatus;
		this.message=message;
	}
	
	public T getData()
	{
		return this.data;
	}
	
	public U getApplicationStatus()
	{
		return this.applicationStatus;
	}
	
	public V getMessage()
	{
		return this.message;
	}

}