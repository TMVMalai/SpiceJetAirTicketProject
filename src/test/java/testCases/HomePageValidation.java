package testCases;

import org.testng.annotations.Test;
import java.util.Map;

import org.testng.annotations.Test;

import baseFunctions.CommonFunctions;
import spiceJet.Modules.Modules;
import spicejet.DDD.dataproviders.DataProviderClasses;

public class HomePageValidation extends CommonFunctions{
	
	@SuppressWarnings("unchecked")
	@Test(enabled =true, groups = "Regression",dataProviderClass=DataProviderClasses.class,dataProvider="getRegistrationData")
	public void searchFlightFunctionality(Object data) {
		Map<String, Object> testData = (Map<String, Object>) data;
		Modules m = new Modules(testData,driver);
		
	}

}
