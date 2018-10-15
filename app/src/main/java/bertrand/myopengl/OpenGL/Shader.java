package bertrand.myopengl.OpenGL;

public class Shader {
        int program;
        int position_Attrib_Location;
        int color_Attrib_Location;
        int normal_Attrib_Location;

        int modelViewMatrix_Uniform_Location;
        int projectionMatrix_Uniform_Location;

        int lightAmbientIntens_Uniform_Location;
        int lightAmbientColor_Uniform_Location;

        int lightDiffuseIntens_Uniform_Location;
        int lightDirection_Uniform_Location;

        int matSpecularIntensity_Uniform_Location;
        int shininess_Uniform_Location;

        public static float[] projectionMatrix = new float[16];
}
