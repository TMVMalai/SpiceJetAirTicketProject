package testCases;


import org.testng.annotations.Test;
import java.util.Map;


import org.testng.annotations.Test;

import baseFunctions.CommonFunctions;
import spiceJet.Modules.Modules;
import spicejet.DDD.dataproviders.DataProviderClasses;

public class RegistrationTest extends CommonFunctions{
	@SuppressWarnings("unchecked")
	@Test(enabled =false, groups = "Regression",dataProviderClass=DataProviderClasses.class,dataProvider="getRegistrationData")
	public void signupFunctionality(Object data){
		Map<String, Object> testData = (Map<String, Object>) data;
		Modules m = new Modules(testData, driver);
		m.switchTest();
	}

}
