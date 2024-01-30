package spicejet.DDD.dataproviders;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import utils.JsonUtilties;

public class DataProviderClasses {
	JsonUtilties jsonUtils = new JsonUtilties();

	@DataProvider(name ="getRegistrationData")
	public Object[] getJsonData(Method m) {
		return jsonUtils.readMultiJsonData("registrationData.json", m.getName());
	}
}
