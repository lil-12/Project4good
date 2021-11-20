import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryReaderTest 
{
	Set<String> keys;
	HashMap<String,String> inventory;
	@BeforeEach
	void getHashMap()
	{
		inventory = InventoryReader.readFile("inventory.xml");
		keys= inventory.keySet();
	}
	
	@Test
	void testQuantity() 
	{
		assert(keys.size()==2);
	}
	
	@Test
	void testCar() 
	{
		
		assert( inventory.get("2").equals("car"));
	}
	
	@Test
	void testBook() 
	{
		
		assert( inventory.get("1").equals("book"));
	}

}
