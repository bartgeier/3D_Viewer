package bertrand.myopengl.OpenGL;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;


public abstract class AbstractShader {
        public class Uniform_Location {
                int modelViewMatrix = 0;
                int projectionMatrix = 0;

                int lightAmbientIntens = 0;
                int lightAmbientColor = 0;

                int lightDiffuseIntens = 0;
                int lightDirection = 0;

                int matSpecularIntensity = 0;
                int shininess = 0;
        }

        public class Attribute_Location {
                int position = 0;
                int color = 0;
                int normal = 0;
        }

        AbstractShader () {
                a = new Attribute_Location();
                u = new Uniform_Location();
        }

        int programID;
        Attribute_Location a;
        Uniform_Location u;

        public abstract void enableVertexAttribArray();

}
