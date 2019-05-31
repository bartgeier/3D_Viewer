package bertrand.myopengl;

import org.junit.Test;

import bertrand.myopengl.Entitys.Box;
import bertrand.myopengl.System.SCamera;
import bertrand.myopengl.Tool.Vec3;

import static org.junit.Assert.assertEquals;

public class test_SCamera {
        @Test
        public void init() {
                Box.Camera camera = new Box.Camera(
                        0,
                        1f,
                        85f,
                        0.1f,
                        300f
                );
                SCamera.init(camera);
                assertEquals(1, camera.R[0],0.001);
                assertEquals(1, camera.T[5],0.001);
                //assertEquals(1, camera.projectionMatrix[10],0.001);
        }

}
