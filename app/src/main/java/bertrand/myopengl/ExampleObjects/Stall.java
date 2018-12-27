package bertrand.myopengl.ExampleObjects;

import android.graphics.Bitmap;

import bertrand.myopengl.Models.TexturedModel;
import bertrand.myopengl.Shaders.TexturedShader;

public class Stall extends TexturedModel {
        public Stall(
                final TexturedShader s,
                final Bitmap bitMap,
                final int[] indices,
                final float[] positions,
                final float[] texCoords,
                final float[] normals
        ){
                super(s, bitMap, indices, positions, texCoords, normals);
                position.x = -0.5f;
                position.y = -3f;
                position.z = -3f;
        }
        private double rotationAngle = 0;

        @Override
        public void updateWithDelta(float dt_ms) {
                double newRotationPeriod_ms = 16000;
                rotationAngle += dt_ms * 360/newRotationPeriod_ms;
                rotation.y = (float)rotationAngle;
        }

}

