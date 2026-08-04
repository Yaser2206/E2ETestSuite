package webUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
	private static Properties prop=new Properties();
	public static String takeScreenshot(String ssName) throws IOException {
		TakesScreenshot ts=(TakesScreenshot)(BaseTest.getWebDriver());
		File ss;
		ss=ts.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(ss,new File(System.getProperty("user.dir")+"/screenshots/"+ssName+".png"));
		return System.getProperty("user.dir")+"/screenshots/"+ssName+".png";
	}
	
	public static void waitForVisibility(long seconds,By locator) {
		WebDriverWait wait=new WebDriverWait(BaseTest.getWebDriver(),Duration.ofSeconds(seconds));
		wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public static void waitForVisibility(long seconds,WebElement webElement) {
		WebDriverWait wait=new WebDriverWait(BaseTest.getWebDriver(),Duration.ofSeconds(seconds));
		wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf(webElement));
	}
	
	public static void scrollToViewElement(WebElement element) {
		JavascriptExecutor js=(JavascriptExecutor)BaseTest.getWebDriver();
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public static void clickByLocator(By locator) {
		BaseTest.getWebDriver().findElement(locator).click();
	}
	
	public static void writeToGlobalConfig(String key,String value) throws IOException {
		FileInputStream fis=new FileInputStream(BaseTest.propertiesPath);
		FileOutputStream fos=new FileOutputStream(BaseTest.propertiesPath);
		prop.load(fis);
		prop.setProperty(key, value);
		prop.store(fos, "Key added");
		fis.close();
		fos.close();
	}
	
	public static void writeToApiConfig(String key,String value) throws IOException {
		FileInputStream fis=new FileInputStream(BaseTest.apiPropertiesPath);
		FileOutputStream fos=new FileOutputStream(BaseTest.apiPropertiesPath);
		prop.load(fis);
		prop.setProperty(key, value);
		prop.store(fos, "Key added");
		fis.close();
		fos.close();
	}//apiPropertiesPath
	
	public static String readApiProperty(String key) throws IOException {
		FileInputStream fis=new FileInputStream(BaseTest.apiPropertiesPath);
		prop.load(fis);
		String value=prop.getProperty(key);
		fis.close();
		return value;
	}
	
	public static String readGlobalProperty(String key) throws IOException {
		FileInputStream fis=new FileInputStream(BaseTest.propertiesPath);
		prop.load(fis);
		String value=prop.getProperty(key);
		fis.close();
		return value;
	}
	@SuppressWarnings({ "resource", "rawtypes", "unused" })
	public static List readExcelValuesList(String sheetName, String title) throws IOException {
		FileInputStream fis=new FileInputStream(BaseTest.testDataPath);
		Workbook book=new XSSFWorkbook(fis);
		List<String> excelValues = null;
		try {
			Sheet sheet=book.getSheet(sheetName);
			Row row = null;
			Cell cell;
			excelValues = new ArrayList<>();;
			int lastRow=sheet.getLastRowNum();
			int lastCol=0;
			int titleCol = 0;
			String value;
			if(lastRow>0) {
				row=sheet.getRow(0);
				lastCol=row.getLastCellNum();
			}
			for(int i=0;i<lastCol;i++){
				if(sheet.getRow(0).getCell(i).getStringCellValue().equals(title)) {
					titleCol=i;
					break;
				}
			}
			for(int i=1;i<lastRow;i++) {
				value=sheet.getRow(i).getCell(titleCol).getStringCellValue();
				excelValues.add(value);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fis.close();
			e.printStackTrace();
		}
		return excelValues;
	}
	
	@SuppressWarnings("deprecation")
	public static String readJson(String key) throws IOException {
		Map<String, String> map=new HashMap<>();
		String jsonStr=FileUtils.readFileToString(new File(BaseTest.testJsonPath));
		ObjectMapper mapper=new ObjectMapper();
		map=mapper.readValue(jsonStr, new TypeReference<Map<String, String>>() {});
		return map.get(key);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
