package bertrand.myopengl.Light;

import bertrand.myopengl.Tool.TextChooser.Color3f;
import bertrand.myopengl.Tool.Vec3;

public class Light {
 	public Vec3 position;
 	public Color3f color;
        public Vec3 attenuation = new Vec3(1, 0, 0);

        public Light(
                float x, float y, float z,
                float red, float green, float blue
        ) {
                position = new Vec3(x, y, z);
                color = new Color3f(red, green, blue);
        }

  	public Light(Vec3 position, float red, float green, float blue) {
  	        color = new Color3f(red, green, blue);
                this.position = position;
        }
  }
