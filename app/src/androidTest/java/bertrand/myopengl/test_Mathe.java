package bertrand.myopengl;

import android.opengl.Matrix;

import org.junit.Test;

import bertrand.myopengl.Tool.Mathe;
import bertrand.myopengl.Tool.Vec3;

import static org.junit.Assert.assertEquals;

public class test_Mathe {
        @Test
        public void rotationXYZ_and_eulerAngel() {
                final float [] R = new float[16];

                Mathe.rotationXYZ(R,10f,0f,0f);
                Vec3 v = Mathe.eulerAngels(R);
                assertEquals(10f, v.x, 0.001f);
                assertEquals(0f, v.y, 0.001f);
                assertEquals(0f, v.z, 0.001f);

                Mathe.rotationXYZ(R,180f,0f,0f);
                v = Mathe.eulerAngels(R);
                assertEquals(180f, v.x, 0.001f);
                assertEquals(0f, v.y, 0.001f);
                assertEquals(0f, v.z, 0.001f);

                Mathe.rotationXYZ(R,260f,0f,0f);
                v = Mathe.eulerAngels(R);
                assertEquals(260f, v.x, 0.001f);
                assertEquals(0f, v.y, 0.001f);
                assertEquals(0f, v.z, 0.001f);

                Mathe.rotationXYZ(R,0f,10f,0f);
                v = Mathe.eulerAngels(R);
                assertEquals(0f, v.x, 0.001f);
                assertEquals(10f, v.y, 0.001f);
                assertEquals(0f, v.z, 0.001f);

                Mathe.rotationXYZ(R,0f,180f,0f);
                v = Mathe.eulerAngels(R);
                assertEquals(180f, v.x, 0.001f);
                assertEquals(0f, v.y, 0.001f);
                assertEquals(180f, v.z, 0.001f);

                Mathe.rotationXYZ(R,0f,260f,0f);
                v = Mathe.eulerAngels(R);
                assertEquals(180f, v.x, 0.001f);
                assertEquals(280f, v.y, 0.001f);
                assertEquals(180f, v.z, 0.001f);

                Mathe.rotationXYZ(R,0f,0f,10f);
                v = Mathe.eulerAngels(R);
                assertEquals(0f, v.x, 0.001f);
                assertEquals(0f, v.y, 0.001f);
                assertEquals(10f, v.z, 0.001f);

                Mathe.rotationXYZ(R,0f,0f,180f);
                v = Mathe.eulerAngels(R);
                assertEquals(0f, v.x, 0.001f);
                assertEquals(0f, v.y, 0.001f);
                assertEquals(180f, v.z, 0.001f);

                Mathe.rotationXYZ(R,0f,0f,260f);
                v = Mathe.eulerAngels(R);
                assertEquals(0f, v.x, 0.001f);
                assertEquals(0f, v.y, 0.001f);
                assertEquals(260f, v.z, 0.001f);

                Mathe.rotationXYZ(R,10f,20f,30f);
                v = Mathe.eulerAngels(R);
                assertEquals(10f, v.x, 0.001f);
                assertEquals(20f, v.y, 0.001f);
                assertEquals(30f, v.z, 0.001f);

                Mathe.rotationXYZ(R,0f,0f,0f);
                v = Mathe.eulerAngels(R);
                assertEquals(-0f, v.x, 0.001f);
                assertEquals(-0f, v.y, 0.001f);
                assertEquals(-0f, v.z, 0.001f);

                Mathe.rotationXYZ(R,-45f,-45f,-45f);
                v = Mathe.eulerAngels(R);
                assertEquals(315f, v.x, 0.001f);
                assertEquals(315f, v.y, 0.001f);
                assertEquals(315f, v.z, 0.001f);

                Mathe.rotationXYZ(R,45f,45f,45f);
                v = Mathe.eulerAngels(R);
                assertEquals(45f, v.x, 0.001f);
                assertEquals(45f, v.y, 0.001f);
                assertEquals(45f, v.z, 0.001f);

                Mathe.rotationXYZ(R,90f,90f,90f);
                v = Mathe.eulerAngels(R);
                assertEquals(270f, v.x, 0.001f);
                assertEquals(90f, v.y, 0.001f);
                assertEquals(270f, v.z, 0.001f);

                Mathe.rotationXYZ(R,135f,135f,135f);
                v = Mathe.eulerAngels(R);
                assertEquals(315f, v.x, 0.001f);
                assertEquals(45f, v.y, 0.001f);
                assertEquals(315f, v.z, 0.001f);

                Mathe.rotationXYZ(R,180f,180f,180f);
                v = Mathe.eulerAngels(R);
                assertEquals(0f, v.x, 0.001f);
                assertEquals(0f, v.y, 0.001f);
                assertEquals(0f, v.z, 0.001f);

                Mathe.rotationXYZ(R,225f,225f,225f);
                v = Mathe.eulerAngels(R);
                assertEquals(45f, v.x, 0.001f);
                assertEquals(315f, v.y, 0.001f);
                assertEquals(45f, v.z, 0.001f);

                Mathe.rotationXYZ(R,270f,270f,270f);
                v = Mathe.eulerAngels(R);
                assertEquals(270f, v.x, 0.001f);
                assertEquals(270f, v.y, 0.001f);
                assertEquals(270f, v.z, 0.001f);

                Mathe.rotationXYZ(R,315f,315,315);
                v = Mathe.eulerAngels(R);
                assertEquals(315f, v.x, 0.001f);
                assertEquals(315f, v.y, 0.001f);
                assertEquals(315f, v.z, 0.001f);

                Mathe.rotationXYZ(R,90f,0,90);
                v = Mathe.eulerAngels(R);
                assertEquals(90f, v.x, 0.001f);
                assertEquals(0f, v.y, 0.001f);
                assertEquals(90f, v.z, 0.001f);

                Mathe.rotationXYZ(R,720f,0f,0f);
                v = Mathe.eulerAngels(R);
                assertEquals(0f, v.x, 0.001f);
                assertEquals(0f, v.y, 0.001f);
                assertEquals(0f, v.z, 0.001f);

                Mathe.rotationXYZ(R,0f,720f,0f);
                v = Mathe.eulerAngels(R);
                assertEquals(0f, v.x, 0.001f);
                assertEquals(0f, v.y, 0.001f);
                assertEquals(0f, v.z, 0.001f);

                Mathe.rotationXYZ(R,0f,0f,720f);
                v = Mathe.eulerAngels(R);
                assertEquals(0f, v.x, 0.001f);
                assertEquals(0f, v.y, 0.001f);
                assertEquals(0f, v.z, 0.001f);
        }

        @Test
        public void test_rotationDeltaXYZ() {
                float[] Ra = new float[16];
                Mathe.rotationXYZ(Ra,45f,135f,-5f);

                float[] Rb = new float[16];
                Matrix.setIdentityM(Rb, 0);
                Mathe.rotationDeltaXYZ(Rb,45f,135f,-5f);

                assertEquals(Ra[0], Rb[0], 0.001f);
                assertEquals(Ra[1], Rb[1], 0.001f);
                assertEquals(Ra[2], Rb[2], 0.001f);
                assertEquals(Ra[3], Rb[3], 0.001f);
                assertEquals(Ra[4], Rb[4], 0.001f);
                assertEquals(Ra[5], Rb[5], 0.001f);
                assertEquals(Ra[6], Rb[6], 0.001f);
                assertEquals(Ra[7], Rb[7], 0.001f);
                assertEquals(Ra[8], Rb[8], 0.001f);
                assertEquals(Ra[9], Rb[9], 0.001f);
                assertEquals(Ra[10], Rb[10], 0.001f);
                assertEquals(Ra[11], Rb[11], 0.001f);
                assertEquals(Ra[12], Rb[12], 0.001f);
                assertEquals(Ra[13], Rb[13], 0.001f);
                assertEquals(Ra[14], Rb[14], 0.001f);
                assertEquals(Ra[15], Rb[15], 0.001f);
        }

        @Test
        public void adjustDistance() {
                float[] Rc = new float[16];
                Matrix.setIdentityM(Rc, 0);
                Matrix.translateM(Rc,0, 10, 20, 30);

                float x = Mathe.adjustDistance(30f, 1/1.1f, 0.1f);
                assertEquals(27.17f, x, 0.1f);
                x = Mathe.adjustDistance(-30f, -1/1.1f, -0.1f);
                assertEquals(27.17f, x, 0.1f);

        }

        @Test
        public void Tx() {
                float[] T = new float[16];
                Matrix.setIdentityM(T,0);
                Matrix.translateM(T,0,10,20,30);
                assertEquals(10, Mathe.Tx(T),0.1f);
        }

        @Test
        public void Ty() {
                float[] T = new float[16];
                Matrix.setIdentityM(T,0);
                Matrix.translateM(T,0,10,20,30);
                assertEquals(20,Mathe.Ty(T),0.1f);
        }

        @Test
        public void Tz() {
                float[] T = new float[16];
                Matrix.setIdentityM(T,0);
                Matrix.translateM(T,0,10,20,30);
                assertEquals(30,Mathe.Tz(T),0.1f);
        }

        @Test
        public void translationXYZ() {
                float[] T = new float[16];
                Mathe.translationXYZ(T,10,20,30);
                assertEquals(1f,  T[0],0.1f);
                assertEquals(0f,  T[1],0.1f);
                assertEquals(10f, T[12],0.1f);
                assertEquals(20f, T[13],0.1f);
                assertEquals(30f, T[14],0.1f);
                assertEquals(1,   T[15],0.1f);

                Matrix.translateM(T,0,1,2,3);
                assertEquals(11f, T[12],0.1f);
                assertEquals(22f, T[13],0.1f);
                assertEquals(33f, T[14],0.1f);
        }

        @Test
        public void quaternion_to_matrix() {
                //float[] H = new float[4];
                float[] R = new float[16];
                //https://www.andre-gaschler.com/rotationconverter/
                Mathe.quatToMatrix(R,0.5f,0.5f,0.5f,0.5f);
                assertEquals(0.0f ,R[0], 0.001f);
                assertEquals(1.0f ,R[1], 0.001f);
                assertEquals(0.0f ,R[2], 0.001f);
                assertEquals(0.0f ,R[3], 0.001f);

                assertEquals(0.0f, R[4], 0.001f);
                assertEquals(0.0f, R[5], 0.001f);
                assertEquals(1.0f ,R[6], 0.001f);
                assertEquals(0.0f ,R[7], 0.001f);

                assertEquals(1.0f ,R[8], 0.001f);
                assertEquals(0.0f ,R[9], 0.001f);
                assertEquals(0.0f ,R[10], 0.001f);
                assertEquals(0.0f ,R[11], 0.001f);

                assertEquals(0.0f ,R[12], 0.001f);
                assertEquals(0.0f ,R[13], 0.001f);
                assertEquals(0.0f ,R[14], 0.001f);
                assertEquals(1.0f ,R[15], 0.001f);

                Mathe.quatToMatrix(R,0.9f,1.0f,5.2f,3.6f);
                assertEquals(-0.9134178f ,R[0], 0.000001f);
                assertEquals(0.40373120f ,R[1], 0.000001f);
                assertEquals(-0.0516623f ,R[2], 0.000001f);
                assertEquals(0.00000000f ,R[3], 0.000001f);

                assertEquals(0.0937575f, R[4], 0.000001f);
                assertEquals(0.3322172f, R[5], 0.000001f);
                assertEquals(0.9385315f ,R[6], 0.000001f);
                assertEquals(0.0000000f ,R[7], 0.000001f);

                assertEquals(0.39607750f ,R[8], 0.000001f);
                assertEquals(0.85242770f ,R[9], 0.000001f);
                assertEquals(-0.3413059f ,R[10], 0.000001f);
                assertEquals(0.00000000f ,R[11], 0.000001f);

                assertEquals(0.0f ,R[12], 0.001f);
                assertEquals(0.0f ,R[13], 0.001f);
                assertEquals(0.0f ,R[14], 0.001f);
                assertEquals(1.0f ,R[15], 0.001f);
        }

        @Test
        public void rotateM_withQuaternion() {
                float[] TF = new float[16];
                Matrix.setIdentityM(TF,0);
                Mathe.rotateM_withQuaternion(TF, 0.9f,1.0f,5.2f,3.6f);
                assertEquals(-0.9134178f ,TF[0], 0.000001f);
                assertEquals(0.40373120f ,TF[1], 0.000001f);
                assertEquals(-0.0516623f ,TF[2], 0.000001f);
                assertEquals(0.00000000f ,TF[3], 0.000001f);

                assertEquals(0.0937575f, TF[4], 0.000001f);
                assertEquals(0.3322172f, TF[5], 0.000001f);
                assertEquals(0.9385315f ,TF[6], 0.000001f);
                assertEquals(0.0000000f ,TF[7], 0.000001f);

                assertEquals(0.39607750f ,TF[8], 0.000001f);
                assertEquals(0.85242770f ,TF[9], 0.000001f);
                assertEquals(-0.3413059f ,TF[10], 0.000001f);
                assertEquals(0.00000000f ,TF[11], 0.000001f);

                assertEquals(0.0f ,TF[12], 0.001f);
                assertEquals(0.0f ,TF[13], 0.001f);
                assertEquals(0.0f ,TF[14], 0.001f);
                assertEquals(1.0f ,TF[15], 0.001f);
        }
}
