package webUtils;
public enum PathsEnum{
    propertiesPath(System.getProperty("user.dir")+ "\\src\\main\\resources\\GlobalConfig.properties"),
    apiPropertiesPath(System.getProperty("user.dir")+ "\\src\\main\\resources\\APITokenConfig.properties"),
    testDataPath(System.getProperty("user.dir")+"\\src\\test\\resources\\ExcelDataSheets\\testData.xlsx"),
    testJsonPath(System.getProperty("user.dir")+"\\src\\test\\resources\\json\\testData.json");
    private final String path;
    PathsEnum(String path){
        this.path=path;
    }
    public String getPath(){
        return path;
    }

    
}