package World;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;


public class createTerrain {
    
    Properties props = new Properties();
    Random ran = new Random();
    PerlinNoise noise = new PerlinNoise();
    int iterationCount= 0;
    ArrayList<String> surfaceArrayY = new ArrayList<String>();
    ArrayList<String> surfaceArrayX = new ArrayList<String>();
    ArrayList<String> caveCoordsX = new ArrayList<String>();
    ArrayList<String> caveCoordsY = new ArrayList<String>();
    

    //create the basic terrain with caves/dungeons/trees/resources here
    public createTerrain(int x, int y, int z, AssetManager asset, Node root){
        System.out.println(Integer.toString(x) + " " + Integer.toString(y) 
                + " " + Integer.toString(z));
        //creates the first layer of terrain
        this.makeSurface(x, y, z, asset, root);
        //just to see my stupid long arrayLists
        //System.out.println(surfaceArrayY);
        //System.out.println(surfaceArrayX);
        this.modifyProps(x, y, z);
        this.fillBelowSurface();
    }
    
    public void fillBelowSurface(){
        
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
            
            StringBuilder caveX = new StringBuilder();
            for(String s : caveCoordsX){
                caveX.append(s);
                caveX.append(",");
            }
            StringBuilder caveY = new StringBuilder();
            
            for(String s : caveCoordsY){
                caveY.append(s);
                caveY.append(",");
            }
            
            System.out.println(sX.toString());
            System.out.println(sY.toString());
            System.out.println(caveX.toString());
            System.out.println(caveY.toString());
            
            props.setProperty("sizeX", "10000");
            props.setProperty("sizeY", "1000");
            props.setProperty("sizeZ", "1");
            props.setProperty("SurfaceX", sX.toString());
            props.setProperty("SurfaceY", sY.toString());
            props.setProperty("caveCoordsX", caveX.toString());
            props.setProperty("caveCoordsY", caveY.toString());
            
            
            props.store(new FileOutputStream("./assets/Config/"
                    + "World.properties"), null);
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void makeSurface(int x, int y, int z, 
            AssetManager asset, Node root){
        //noise along the x axis
        float[][] finalnoise = noise.GeneratePerlinNoise
                (noise.genWhiteNoise(x, 1), 8, (float) 0.1);
        //noise along the y axis - adds a little bit of overhangs
        float[][] testnoise = noise.GeneratePerlinNoise
                (noise.genWhiteNoise(1, x), 6, (float) 0.1);
        
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
                this.createBox(newX, newY, 0, "Blue", asset, root);
                
                if(ran.nextInt(200) == 4){
                    if(newY <= 10 || newY >= 70){
                        this.makeCave(newX, newY, ran.nextInt(600), asset, root);
                    }
                }
                //System.out.println("x: " + newX + " y: " + newY);
                //append the x and y coords to an arraylist
                surfaceArrayX.add(Integer.toString(newX));
                surfaceArrayY.add(Integer.toString(newY));
            }
        }
    }
    
    public void makeCave(int x, int y, int endY, AssetManager asset, Node root){
        System.out.println("This is a cave!");
        //total dustance from surface to bottom of world
        int distY = y + endY;
        //generate the perlinNoise for the cave path
        float[][] testnoise = noise.GeneratePerlinNoise
                (noise.genWhiteNoise(1, distY), 5, (float) 0.2);
        
        if(ran.nextInt(100) >= 33){
            for(int i = 0; i <= testnoise.length - 1; i++){
                for(int j = 0; j <= testnoise[i].length - 1; j++){
                    float tempX = testnoise[i][j] * 40;
                    int newX = (int)tempX;
                    int finalX = x + newX;
                    int newY = (0-i) + y;
                    for(int g = 0; g <= 7; g++){
                        this.createBox(finalX + g, newY, 0, "Green", asset, 
                                root);
                        caveCoordsX.add(Integer.toString(finalX + g));
                        caveCoordsY.add(Integer.toString(newY));
                    }
                    if(ran.nextInt(700) == 100){
                        this.makeCaveBranch(finalX, newY, ran.nextInt(600), 
                                asset, root);
                    }
                }
            }
        }else if(ran.nextInt(100)>= 66){
            for(int i = 0; i <= testnoise.length - 1; i++){
                for(int j = 0; j <= testnoise[i].length - 1; j++){
                    float tempX = testnoise[i][j] * 40;
                    int newX = (int)tempX;
                    int finalX = x + newX;
                    int newY = (0-i) + y;
                    for(int g = 0; g <= 7; g++){
                        this.createBox(finalX + g + i, newY, 0, "Green", asset, 
                                root);
                        caveCoordsX.add(Integer.toString(finalX + g + i));
                        caveCoordsY.add(Integer.toString(newY));
                    }
                    if(ran.nextInt(700) == 100){
                        this.makeCaveBranch(finalX, newY, ran.nextInt(600), 
                                asset, root);
                    }
                }
            }
        }else{
            for(int i = 0; i <= testnoise.length - 1; i++){
                for(int j = 0; j <= testnoise[i].length - 1; j++){
                    float tempX = testnoise[i][j] * 40;
                    int newX = (int)tempX;
                    int finalX = x + newX;
                    int newY = (0-i) + y;
                    for(int g = 0; g <= 7; g++){
                        this.createBox(finalX + g - i, newY, 0, "Green", asset, 
                                root);
                        caveCoordsX.add(Integer.toString(finalX + g - i));
                        caveCoordsY.add(Integer.toString(newY));
                    }
                    if(ran.nextInt(700) == 100){
                        this.makeCaveBranch(finalX, newY, ran.nextInt(600), 
                                asset, root);
                    }
                }
            }
        }
    }
    
    public void makeCaveBranch(int x, int y, int endX, 
            AssetManager asset, Node root){
        System.out.println("mmmm");
        //generate the perlinNoise for the cave path
        float[][] testnoise = noise.GeneratePerlinNoise
                (noise.genWhiteNoise(endX, 1), 5, (float) 0.2);
        if(ran.nextInt(99) >= 45){
            for(int i = 0; i <= testnoise.length - 1; i++){
                for(int j = 0; j <= testnoise[i].length - 1; j++){
                    float tempY = testnoise[i][j] * 40;
                    int newY = (int)tempY;
                    int finalY = y + newY;
                    int newX = x + j;
                    for(int g = 0; g <= 7; g++){
                        this.createBox(newX, finalY + g, 0, "Green", asset, 
                                root);
                        caveCoordsX.add(Integer.toString(newX));
                        caveCoordsY.add(Integer.toString(finalY + g));
                    }
                    if(ran.nextInt(200) == 5){
                        if(iterationCount <= 200){
                            iterationCount += 1;
                            System.out.println(newX + " " + newY);
                            this.makeCave(newX, finalY, 800, 
                                    asset, root);
                        }
                    }
                    }
                }
        }else{
            for(int i = 0; i <= testnoise.length - 1; i++){
            for(int j = 0; j <= testnoise[i].length - 1; j++){
                float tempY = testnoise[i][j] * 40;
                int newY = (int)tempY;
                int finalY = y + newY;
                int newX = x - j;
                for(int g = 0; g <= 7; g++){
                        this.createBox(newX, finalY + g, 0, "Green", asset, 
                                root);
                        caveCoordsX.add(Integer.toString(newX));
                        caveCoordsY.add(Integer.toString(finalY + g));
                        
                    }
                if(ran.nextInt(200) == 5){
                    if(iterationCount <= 200){
                        iterationCount += 1;
                        System.out.println(newX + " " + newY);
                        this.makeCave(newX, finalY, 800, 
                                asset, root);
                    }
                }
                }
            }
        }
        }
    
    public void createBox(int x, int y, int z, String color, 
            AssetManager assetManager, Node root){
        //create a box and place it at x, y
        //Box b = new Box((float)0.5, (float)0.5, (float)0.5);
        //create a quad
        Quad b = new Quad((float)1.0, (float)1.0);
        Geometry geom = new Geometry("Quad", b);
        geom.setLocalTranslation(new Vector3f(x, y, z));

        Material mat = new Material(assetManager, 
                "Common/MatDefs/Misc/Unshaded.j3md");
        if(color == "Blue"){
            mat.setColor("Color", ColorRGBA.Blue);
        }else if(color == "Green"){
            mat.setColor("Color", ColorRGBA.Green);
        }else if(color == "Red"){
            mat.setColor("Color", ColorRGBA.Red);
        }
        geom.setMaterial(mat);

        root.attachChild(geom);
    }
}
