package World;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public final class handleWorldConfig {
    
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
        if (listOfFiles.length == 0){
            //create a world.properties file
                    System.out.println("No World Config File:"
                            + " Creating one now");
                    this.createPropertiesFile();
    }
        else{
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
                        this.createPropertiesFile();
                    }
                }
        }
    }
    }
    
    public void createPropertiesFile(){
        Properties props = new Properties();
        try{
            props.setProperty("sizeX", "100");
            props.setProperty("sizeY", "50");
            props.setProperty("sizeZ", "25");
            
            props.store(new FileOutputStream("./assets/Config/"
                    + "World.properties"), null);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}