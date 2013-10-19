
package World;

// World is generated at random then saved as a binary file

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

public class createWorld {
    //create the variables used for the size 
    //of the world in the properties file
    private int sizeX;
    private int sizeY;
    private int sizeZ;
    
    public createWorld(String name, AssetManager asset, Node root){
        //will eventually be used to name the world file
        System.out.println(name);
        //check for properties file
        //if not found create one with default props
        handleWorldConfig confighandler = new handleWorldConfig();
        //load the props to see how big to make the world
        this.loadProps();
        //begin creating terrain for given world size
        createTerrain terrain = new createTerrain(sizeX, sizeY, sizeZ, 
                asset, root);
    }
    
    public void loadProps(){
        //load the props
        Properties props = new Properties();
        
        try{
            props.load(new FileInputStream("./assets/Config/"
                    + "World.properties"));
            sizeX = Integer.parseInt(props.getProperty("sizeX"));
            sizeY = Integer.parseInt(props.getProperty("sizeY"));
            sizeZ = Integer.parseInt(props.getProperty("sizeZ"));
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
