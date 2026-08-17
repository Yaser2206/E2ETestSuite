package webUtils;
public enum PathsEnum{
    propertiesPath(System.getProperty("user.dir")+ "\\src\\main\\java\\resources\\GlobalConfig.properties"),
    apiPropertiesPath(System.getProperty("user.dir")+ "\\src\\main\\java\\resources\\APITokenConfig.properties"),
    testDataPath(System.getProperty("user.dir")+"\\src\\main\\java\\resources\\testData.xlsx"),
    testJsonPath(System.getProperty("user.dir")+"\\src\\main\\java\\resources\\json\\testData.json");
    private final String path;
    PathsEnum(String path){
        this.path=path;
    }
    public String getPath(){
        return path;
    }

    
}