package World;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class createTerrain {
    //create the basic terrain with caves/dungeons/trees/resources here
    public createTerrain(int x, int y, int z, AssetManager asset, Node root){
        System.out.println(Integer.toString(x) + " " + Integer.toString(y) 
                + " " + Integer.toString(z));
        this.createBox(2, 3, asset, root);
    }
    
    public void createBox(int x, int y, AssetManager assetManager, Node root){
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        geom.setLocalTranslation(new Vector3f(x, y, 0));

        Material mat = new Material(assetManager, 
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        root.attachChild(geom);
    }
}
