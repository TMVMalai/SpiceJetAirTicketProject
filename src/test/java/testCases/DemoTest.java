package testCases;

import org.testng.annotations.Test;
import java.util.Map;
import baseFunctions.CommonFunctions;
import spiceJet.Modules.Modules;
import spicejet.DDD.dataproviders.DataProviderClasses;

public class DemoTest extends CommonFunctions {
	
	@SuppressWarnings("unchecked")
	@Test(enabled =true, groups = "Regression",dataProviderClass=DataProviderClasses.class,dataProvider="getRegistrationData")
	public void searchFlightFunctionality(Object data) {
		Map<String, Object> testData = (Map<String, Object>) data;
		Modules m = new Modules(testData,driver);
		m.searchOneWayFlight();
	}
	
	@SuppressWarnings("unchecked")
	@Test(enabled =true, groups = "Regression",dataProviderClass=DataProviderClasses.class,dataProvider="getRegistrationData")
	public void roundTripFunctionality(Object data) {
		Map<String, Object> testData = (Map<String, Object>) data;
		Modules m = new Modules(testData,driver);
		m.roundTripSearchFlight();
	}

}
