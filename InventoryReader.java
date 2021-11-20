/*
Lily Davis
Project 4
Nov 19, 2021
*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class InventoryReader 
{	
	public static void main(String []args)
	{
		
		HashMap<String,String> inventory = readFile("inventory.txt");
		for (String key : inventory.keySet()) 
		{
			System.out.print("Key = " + key + ", ");
			System.out.println("Data = " + inventory.get(key));
		}
	}
	
	//reads an xml file of the things in the stores inventory
	public static HashMap<String,String> readFile(String filename)
	{
		HashMap <String,String> inventory = new HashMap <String,String>();//hashmap returned full of info about items in store
		try 
		{
			File file = new File(filename);//xml file to read from
			BufferedReader in = new BufferedReader(new FileReader(file));
			String thisLine = removeTab(in.readLine());//next line in xml file
			while(thisLine!=null)
			{
				if(removeTab(thisLine).equals("<PRODUCT>"))
				{
					thisLine = removeTab(in.readLine());
					String stockNumber= "";
					String description="";
					
					while(!thisLine.equals("</PRODUCT>"))//reads in a product
					{
						if(thisLine.equals("<stockNumber>"))//reads stock number
						{
							thisLine = removeTab(in.readLine());
							while(!thisLine.equals("</stockNumber>"))
							{
								stockNumber = stockNumber.concat(thisLine);
								thisLine = removeTab(in.readLine());
							}
						}
						else if(thisLine.equals("<description>"))//reads description
						{
							thisLine = removeTab(in.readLine());
							while(!thisLine.equals("</description>"))
							{
								description=description.concat(thisLine);
								thisLine = removeTab(in.readLine());
							}
						}
						thisLine = removeTab(in.readLine());
					}
					if(!description.equals("")&&!stockNumber.equals(""))
					{
						inventory.put(stockNumber, description);
					}	
				}	
				thisLine = removeTab(in.readLine());
			}
			in.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return inventory;	
	}
	
	private static String removeTab(String string)//removes the tabs from the front of a line
	{
		if(string == null)
			return string;
		else return string.substring(string.lastIndexOf("\t")+1);
	}
		
}
