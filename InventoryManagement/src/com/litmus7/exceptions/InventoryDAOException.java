package com.litmus7.exceptions;

public class InventoryDAOException extends Exception
{
		String errorCode;
		
		public InventoryDAOException(String message,Throwable cause)
		{
			super(message,cause);
		}
		
		public InventoryDAOException(String message,String errorCode, Throwable cause)
		{
			super(message,cause);
			this.errorCode=errorCode;
			
		}
		
		String getErrorCode()
		{
			return errorCode;
		}
	}
	
