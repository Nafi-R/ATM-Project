package javaATM.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import javaATM.*;
import java.util.ArrayList;

public class AdminTest {
	// String name, int adminID, int passcode

	@Test
	void testGetName() {
		Admin testAdmin = new Admin("Joe",1,34);
		String errormsg = String.format("Failed to get name correctly. Expected Joe, got %s", testAdmin.getName());
		assertTrue(testAdmin.getName().equals("Joe"),errormsg);
	}
	
	@Test
	void testGetNameNull() {
		Admin testAdmin = new Admin(null,1,34);
		String errormsg = "Expected null name result, got non-null";
		assertTrue(testAdmin.getName() == null, errormsg);
	}
	
	@Test
	void testGetAdminID() {
		Admin testAdmin = new Admin("Joe",1,34);
		String errormsg = String.format("Failed to get admin ID correctly. Expected 1, got %d", testAdmin.getAdminID());
		assertEquals(testAdmin.getAdminID(),1,errormsg);
	}
	
	
	@Test
	void testGetPasscode() {
		Admin testAdmin = new Admin("Joe",1,34);
		String errormsg = String.format("Failed to get passcode correctly. Expected 34, got %d", testAdmin.getPasscode());
		assertEquals(testAdmin.getPasscode(),34,errormsg);
	}
	
	@Test
	void testSetID() {
		Admin testAdmin = new Admin("Joe",1,34);
		String errormsg = String.format("Failed to set admin ID correctly. Expected 2 after setting, got %d", testAdmin.getAdminID());
		testAdmin.setAdminID(2);
		assertEquals(testAdmin.getAdminID(), 2, errormsg);
	}
	
	@Test
	void testSetName() {
		Admin testAdmin = new Admin("Joe",1,34);
		String errormsg = String.format("Failed to set name correctly. Expected Bob after setting, got %s", testAdmin.getName());
		testAdmin.setName("Bob");
		assertTrue(testAdmin.getName().equals("Bob"), errormsg);
	}
	
	@Test
	void testSetNameNull() {
		Admin testAdmin = new Admin("Joe",1,34);
		assertTrue(testAdmin.getName().equals("Joe"), "Current name is not Joe");
		String errormsg = "Failed to set name correctly. Expected null-name after setting, got non-null";
		testAdmin.setName(null);
		assertTrue(testAdmin.getName() == null, errormsg);
	}
	
	@Test
	void testSetPass() {
		Admin testAdmin = new Admin("Joe",1,34);
		String errormsg = String.format("Failed to set passcode correctly. Expected 3 after setting, got %d", testAdmin.getPasscode());
		testAdmin.setPasscode(3);
		assertEquals(testAdmin.getPasscode(), 3, errormsg);
	}


}
