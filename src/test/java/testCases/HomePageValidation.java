package testCases;

import org.testng.annotations.Test;
import java.util.Map;


import baseFunctions.CommonFunctions;
import spiceJet.Modules.Modules;
import spicejet.DDD.dataproviders.DataProviderClasses;

public class HomePageValidation extends CommonFunctions{
	
	@SuppressWarnings("unchecked")
	@Test(enabled =true, groups = "Regression",dataProviderClass=DataProviderClasses.class,dataProvider="getHomePageData")
	public void HomePageValidationFunctionality(Object data) {
		Map<String, Object> testData = (Map<String, Object>) data;
		Modules m = new Modules(testData,driver);
		m.HomePageValidation();
	}

}
