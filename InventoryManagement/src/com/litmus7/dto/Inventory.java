package com.litmus7.dto;

public class Inventory {
	
		private int SKU;
		private String ProductName;
		private int Quantity;
		private float Price;
		

		public Inventory(int SKU,String ProductName,int Quantity,float price)
		{
			this.SKU=SKU;
			this.ProductName=ProductName;
			this.Quantity=Quantity;
			this.Price=price;
		}
		
		public int getSKU()
		{
			return SKU;
		}
		
		public String getProductName()
		{
			return ProductName;
		}
		
		public int getQuantity()
		{
			return Quantity;
		}
		
		public float getPrice()
		{
			return Price;
		}

}
