package World;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.Properties;
import java.util.Random;


public class createTerrain {
    
    Properties props = new Properties();
    //create the basic terrain with caves/dungeons/trees/resources here
    public createTerrain(int x, int y, int z, AssetManager asset, Node root){
        System.out.println(Integer.toString(x) + " " + Integer.toString(y) 
                + " " + Integer.toString(z));
        //creates the first layer of terrain
        //this.makeTerrain(x, y, z, asset, root);
        this.makeNoiseTerrain(x, y, z, asset, root);
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
                    for(int b = 0; b <= z; b++){
                    //System.out.println(finalnoise[i][1]);
                    float tempY = finalnoise[i][j] * 120;
                    //make it taller
                    float testY = testnoise[j][i] * 30;
                    //make it tallewr
                    int newtestY = (int)testY;
                    int newY = (int)tempY;
                    //PerlinNoise is actually height, width so j is x i is y lol
                    this.createBox(newtestY + j, newY + i, b, asset, root);
                    System.out.println(newY);
                    }
            }
        }
    }
    
    public void createBox(int x, int y, int z, AssetManager assetManager, Node root){
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
