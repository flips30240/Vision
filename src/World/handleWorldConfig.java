package World;

import java.io.File;

public class handleWorldConfig {
    
    public handleWorldConfig(){
        this.checkForConfigFile();
        //i need to learn how to fix this
    }
    
    public void checkForConfigFile(){
        //check for world.properties file
        String path = "./assets/Config/";
        String files;
        File folder = new File(path);
        File [] listOfFiles = folder.listFiles();
        
        //check for world.properties file
        for (int i = 0; i < listOfFiles.length; i++){
            if (listOfFiles[i].isFile()){
                files = listOfFiles[i].getName();
                if (files.equals("World.properties")){
                    System.out.println("Found World Config File");
                }
                else{
                    //create a world.properties file
                    System.out.println("No World Config File:"
                            + " Creating one now");
                }
            }
    }
    }
}