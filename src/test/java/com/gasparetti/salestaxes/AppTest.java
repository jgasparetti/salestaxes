package com.gasparetti.salestaxes;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.gasparetti.salestaxes.helpers.DoubleHelperTest;
import com.gasparetti.salestaxes.helpers.StringHelperTest;
import com.gasparetti.salestaxes.services.BillServiceImplTest;
import com.gasparetti.salestaxes.services.TaxServiceImplTest;

import junit.framework.TestCase;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	DoubleHelperTest.class, 
	StringHelperTest.class, 
	BillServiceImplTest.class, 
	TaxServiceImplTest.class})

public class AppTest extends TestCase {
	
}
