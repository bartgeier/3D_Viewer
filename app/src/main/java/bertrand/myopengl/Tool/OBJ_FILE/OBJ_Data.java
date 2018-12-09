package bertrand.myopengl.Tool.OBJ_FILE;

public class OBJ_Data {
        public int[] indices;
        public float[] positions;
        public float[] texCoords;
        public float[] normals;

        public OBJ_Data(int[] indices, float[] positions, float[]texCoords, float[] normals) {
                this.indices = indices;
                this.positions = positions;
                this.texCoords = texCoords;
                this.normals = normals;
        }
}
