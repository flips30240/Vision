package World;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class createTerrain {
    
    Properties props = new Properties();
    //create the basic terrain with caves/dungeons/trees/resources here
    public createTerrain(int x, int y, int z, AssetManager asset, Node root){
        System.out.println(Integer.toString(x) + " " + Integer.toString(y) 
                + " " + Integer.toString(z));
        //creates the first layer of terrain
        this.makeTerrain(x, y, z, asset, root);
    }
    
    public void makeTerrain(int x, int y, int z, AssetManager asset, Node root){
        //init an instance of random
        Random ran = new Random();
        
        //this is the height of the previous block
        int tempY = 0;
        
        //iterate through the creation of blocks for the length of the world
        for(int i = 0; i <= x; i++){
            //50% chance to go up (up to 3 blocks higher)
            if(ran.nextInt(2) == 1){
                int newY = tempY + ran.nextInt(3);
                this.createBox(i, newY, asset, root);
                tempY = newY;
             //50% chance to go down(up to 3 blocks lower)
            }else{
                int newY = tempY - ran.nextInt(3);
                this.createBox(i, newY, asset, root);
                tempY = newY;
            }
        }
    }
    
    public void createBox(int x, int y, AssetManager assetManager, Node root){
        //create a box and place it at x, y
        Box b = new Box((float)0.5, (float)0.5, (float)0.5);
        Geometry geom = new Geometry("Box", b);
        geom.setLocalTranslation(new Vector3f(x, y, 0));

        Material mat = new Material(assetManager, 
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        root.attachChild(geom);
    }
}
