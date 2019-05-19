package demo;

import static org.junit.Assert.*;

import org.junit.Test;

import demo.dao.DAOUtils;

public class SomeSimpleDummyTest {

	@Test
	public void testGetDateForHSQLDB() {
		assertEquals("1976-05-19", DAOUtils.getDateForHSQLDB("19.05.1976"));
	}

}
