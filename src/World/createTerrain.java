package World;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;


public class createTerrain {
    
    Properties props = new Properties();
    ArrayList<String> surfaceArrayY = new ArrayList<String>();
    ArrayList<String> surfaceArrayX = new ArrayList<String>();
    

    //create the basic terrain with caves/dungeons/trees/resources here
    public createTerrain(int x, int y, int z, AssetManager asset, Node root){
        System.out.println(Integer.toString(x) + " " + Integer.toString(y) 
                + " " + Integer.toString(z));
        //creates the first layer of terrain
        this.makeNoiseTerrain(x, y, z, asset, root);
        //just to see my stupid long arrayLists
        //System.out.println(surfaceArrayY);
        //System.out.println(surfaceArrayX);
        this.modifyProps(x, y, z);
    }
    
    public void modifyProps(int x, int y, int z){
        try{
            props.load(new FileInputStream("./assets/Config/"
                    + "World.properties"));
            StringBuilder sX = new StringBuilder();
            for(String s : surfaceArrayX){
                sX.append(s);
                sX.append(",");
            }
            StringBuilder sY = new StringBuilder();
            
            for(String s : surfaceArrayY){
                sY.append(s);
                sY.append(",");
            }
            
            System.out.println(sX.toString());
            System.out.println(sY.toString());
            
            props.setProperty("sizeX", "1000");
            props.setProperty("sizeY", "1");
            props.setProperty("sizeZ", "1");
            props.setProperty("SurfaceX", sX.toString());
            props.setProperty("SurfaceY", sY.toString());
            
            props.store(new FileOutputStream("./assets/Config/"
                    + "World.properties"), null);
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void makeNoiseTerrain(int x, int y, int z, 
            AssetManager asset, Node root){
        //init an instance of random
        //FastNoise noise = new FastNoise();
        PerlinNoise noise = new PerlinNoise();
        //noise along the x axis
        float[][] finalnoise = noise.GeneratePerlinNoise
                (noise.genWhiteNoise(x, y), 8, (float) 0.1);
        //noise along the y axis - adds a little bit of overhangs
        float[][] testnoise = noise.GeneratePerlinNoise
                (noise.genWhiteNoise(y, x), 6, (float) 0.1);
        
        //iterate through the creation of blocks for the length of the world
        for(int i = 0; i <= finalnoise.length - 1; i++){
            for(int j = 0; j <= finalnoise[i].length - 1; j++){
                //System.out.println(finalnoise[i][1]);
                float tempY = finalnoise[i][j] * 120;
                //make it taller
                float testY = testnoise[j][i] * 30;
                //make it tallewr
                int newX = (int)testY + j;
                int newY = (int)tempY + i;
                //PerlinNoise is actually height, width so j is x i is y lol
                this.createBox(newX, newY, 0, asset, root);
                //System.out.println("x: " + newX + " y: " + newY);
                //append the x and y coords to an arraylist
                surfaceArrayX.add(Integer.toString(newX));
                surfaceArrayY.add(Integer.toString(newY));
            }
        }
    }
    
    public void createBox(int x, int y, int z, AssetManager assetManager, 
            Node root){
        //create a box and place it at x, y
        Box b = new Box((float)0.5, (float)0.5, (float)0.5);
        Geometry geom = new Geometry("Box", b);
        geom.setLocalTranslation(new Vector3f(x, y, z));

        Material mat = new Material(assetManager, 
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        root.attachChild(geom);
    }
}
