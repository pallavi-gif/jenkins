package appiumFramework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class Base {
	 public static AppiumDriverLocalService service;
	  public static AndroidDriver<AndroidElement>  driver;
	
	public AppiumDriverLocalService startServer()
	{
		//
	boolean flag=checkIfServerIsRunnning(4723);
	if(!flag)
	{
		
		service=AppiumDriverLocalService.buildDefaultService();
		service.start();
	}
		return service;
		
	}
	
public static boolean checkIfServerIsRunnning(int port) {
		
		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			
			serverSocket.close();
		} catch (IOException e) {
			//If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}

	public static  AndroidDriver<AndroidElement> capabilities(String Genralapp) throws IOException
	{
		
		
		FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\appiumFramework\\global.properties");
		Properties pro = new Properties();
		pro.load(file);
	
		
//		AndroidDriver<AndroidElement>  driver;                                       
	

		// TODO Auto-generated method stub
	 File appDir = new File("src");
     File app = new File(appDir,(String) pro.get(Genralapp));
    
     
     DesiredCapabilities cap = new DesiredCapabilities();
     
     String dev= (String) pro.get("device");
     cap.setCapability(MobileCapabilityType.DEVICE_NAME, dev);
     
     cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
     cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 14);
//     cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.google.android.apps.maps");
//     cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.google.android.apps.maps.MapsActivity");
     
     
     cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
     cap.setCapability("autoGrantPermissions", true);
     cap.setCapability("noReset", true);
  driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"),cap);
 return driver;  
	}
	
	public static void getScreenshot(String s) throws IOException
	{
	File scrfile=	((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	FileUtils.copyFile(scrfile,new File(System.getProperty("user.dir")+"\\"+s+".png"));
	
	}
	

}