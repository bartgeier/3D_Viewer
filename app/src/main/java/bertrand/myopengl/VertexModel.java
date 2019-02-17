package bertrand.myopengl;

import java.util.ArrayList;

public class VertexModel {
    class Vertex {
        public Vertex(float x, float y, float z, float red, float green, float blue, float alpha) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }
        float x;
        float y;
        float z;
        float red;
        float green;
        float blue;
        float alpha;
    }
    ArrayList<Vertex> vertices = new ArrayList<Vertex>();
//    public void test_nextID() {
//        vertices.add(new VertexInfo(1,2,3,1,0,0,1));
//    }

}
