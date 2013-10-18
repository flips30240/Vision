
package World;

// World is generated at random then saved as a binary file

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

public class createWorld {
    int sizeX;
    int sizeY;
    int sizeZ;
    
    public createWorld(String name, AssetManager asset, Node root){
        System.out.println(name);
        handleWorldConfig confighandler = new handleWorldConfig();
        this.loadProps();
        createTerrain terrain = new createTerrain(sizeX, sizeY, sizeZ, 
                asset, root);
    }
    
    public void loadProps(){
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
