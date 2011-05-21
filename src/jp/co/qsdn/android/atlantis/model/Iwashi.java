package jp.co.qsdn.android.atlantis.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

public class Iwashi {
  private static final String TAG = Iwashi.class.getName();
  private FloatBuffer mVertexBuffer;
  private final FloatBuffer mTextureBuffer;  
  private final FloatBuffer mNormalBuffer;  
  private static int texid;
  private long tick = 0;
  private float scale = 0.1035156288414f;
  private float center_xyz[] = {-0.185271816326531f, 0.344428326530612f, -0.00509786734693878f };

  public Iwashi() {

    ByteBuffer vbb = ByteBuffer.allocateDirect(IwashiData.vertices.length * 4);
    vbb.order(ByteOrder.nativeOrder());
    mVertexBuffer = vbb.asFloatBuffer();
    mVertexBuffer.put(IwashiData.vertices);
    mVertexBuffer.position(0);

    ByteBuffer nbb = ByteBuffer.allocateDirect(IwashiData.normals.length * 4);
    nbb.order(ByteOrder.nativeOrder());
    mNormalBuffer = nbb.asFloatBuffer();
    mNormalBuffer.put(IwashiData.normals);
    mNormalBuffer.position(0);

    ByteBuffer tbb = ByteBuffer.allocateDirect(IwashiData.texCoords.length * 4);
    tbb.order(ByteOrder.nativeOrder());
    mTextureBuffer = tbb.asFloatBuffer();
    mTextureBuffer.put(IwashiData.texCoords);
    mTextureBuffer.position(0);
  }

  public static void loadTexture(GL10 gl10, Context context, int resource) {
    Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), resource);
    int a[] = new int[1];
    gl10.glGenTextures(1, a, 0);
    gl10.glBindTexture(GL10.GL_TEXTURE_2D, a[0]);
    texid = a[0];
    GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
    gl10.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
    gl10.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
    bmp.recycle();
  }

  private float getMoveWidth(float x) {
    /*=======================================================================*/
    /* z = 1/3 * x^2 の2次関数から算出                                       */
    /*=======================================================================*/
    float xt = x / scale + center_xyz[0];
    return xt * xt / 20.0f - 0.4f;
  }

  private void animate() {
    if (tick == 0) {
      tick = System.currentTimeMillis();
    }
    else {
      long current = System.currentTimeMillis();
      float nf = (float)((current / 200) % 10000);
      long ni = (long)Math.floor(nf);
      float w = 2f*((float)Math.PI)*(nf - (float)ni);
      float s = (float)Math.sin((double)nf) * scale;
      // z = a * ((x + p)^2)
      // z = 1/3 * ((4.7 + 0)^2)
       
      //303 101 {4.725803, 1.603915, -0.000000}
      //309 103 {4.725803, 1.603915, -0.000000}
      {
        int idx[] = { 101,103,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }

      //300 100 {4.734376, 1.502248, -0.009085}
      //312 104 {4.727424, 1.502259, 0.009085}
      //1290 430 {4.727424, 1.502259, 0.009085}
      //1317 439 {4.734376, 1.502248, -0.009085}
      {
        int idx[] = { 100,104,430,439,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //IwashiData.vertices[2+3*100] = IwashiData.org_vertices[2+3*100] + (1.0f * s);
      //IwashiData.vertices[2+3*104] = IwashiData.org_vertices[2+3*104] + (1.0f * s);
      //IwashiData.vertices[2+3*430] = IwashiData.org_vertices[2+3*430] + (1.0f * s);
      //IwashiData.vertices[2+3*439] = IwashiData.org_vertices[2+3*439] + (1.0f * s);

      //318 106 {4.497553, 1.130905, 0.009254}
      //1293 431 {4.497553, 1.130905, 0.009254}
      //1299 433 {4.497553, 1.130905, 0.009254}
      {
        int idx[] = { 106,431,433,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
//      IwashiData.vertices[2+3*106] = IwashiData.org_vertices[2+3*106] + (1.0f * s);
//      IwashiData.vertices[2+3*431] = IwashiData.org_vertices[2+3*431] + (1.0f * s);
//      IwashiData.vertices[2+3*433] = IwashiData.org_vertices[2+3*433] + (1.0f * s);

      // 096 032 {3.943874, 0.549283, 0.006373}
      // 102 034 {3.943874, 0.549283, 0.006373}
      // 132 044 {3.931480, 0.549297, -0.006373}
      // 138 046 {3.931480, 0.549297, -0.006373}
      // 285 095 {3.943874, 0.549283, 0.006373}
      // 288 096 {3.943874, 0.549283, 0.006373}
      // 321 107 {3.931480, 0.549297, -0.006373}
      // 324 108 {3.931480, 0.549297, -0.006373}
      {
        int idx[] = { 32,34,44,46,95,96,107,108,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
//      {
//        float width = 0.6f * s;
//        IwashiData.vertices[2+3* 32] = IwashiData.org_vertices[2+3* 32] + width;
//        IwashiData.vertices[2+3* 34] = IwashiData.org_vertices[2+3* 34] + width;
//        IwashiData.vertices[2+3* 44] = IwashiData.org_vertices[2+3* 44] + width;
//        IwashiData.vertices[2+3* 46] = IwashiData.org_vertices[2+3* 46] + width;
//        IwashiData.vertices[2+3* 95] = IwashiData.org_vertices[2+3* 95] + width;
//        IwashiData.vertices[2+3* 96] = IwashiData.org_vertices[2+3* 96] + width;
//        IwashiData.vertices[2+3*107] = IwashiData.org_vertices[2+3*107] + width;
//        IwashiData.vertices[2+3*108] = IwashiData.org_vertices[2+3*108] + width;
//      }
   
      // 264 088 {4.587202, 0.163779, 0.009247}
      // 276 092 {4.597796, 0.163766, -0.009247}
      // 282 094 {4.597796, 0.163766, -0.009247}
      // 327 109 {4.587202, 0.163779, 0.009247}
      {
        int idx[] = { 88,92,94,109,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
//      {
//        float width = 1.0f * s;
//        IwashiData.vertices[2+3* 88] = IwashiData.org_vertices[2+3* 88] + width;
//        IwashiData.vertices[2+3* 92] = IwashiData.org_vertices[2+3* 92] + width;
//        IwashiData.vertices[2+3* 94] = IwashiData.org_vertices[2+3* 94] + width;
//        IwashiData.vertices[2+3*109] = IwashiData.org_vertices[2+3*109] + width;
//      }
      // 267 089 {4.865566, -0.206893, 0.009037}
      // 273 091 {4.871437, -0.206896, -0.009037}
      //1329 443 {4.871437, -0.206896, -0.009037}
      //1335 445 {4.871437, -0.206896, -0.009037}
      //1344 448 {4.865566, -0.206893, 0.009037}
      //1350 450 {4.865566, -0.206893, 0.009037}
      {
        int idx[] = { 89,91,443,445,448,450,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
//      {
//        float width = 1.0f * s;
//        IwashiData.vertices[2+3* 89] = IwashiData.org_vertices[2+3* 89] + width;
//        IwashiData.vertices[2+3* 91] = IwashiData.org_vertices[2+3* 91] + width;
//        IwashiData.vertices[2+3*443] = IwashiData.org_vertices[2+3*443] + width;
//        IwashiData.vertices[2+3*445] = IwashiData.org_vertices[2+3*445] + width;
//        IwashiData.vertices[2+3*448] = IwashiData.org_vertices[2+3*448] + width;
//        IwashiData.vertices[2+3*450] = IwashiData.org_vertices[2+3*450] + width;
//      }
      //291 097 {4.508326, 1.130889, -0.009254}
      //1308 436 {4.508326, 1.130889, -0.009254}
      //1314 438 {4.508326, 1.130889, -0.009254}
      {
        int idx[] = { 97,436,438,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
//      {
//        float width = 1.0f * s;
//        IwashiData.vertices[2+3* 97] = IwashiData.org_vertices[2+3* 97] + width;
//        IwashiData.vertices[2+3*436] = IwashiData.org_vertices[2+3*436] + width;
//        IwashiData.vertices[2+3*438] = IwashiData.org_vertices[2+3*438] + width;
//      }
      //1326 442 {4.868408, -0.319613, -0.000000}
      //1353 451 {4.868408, -0.319613, -0.000000}
      {
        int idx[] = { 442,451,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
//      {
//        float width = 1.0f * s;
//        IwashiData.vertices[2+3*442] = IwashiData.org_vertices[2+3*442] + width;
//        IwashiData.vertices[2+3*451] = IwashiData.org_vertices[2+3*451] + width;
//      }
      // 231 077 {4.189324, -0.027536, -0.000000}
      // 237 079 {4.189324, -0.027536, -0.000000}
      //1323 441 {4.189324, -0.027536, -0.000000}
      //1332 444 {4.189324, -0.027536, -0.000000}
      //1347 449 {4.189324, -0.027536, -0.000000}
      //1356 452 {4.189324, -0.027536, -0.000000}
      {
        int idx[] = { 77,79,441,444,449,452,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
//      {
//        float width = 0.6f * s;
//        IwashiData.vertices[2+3* 77] = IwashiData.org_vertices[2+3* 77] + width;
//        IwashiData.vertices[2+3* 79] = IwashiData.org_vertices[2+3* 79] + width;
//        IwashiData.vertices[2+3*441] = IwashiData.org_vertices[2+3*441] + width;
//        IwashiData.vertices[2+3*444] = IwashiData.org_vertices[2+3*444] + width;
//        IwashiData.vertices[2+3*449] = IwashiData.org_vertices[2+3*449] + width;
//        IwashiData.vertices[2+3*452] = IwashiData.org_vertices[2+3*452] + width;
//      }
      //084 028 {3.994344, 0.212614, -0.011905}
      //093 031 {3.994344, 0.212614, -0.011905}
      //141 047 {3.985378, 0.212621, -0.040541}
      //150 050 {3.985378, 0.212621, -0.040541}
      //228 076 {3.985378, 0.212621, -0.040541}
      //240 080 {3.994344, 0.212614, -0.011905}
      //261 087 {3.985378, 0.212621, -0.040541}
      //270 090 {3.994344, 0.212614, -0.011905}
      //279 093 {3.994344, 0.212614, -0.011905}
      //330 110 {3.985378, 0.212621, -0.040541}
      //1338 446 {3.994344, 0.212614, -0.011905}
      //1341 447 {3.985378, 0.212621, -0.040541}
      {
        int idx[] = { 28,31,47,50,76,80,87,90,93,110,446,447,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
//      {
//        float width = 0.6f * s;
//        IwashiData.vertices[2+3* 28] = IwashiData.org_vertices[2+3* 28] + width;
//        IwashiData.vertices[2+3* 31] = IwashiData.org_vertices[2+3* 31] + width;
//        IwashiData.vertices[2+3* 47] = IwashiData.org_vertices[2+3* 47] + width;
//        IwashiData.vertices[2+3* 50] = IwashiData.org_vertices[2+3* 50] + width;
//        IwashiData.vertices[2+3* 76] = IwashiData.org_vertices[2+3* 76] + width;
//        IwashiData.vertices[2+3* 80] = IwashiData.org_vertices[2+3* 80] + width;
//        IwashiData.vertices[2+3* 87] = IwashiData.org_vertices[2+3* 87] + width;
//        IwashiData.vertices[2+3* 90] = IwashiData.org_vertices[2+3* 90] + width;
//        IwashiData.vertices[2+3* 93] = IwashiData.org_vertices[2+3* 93] + width;
//        IwashiData.vertices[2+3*110] = IwashiData.org_vertices[2+3*110] + width;
//        IwashiData.vertices[2+3*446] = IwashiData.org_vertices[2+3*446] + width;
//        IwashiData.vertices[2+3*447] = IwashiData.org_vertices[2+3*447] + width;
//      }
      //105 035 {4.001855, 0.959487, -0.012866}
      //111 037 {4.001855, 0.959487, -0.012866}
      //246 082 {4.001855, 0.959487, -0.012866}
      //294 098 {4.001855, 0.959487, -0.012866}
      //1305 435 {4.001855, 0.959487, -0.012866}
      // XXXX
      //120 040 {3.992240, 0.959496, -0.039771}
      //129 043 {3.992240, 0.959496, -0.039771}
      //258 086 {3.992240, 0.959496, -0.039771}
      //315 105 {3.992240, 0.959496, -0.039771}
      //1302 434 {3.992240, 0.959496, -0.039771}
      {
        int idx[] = { 35,37,82,98,435,40,43,86,105,434,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
//      {
//        float width = 0.6f * s;
//        IwashiData.vertices[2+3* 35] = IwashiData.org_vertices[2+3* 35] + width;
//        IwashiData.vertices[2+3* 37] = IwashiData.org_vertices[2+3* 37] + width;
//        IwashiData.vertices[2+3* 82] = IwashiData.org_vertices[2+3* 82] + width;
//        IwashiData.vertices[2+3* 98] = IwashiData.org_vertices[2+3* 98] + width;
//        IwashiData.vertices[2+3*435] = IwashiData.org_vertices[2+3*435] + width;
//
//        IwashiData.vertices[2+3* 40] = IwashiData.org_vertices[2+3* 40] + width;
//        IwashiData.vertices[2+3* 43] = IwashiData.org_vertices[2+3* 43] + width;
//        IwashiData.vertices[2+3* 86] = IwashiData.org_vertices[2+3* 86] + width;
//        IwashiData.vertices[2+3*105] = IwashiData.org_vertices[2+3*105] + width;
//        IwashiData.vertices[2+3*434] = IwashiData.org_vertices[2+3*434] + width;
//      }
      // 249 083 {4.250497, 1.351480, -0.030413}
      // 255 085 {4.250497, 1.351480, -0.030413}
      // 297 099 {4.250497, 1.351480, -0.030413}
      // 306 102 {4.250497, 1.351480, -0.030413}
      //1287 429 {4.250497, 1.351480, -0.030413}
      //1296 432 {4.250497, 1.351480, -0.030413}
      //1311 437 {4.250497, 1.351480, -0.030413}
      //1320 440 {4.250497, 1.351480, -0.030413}
      {
        int idx[] = { 83,85,99,102,429,432,437,440,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
//      {
//        float width = 0.6f * s;
//        IwashiData.vertices[2+3* 83] = IwashiData.org_vertices[2+3* 83] + width;
//        IwashiData.vertices[2+3* 85] = IwashiData.org_vertices[2+3* 85] + width;
//        IwashiData.vertices[2+3* 99] = IwashiData.org_vertices[2+3* 99] + width;
//        IwashiData.vertices[2+3*102] = IwashiData.org_vertices[2+3*102] + width;
//        IwashiData.vertices[2+3*429] = IwashiData.org_vertices[2+3*429] + width;
//        IwashiData.vertices[2+3*432] = IwashiData.org_vertices[2+3*432] + width;
//        IwashiData.vertices[2+3*437] = IwashiData.org_vertices[2+3*437] + width;
//        IwashiData.vertices[2+3*440] = IwashiData.org_vertices[2+3*440] + width;
//      }
      //114 038 {3.393267, 0.860405, -0.028042}
      //117 039 {3.393267, 0.860405, -0.028042}
      //243 081 {3.393267, 0.860405, -0.028042}
      //252 084 {3.393267, 0.860405, -0.028042}
      //705 235 {3.393267, 0.860405, -0.028042}
      //714 238 {3.393267, 0.860405, -0.028042}
      {
        int idx[] = { 38,39,81,84,235,238, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
//      {
//        float width = 0.5f * s;
//        IwashiData.vertices[2+3* 38] = IwashiData.org_vertices[2+3* 38] + width;
//        IwashiData.vertices[2+3* 39] = IwashiData.org_vertices[2+3* 39] + width;
//        IwashiData.vertices[2+3* 81] = IwashiData.org_vertices[2+3* 81] + width;
//        IwashiData.vertices[2+3* 84] = IwashiData.org_vertices[2+3* 84] + width;
//        IwashiData.vertices[2+3*235] = IwashiData.org_vertices[2+3*235] + width;
//        IwashiData.vertices[2+3*238] = IwashiData.org_vertices[2+3*238] + width;
//      }
      //081 027 {3.465865, 0.220323, -0.023851}
      //144 048 {3.465865, 0.220323, -0.023851}
      //225 075 {3.465865, 0.220323, -0.023851}
      //234 078 {3.465865, 0.220323, -0.023851}
      //660 220 {3.465865, 0.220323, -0.023851}
      //690 230 {3.465865, 0.220323, -0.023851}
      //696 232 {3.465865, 0.220323, -0.023851}
      //720 240 {3.465865, 0.220323, -0.023851}
      {
        int idx[] = { 27,48,75,78,220,230,232,240,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
//      {
//        float width = 0.5f * s;
//        IwashiData.vertices[2+3* 27] = IwashiData.org_vertices[2+3* 27] + width;
//        IwashiData.vertices[2+3* 48] = IwashiData.org_vertices[2+3* 48] + width;
//        IwashiData.vertices[2+3* 75] = IwashiData.org_vertices[2+3* 75] + width;
//        IwashiData.vertices[2+3* 78] = IwashiData.org_vertices[2+3* 78] + width;
//        IwashiData.vertices[2+3*220] = IwashiData.org_vertices[2+3*220] + width;
//        IwashiData.vertices[2+3*230] = IwashiData.org_vertices[2+3*230] + width;
//        IwashiData.vertices[2+3*232] = IwashiData.org_vertices[2+3*232] + width;
//        IwashiData.vertices[2+3*240] = IwashiData.org_vertices[2+3*240] + width;
//      }
      //663 221 {3.128526, 0.180488, -0.023306}
      //669 223 {3.128526, 0.180488, -0.023306}
      //678 226 {3.128526, 0.180488, -0.023306}
      //687 229 {3.128526, 0.180488, -0.023306}
      {
        int idx[] = { 221,223,226,229,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
//      {
//        float width = 0.5f * s;
//        IwashiData.vertices[2+3*221] = IwashiData.org_vertices[2+3*221] + width;
//        IwashiData.vertices[2+3*223] = IwashiData.org_vertices[2+3*223] + width;
//        IwashiData.vertices[2+3*226] = IwashiData.org_vertices[2+3*226] + width;
//        IwashiData.vertices[2+3*229] = IwashiData.org_vertices[2+3*229] + width;
//      }
      //087 029 {2.908598, 0.545923, 0.068958}
      //090 030 {2.908598, 0.545923, 0.068958}
      //099 033 {2.908598, 0.545923, 0.068958}
      //108 036 {2.908598, 0.545923, 0.068958}
      //123 041 {2.897367, 0.545929, -0.111540}
      //126 042 {2.897367, 0.545929, -0.111540}
      //135 045 {2.897367, 0.545929, -0.111540}
      //147 049 {2.897367, 0.545929, -0.111540}
      //177 059 {2.908598, 0.545923, 0.068958}
      //183 061 {2.908598, 0.545923, 0.068958}
      //192 064 {2.908598, 0.545923, 0.068958}
      //201 067 {2.897367, 0.545929, -0.111540}
      //210 070 {2.897367, 0.545929, -0.111540}
      //222 074 {2.897367, 0.545929, -0.111540}
      //627 209 {2.908598, 0.545923, 0.068958}
      //633 211 {2.908598, 0.545923, 0.068958}
      //645 215 {2.897367, 0.545929, -0.111540}
      //654 218 {2.897367, 0.545929, -0.111540}
      //699 233 {2.908598, 0.545923, 0.068958}
      //702 234 {2.908598, 0.545923, 0.068958}
      //717 239 {2.897367, 0.545929, -0.111540}
      //726 242 {2.897367, 0.545929, -0.111540}
      //1371 457 {2.897367, 0.545929, -0.111540}
      //1380 460 {2.908598, 0.545923, 0.068958}
      {
        int idx[] = { 29,30,33,36,41,42,45,49,59,61,64,67,70,74,209,211,215,218,233,234,239,242,457,460,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
//      {
//        float width = 0.45f * s;
//        IwashiData.vertices[2+3* 29] = IwashiData.org_vertices[2+3* 29] + width;
//        IwashiData.vertices[2+3* 30] = IwashiData.org_vertices[2+3* 30] + width;
//        IwashiData.vertices[2+3* 33] = IwashiData.org_vertices[2+3* 33] + width;
//        IwashiData.vertices[2+3* 36] = IwashiData.org_vertices[2+3* 36] + width;
//        IwashiData.vertices[2+3* 41] = IwashiData.org_vertices[2+3* 41] + width;
//        IwashiData.vertices[2+3* 42] = IwashiData.org_vertices[2+3* 42] + width;
//        IwashiData.vertices[2+3* 45] = IwashiData.org_vertices[2+3* 45] + width;
//        IwashiData.vertices[2+3* 49] = IwashiData.org_vertices[2+3* 49] + width;
//        IwashiData.vertices[2+3* 59] = IwashiData.org_vertices[2+3* 59] + width;
//        IwashiData.vertices[2+3* 61] = IwashiData.org_vertices[2+3* 61] + width;
//        IwashiData.vertices[2+3* 64] = IwashiData.org_vertices[2+3* 64] + width;
//        IwashiData.vertices[2+3* 67] = IwashiData.org_vertices[2+3* 67] + width;
//        IwashiData.vertices[2+3* 70] = IwashiData.org_vertices[2+3* 70] + width;
//        IwashiData.vertices[2+3* 74] = IwashiData.org_vertices[2+3* 74] + width;
//        IwashiData.vertices[2+3*211] = IwashiData.org_vertices[2+3*211] + width;
//        IwashiData.vertices[2+3*209] = IwashiData.org_vertices[2+3*209] + width;
//        IwashiData.vertices[2+3*215] = IwashiData.org_vertices[2+3*215] + width;
//        IwashiData.vertices[2+3*218] = IwashiData.org_vertices[2+3*218] + width;
//        IwashiData.vertices[2+3*233] = IwashiData.org_vertices[2+3*233] + width;
//        IwashiData.vertices[2+3*234] = IwashiData.org_vertices[2+3*234] + width;
//        IwashiData.vertices[2+3*239] = IwashiData.org_vertices[2+3*239] + width;
//        IwashiData.vertices[2+3*242] = IwashiData.org_vertices[2+3*242] + width;
//        IwashiData.vertices[2+3*457] = IwashiData.org_vertices[2+3*457] + width;
//        IwashiData.vertices[2+3*460] = IwashiData.org_vertices[2+3*460] + width;
//      }
      //672 224 {2.755704, 0.041151, -0.025086}
      //675 225 {2.755704, 0.041151, -0.025086}
      //1182 394 {2.755704, 0.041151, -0.025086}
      //1188 396 {2.755704, 0.041151, -0.025086}
      //1203 401 {2.755704, 0.041151, -0.025086}
      //1209 403 {2.755704, 0.041151, -0.025086}
      {
        int idx[] = { 224,225,394,396,401,403,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
//      {
//        float width = 0.45f * s;
//        IwashiData.vertices[2+3*224] = IwashiData.org_vertices[2+3*224] + width;
//        IwashiData.vertices[2+3*225] = IwashiData.org_vertices[2+3*225] + width;
//        IwashiData.vertices[2+3*394] = IwashiData.org_vertices[2+3*394] + width;
//        IwashiData.vertices[2+3*396] = IwashiData.org_vertices[2+3*396] + width;
//        IwashiData.vertices[2+3*401] = IwashiData.org_vertices[2+3*401] + width;
//        IwashiData.vertices[2+3*403] = IwashiData.org_vertices[2+3*403] + width;
//      }
      // 606 202 {2.601744, 0.072730, -0.082255}
      // 615 205 {2.608089, 0.072728, 0.042083}
      // 624 208 {2.608089, 0.072728, 0.042083}
      // 648 216 {2.601744, 0.072730, -0.082255}
      // 657 219 {2.601744, 0.072730, -0.082255}
      // 666 222 {2.601744, 0.072730, -0.082255}
      // 681 227 {2.608089, 0.072728, 0.042083}
      // 684 228 {2.608089, 0.072728, 0.042083}
      // 693 231 {2.608089, 0.072728, 0.042083}
      // 723 241 {2.601744, 0.072730, -0.082255}
      //1191 397 {2.608089, 0.072728, 0.042083}
      //1200 400 {2.601744, 0.072730, -0.082255}
      {
        int idx[] = { 202,205,208,216,219,222,227,228,231,241,397,400,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
//      {
//        float width = 0.44f * s;
//        IwashiData.vertices[2+3*202] = IwashiData.org_vertices[2+3*202] + width;
//        IwashiData.vertices[2+3*205] = IwashiData.org_vertices[2+3*205] + width;
//        IwashiData.vertices[2+3*208] = IwashiData.org_vertices[2+3*208] + width;
//        IwashiData.vertices[2+3*216] = IwashiData.org_vertices[2+3*216] + width;
//        IwashiData.vertices[2+3*219] = IwashiData.org_vertices[2+3*219] + width;
//        IwashiData.vertices[2+3*222] = IwashiData.org_vertices[2+3*222] + width;
//        IwashiData.vertices[2+3*227] = IwashiData.org_vertices[2+3*227] + width;
//        IwashiData.vertices[2+3*228] = IwashiData.org_vertices[2+3*228] + width;
//        IwashiData.vertices[2+3*231] = IwashiData.org_vertices[2+3*231] + width;
//        IwashiData.vertices[2+3*241] = IwashiData.org_vertices[2+3*241] + width;
//        IwashiData.vertices[2+3*397] = IwashiData.org_vertices[2+3*397] + width;
//        IwashiData.vertices[2+3*400] = IwashiData.org_vertices[2+3*400] + width;
//      }
      //636 212 {2.606399, 0.965839, -0.022280}
      //642 214 {2.606399, 0.965839, -0.022280}
      //708 236 {2.606399, 0.965839, -0.022280}
      //711 237 {2.606399, 0.965839, -0.022280}
      {
        int idx[] = { 212,214,236,237,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
//      {
//        float width = 0.44f * s;
//        IwashiData.vertices[2+3*212] = IwashiData.org_vertices[2+3*212] + width;
//        IwashiData.vertices[2+3*214] = IwashiData.org_vertices[2+3*214] + width;
//        IwashiData.vertices[2+3*236] = IwashiData.org_vertices[2+3*236] + width;
//        IwashiData.vertices[2+3*237] = IwashiData.org_vertices[2+3*237] + width;
//      }
      //174 058 {1.993230, -0.000729, 0.124182}
      //216 072 {1.985328, -0.000726, -0.159362}
      //561 187 {1.990646, 1.132275, -0.019784}
      //570 190 {1.990646, 1.132275, -0.019784}
      //603 201 {1.985328, -0.000726, -0.159362}
      //618 206 {1.993230, -0.000729, 0.124182}
      //621 207 {1.993230, -0.000729, 0.124182}
      //630 210 {1.990646, 1.132275, -0.019784}
      //639 213 {1.990646, 1.132275, -0.019784}
      //651 217 {1.985328, -0.000726, -0.159362}
      //1179 393 {1.954150, -0.416138, -0.022541}
      //1212 404 {1.954150, -0.416138, -0.022541}
      //1362 454 {1.990646, 1.132275, -0.019784}
      //1368 456 {1.990646, 1.132275, -0.019784}
      //1383 461 {1.990646, 1.132275, -0.019784}
      //1389 463 {1.990646, 1.132275, -0.019784}
      //1401 467 {1.993230, -0.000729, 0.124182}
      //1407 469 {1.993230, -0.000729, 0.124182}
      //1416 472 {1.985328, -0.000726, -0.159362}
      //1422 474 {1.985328, -0.000726, -0.159362}
      {
        int idx[] = { 58, 72, 187, 190, 201, 206, 207, 210, 213, 217, 393, 404, 454, 456, 461, 463, 467, 469, 472, 474, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //609 203 {1.841455, -0.150632, -0.019362}
      //612 204 {1.841455, -0.150632, -0.019362}
      //1185 395 {1.841455, -0.150632, -0.019362}
      //1194 398 {1.841455, -0.150632, -0.019362}
      //1197 399 {1.841455, -0.150632, -0.019362}
      //1206 402 {1.841455, -0.150632, -0.019362}
      //1398 466 {1.841455, -0.150632, -0.019362}
      //1425 475 {1.841455, -0.150632, -0.019362}
      {
        int idx[] = { 203, 204, 395, 398, 399, 402, 466, 475, }; 
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //1218 406 {0.956889, -0.352683, -0.017794}
      //1224 408 {0.956889, -0.352683, -0.017794}
      //1239 413 {0.956889, -0.352683, -0.017794}
      //1245 415 {0.956889, -0.352683, -0.017794}
      //1395 465 {0.956889, -0.352683, -0.017794}
      //1404 468 {0.956889, -0.352683, -0.017794}
      //1419 473 {0.956889, -0.352683, -0.017794}
      //1428 476 {0.956889, -0.352683, -0.017794}
      {
        int idx[] = { 406, 408, 413, 415, 465, 468, 473, 476, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //171 057 {0.581339, -0.149219, 0.291680}
      //180 060 {0.581339, -0.149219, 0.291680}
      //186 062 {0.583216, 0.232926, 0.394389}
      //189 063 {0.583216, 0.232926, 0.394389}
      //195 065 {0.583626, 0.694177, 0.392306}
      //198 066 {0.571708, 0.694188, -0.416034}
      //204 068 {0.571238, 0.232938, -0.418118}
      //207 069 {0.571238, 0.232938, -0.418118}
      //213 071 {0.572380, -0.149212, -0.315408}
      //219 073 {0.572380, -0.149212, -0.315408}
      //558 186 {0.581885, 1.091331, 0.247236}
      //573 191 {0.574212, 1.091338, -0.270963}
      //576 192 {0.581885, 1.091331, 0.247236}
      //600 200 {0.574212, 1.091338, -0.270963}
      //930 310 {0.571238, 0.232938, -0.418118}
      //933 311 {0.572380, -0.149212, -0.315408}
      //939 313 {0.572380, -0.149212, -0.315408}
      //948 316 {0.571708, 0.694188, -0.416034}
      //951 317 {0.571238, 0.232938, -0.418118}
      //957 319 {0.571238, 0.232938, -0.418118}
      //966 322 {0.574212, 1.091338, -0.270963}
      //969 323 {0.571708, 0.694188, -0.416034}
      //975 325 {0.571708, 0.694188, -0.416034}
      //993 331 {0.574212, 1.091338, -0.270963}
      //1002 334 {0.581885, 1.091331, 0.247236}
      //1020 340 {0.583626, 0.694177, 0.392306}
      //1026 342 {0.583626, 0.694177, 0.392306}
      //1029 343 {0.581885, 1.091331, 0.247236}
      //1038 346 {0.583216, 0.232926, 0.394389}
      //1044 348 {0.583216, 0.232926, 0.394389}
      //1047 349 {0.583626, 0.694177, 0.392306}
      //1056 352 {0.581339, -0.149219, 0.291680}
      //1062 354 {0.581339, -0.149219, 0.291680}
      //1065 355 {0.583216, 0.232926, 0.394389}
      //1077 359 {0.581339, -0.149219, 0.291680}
      //1083 361 {0.581339, -0.149219, 0.291680}
      //1164 388 {0.572380, -0.149212, -0.315408}
      //1170 390 {0.572380, -0.149212, -0.315408}
      //1227 409 {0.581339, -0.149219, 0.291680}
      //1236 412 {0.572380, -0.149212, -0.315408}
      //1359 453 {0.574212, 1.091338, -0.270963}
      //1365 455 {0.571708, 0.694188, -0.416034}
      //1374 458 {0.571708, 0.694188, -0.416034}
      //1377 459 {0.583626, 0.694177, 0.392306}
      //1386 462 {0.583626, 0.694177, 0.392306}
      //1392 464 {0.581885, 1.091331, 0.247236}
      //1410 470 {0.581339, -0.149219, 0.291680}
      //1413 471 {0.572380, -0.149212, -0.315408}
      {
        int idx[] = { 57, 60, 62, 63, 65, 66, 68, 69, 71, 73, 186, 191, 192, 200, 310, 
                      311, 313, 316, 317, 319, 322, 323, 325, 331, 334, 340, 342, 343, 
                      346, 348, 349, 352, 354, 355, 359, 361, 388, 390, 409, 412, 453, 
                      455, 458, 459, 462, 464, 470, 471, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //1095 365 {0.389382, -0.656846, 0.166823}
      //1101 367 {0.389382, -0.656846, 0.166823}
      //1149 383 {0.384593, -0.656843, -0.186195}
      //1155 385 {0.384593, -0.656843, -0.186195}
      {
        int idx[] = { 365,367,383,385,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //564 188 {0.354908, 1.325953, -0.010446}
      //567 189 {0.354908, 1.325953, -0.010446}
      //579 193 {0.354908, 1.325953, -0.010446}
      //588 196 {0.354908, 1.325953, -0.010446}
      //597 199 {0.354908, 1.325953, -0.010446}
      {
        int idx[] = { 188,189,193,196,199,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //591 197 {0.288338, 1.460179, -0.019098}
      {
        int idx[] = { 197, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //1074 358 {0.199635, -0.364357, 0.152904}
      //1092 364 {0.199635, -0.364357, 0.152904}
      //1119 373 {0.199635, -0.364357, 0.152904}
      //1128 376 {0.193223, -0.364353, -0.172992}
      //1146 382 {0.193223, -0.364353, -0.172992}
      //1173 391 {0.193223, -0.364353, -0.172992}
      //1221 407 {0.199635, -0.364357, 0.152904}
      //1230 410 {0.199635, -0.364357, 0.152904}
      //1233 411 {0.193223, -0.364353, -0.172992}
      //1242 414 {0.193223, -0.364353, -0.172992}
      {
        int idx[] = { 358,364,373,376,382,391,407,410,411,414, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //1110 370 {0.157074, -0.503665, -0.017842}
      //1116 372 {0.157074, -0.503665, -0.017842}
      //1131 377 {0.157074, -0.503665, -0.017842}
      //1137 379 {0.157074, -0.503665, -0.017842}
      //1215 405 {0.157074, -0.503665, -0.017842}
      //1248 416 {0.157074, -0.503665, -0.017842}
      {
        int idx[] = { 370,372,377,379,405,416, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii; 
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //1104 368 {0.139649, -0.683434, 0.172252}
      //1158 386 {0.134860, -0.683430, -0.190388}
      {
        int idx[] = { 368,386,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii;
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //552 184 {-0.187596, 1.774881, -0.017535}
      //735 245 {-0.187596, 1.774881, -0.017535}
      {
        int idx[] = { 184,245,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii;
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //516 172 {-0.267770, -0.458273, -0.190623}
      //525 175 {-0.267770, -0.458273, -0.190623}
      //528 176 {-0.265469, -0.539220, -0.017226}
      //534 178 {-0.265469, -0.539220, -0.017226}
      //537 179 {-0.263027, -0.458277, 0.173962}
      //543 181 {-0.263027, -0.458277, 0.173962}
      //1071 357 {-0.263027, -0.458277, 0.173962}
      //1080 360 {-0.263027, -0.458277, 0.173962}
      //1089 363 {-0.263027, -0.458277, 0.173962}
      //1098 366 {-0.263027, -0.458277, 0.173962}
      //1107 369 {-0.265469, -0.539220, -0.017226}
      //1113 371 {-0.263027, -0.458277, 0.173962}
      //1122 374 {-0.263027, -0.458277, 0.173962}
      //1125 375 {-0.267770, -0.458273, -0.190623}
      //1134 378 {-0.267770, -0.458273, -0.190623}
      //1140 380 {-0.265469, -0.539220, -0.017226}
      //1143 381 {-0.267770, -0.458273, -0.190623}
      //1152 384 {-0.267770, -0.458273, -0.190623}
      //1167 389 {-0.267770, -0.458273, -0.190623}
      //1176 392 {-0.267770, -0.458273, -0.190623}
      {
        int idx[] = { 172,175,176,178,179,181,357,360,363,366,369,371,374,375,378,380,381,384,389,392, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii;
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //555 185 {-0.302373, 2.225360, -0.016608}
      {
        int idx[] = { 185, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii;
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //156 052 {-1.178311, 1.362515, -0.010047}
      //165 055 {-1.178311, 1.362515, -0.010047}
      //549 183 {-1.178311, 1.362515, -0.010047}
      //729 243 {-1.178311, 1.362515, -0.010047}
      //981 327 {-1.178311, 1.362515, -0.010047}
      //1014 338 {-1.178311, 1.362515, -0.010047}
      {
        int idx[] = { 52,55,183,243,327,338, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii;
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //153 051 {-1.440711, 1.066319, 0.368437}
      //168 056 {-1.449087, 1.066327, -0.375763}
      //513 171 {-1.450822, -0.196619, -0.432703}
      //546 182 {-1.441195, -0.196627, 0.425376}
      //741 247 {-1.450822, -0.196619, -0.432703}
      //771 257 {-1.441195, -0.196627, 0.425376}
      //777 259 {-1.439693, 0.174198, 0.532284}
      //786 262 {-1.439693, 0.174198, 0.532284}
      //789 263 {-1.439283, 0.675618, 0.529550}
      //795 265 {-1.440711, 1.066319, 0.368437}
      //804 268 {-1.449087, 1.066327, -0.375763}
      //813 271 {-1.451202, 0.675629, -0.536876}
      //816 272 {-1.451672, 0.174209, -0.539610}
      //822 274 {-1.451672, 0.174209, -0.539610}
      //858 286 {-1.451672, 0.174209, -0.539610}
      //864 288 {-1.451672, 0.174209, -0.539610}
      //867 289 {-1.450822, -0.196619, -0.432703}
      //876 292 {-1.449087, 1.066327, -0.375763}
      //879 293 {-1.451202, 0.675629, -0.536876}
      //885 295 {-1.451202, 0.675629, -0.536876}
      //894 298 {-1.439283, 0.675618, 0.529550}
      //900 300 {-1.439283, 0.675618, 0.529550}
      //903 301 {-1.440711, 1.066319, 0.368437}
      //912 304 {-1.441195, -0.196627, 0.425376}
      //915 305 {-1.439693, 0.174198, 0.532284}
      //921 307 {-1.439693, 0.174198, 0.532284}
      //927 309 {-1.451672, 0.174209, -0.539610}
      //936 312 {-1.451672, 0.174209, -0.539610}
      //942 314 {-1.450822, -0.196619, -0.432703}
      //945 315 {-1.451202, 0.675629, -0.536876}
      //954 318 {-1.451202, 0.675629, -0.536876}
      //960 320 {-1.451672, 0.174209, -0.539610}
      //963 321 {-1.449087, 1.066327, -0.375763}
      //972 324 {-1.449087, 1.066327, -0.375763}
      //978 326 {-1.451202, 0.675629, -0.536876}
      //987 329 {-1.449087, 1.066327, -0.375763}
      //996 332 {-1.449087, 1.066327, -0.375763}
      //999 333 {-1.440711, 1.066319, 0.368437}
      //1008 336 {-1.440711, 1.066319, 0.368437}
      //1017 339 {-1.439283, 0.675618, 0.529550}
      //1023 341 {-1.440711, 1.066319, 0.368437}
      //1032 344 {-1.440711, 1.066319, 0.368437}
      //1035 345 {-1.439693, 0.174198, 0.532284}
      //1041 347 {-1.439283, 0.675618, 0.529550}
      //1050 350 {-1.439283, 0.675618, 0.529550}
      //1053 351 {-1.441195, -0.196627, 0.425376}
      //1059 353 {-1.439693, 0.174198, 0.532284}
      //1068 356 {-1.439693, 0.174198, 0.532284}
      //1086 362 {-1.441195, -0.196627, 0.425376}
      //1161 387 {-1.450822, -0.196619, -0.432703}
      {
        int idx[] = { 51,56,171,182,247,257,259,262,263,265,268,271,272,274,286,288,289,292,293,
                      295,298,300,301,304,305,307,309,312,314,315,318,320,321,324,326,329,332,333,
                      336,339,341,344,345,347,350,351,353,356,362,387, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii;
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //519 173 {-1.599839, -0.511679, -0.007429}
      //522 174 {-1.599839, -0.511679, -0.007429}
      //531 177 {-1.599839, -0.511679, -0.007429}
      //540 180 {-1.599839, -0.511679, -0.007429}
      //744 248 {-1.599839, -0.511679, -0.007429}
      //750 250 {-1.599839, -0.511679, -0.007429}
      //759 253 {-1.599839, -0.511679, -0.007429}
      //768 256 {-1.599839, -0.511679, -0.007429}
      {
        int idx[] = { 173,174,177,180,248,250,253,256, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii;
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //840 280 {-2.228655, -0.728475, -0.650837}
      //849 283 {-2.215483, -0.728483, 0.657006}
      {
        int idx[] = { 280,283,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii;
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //843 281 {-2.823029, -0.686539, -0.662640}
      //852 284 {-2.811438, -0.686546, 0.670077}
      {
        int idx[] = { 281,284,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii;
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //024 008 {-3.196742, -0.049302, 0.447789}
      //762 254 {-3.196742, -0.049302, 0.447789}
      //765 255 {-3.196742, -0.049302, 0.447789}
      //846 282 {-3.196742, -0.049302, 0.447789}
      //909 303 {-3.196742, -0.049302, 0.447789}
      //918 306 {-3.196742, -0.049302, 0.447789}
      {
        int idx[] = { 8,254,255,282,303,306, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii;
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //030 010 {-3.354060, 0.467534, 0.517028}
      //039 013 {-3.354060, 0.467534, 0.517028}
      //048 016 {-3.354060, 0.467534, 0.517028}
      //051 017 {-3.355715, 0.978260, 0.327890}
      //057 019 {-3.363465, 0.978265, -0.319692}
      //060 020 {-3.366217, 0.467542, -0.508830}
      //066 022 {-3.366217, 0.467542, -0.508830}
      //075 025 {-3.366217, 0.467542, -0.508830}
      //780 260 {-3.354060, 0.467534, 0.517028}
      //783 261 {-3.354060, 0.467534, 0.517028}
      //792 264 {-3.355715, 0.978260, 0.327890}
      //807 269 {-3.363465, 0.978265, -0.319692}
      //810 270 {-3.366217, 0.467542, -0.508830}
      //819 273 {-3.366217, 0.467542, -0.508830}
      //873 291 {-3.363465, 0.978265, -0.319692}
      //882 294 {-3.363465, 0.978265, -0.319692}
      //888 296 {-3.366217, 0.467542, -0.508830}
      //891 297 {-3.354060, 0.467534, 0.517028}
      //897 299 {-3.355715, 0.978260, 0.327890}
      //906 302 {-3.355715, 0.978260, 0.327890}
      //1437 479 {-3.363465, 0.978265, -0.319692}
      //1443 481 {-3.363465, 0.978265, -0.319692}
      //1452 484 {-3.355715, 0.978260, 0.327890}
      //1458 486 {-3.355715, 0.978260, 0.327890}
      {
        int idx[] = { 10,13,16,17,19,20,22,25,260,261,264,269,270,273,291,294,296,297,299,302,479,481,484,486, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii;
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //003 001 {-3.482355, -0.314469, 0.028982}
      //012 004 {-3.482355, -0.314469, 0.028982}
      //021 007 {-3.482355, -0.314469, 0.028982}
      //753 251 {-3.482355, -0.314469, 0.028982}
      //756 252 {-3.482355, -0.314469, 0.028982}
      //834 278 {-3.482355, -0.314469, 0.028982}
      {
        int idx[] = { 1,4,7,251,252,278,};
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii;
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //000 000 {-3.563356, -0.050557, -0.371373}
      //015 005 {-3.518670, 0.077497, 0.434307}
      //018 006 {-3.518670, 0.077497, 0.434307}
      //027 009 {-3.518670, 0.077497, 0.434307}
      //078 026 {-3.563356, -0.050557, -0.371373}
      //159 053 {-3.592829, 1.130262, 0.000657}
      //162 054 {-3.592829, 1.130262, 0.000657}
      //462 154 {-3.563356, -0.050557, -0.371373}
      //474 158 {-3.518670, 0.077497, 0.434307}
      //480 160 {-3.518670, 0.077497, 0.434307}
      //507 169 {-3.563356, -0.050557, -0.371373}
      //774 258 {-3.518670, 0.077497, 0.434307}
      //798 266 {-3.592829, 1.130262, 0.000657}
      //801 267 {-3.592829, 1.130262, 0.000657}
      //825 275 {-3.563356, -0.050557, -0.371373}
      //828 276 {-3.563356, -0.050557, -0.371373}
      //855 285 {-3.563356, -0.050557, -0.371373}
      //924 308 {-3.518670, 0.077497, 0.434307}
      //1434 478 {-3.592829, 1.130262, 0.000657}
      //1461 487 {-3.592829, 1.130262, 0.000657}
      {
        int idx[] = { 0,5,6,9,26,53,54,154,158,160,169,258,266,267,275,276,285,308,478,487, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii;
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //489 163 {-4.281947, 0.878867, 0.003449}
      //498 166 {-4.281947, 0.878867, 0.003449}
      //1431 477 {-4.281947, 0.878867, 0.003449}
      //1440 480 {-4.281947, 0.878867, 0.003449}
      //1455 485 {-4.281947, 0.878867, 0.003449}
      //1464 488 {-4.281947, 0.878867, 0.003449}
      {
        int idx[] = { 163,166,477,480,485,488, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii;
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //006 002 {-4.318616, -0.053987, 0.004143}
      //009 003 {-4.318616, -0.053987, 0.004143}
      //033 011 {-4.382598, 0.380261, 0.198944}
      //036 012 {-4.382598, 0.380261, 0.198944}
      //042 014 {-4.345534, 0.664362, 0.218885}
      //045 015 {-4.345534, 0.664362, 0.218885}
      //054 018 {-4.349825, 0.664365, -0.202633}
      //063 021 {-4.349825, 0.664365, -0.202633}
      //069 023 {-4.387043, 0.380264, -0.181603}
      //072 024 {-4.387043, 0.380264, -0.181603}
      //348 116 {-4.318616, -0.053987, 0.004143}
      //354 118 {-4.318616, -0.053987, 0.004143}
      //381 127 {-4.382598, 0.380261, 0.198944}
      //390 130 {-4.382598, 0.380261, 0.198944}
      //393 131 {-4.345534, 0.664362, 0.218885}
      //399 133 {-4.345534, 0.664362, 0.218885}
      //408 136 {-4.345534, 0.664362, 0.218885}
      //420 140 {-4.349825, 0.664365, -0.202633}
      //426 142 {-4.349825, 0.664365, -0.202633}
      //435 145 {-4.349825, 0.664365, -0.202633}
      //438 146 {-4.387043, 0.380264, -0.181603}
      //444 148 {-4.387043, 0.380264, -0.181603}
      //465 155 {-4.318616, -0.053987, 0.004143}
      //471 157 {-4.318616, -0.053987, 0.004143}
      //483 161 {-4.382598, 0.380261, 0.198944}
      //486 162 {-4.345534, 0.664362, 0.218885}
      //501 167 {-4.349825, 0.664365, -0.202633}
      //504 168 {-4.387043, 0.380264, -0.181603}
      //1254 418 {-4.387043, 0.380264, -0.181603}
      //1281 427 {-4.382598, 0.380261, 0.198944}
      //1446 482 {-4.349825, 0.664365, -0.202633}
      //1449 483 {-4.345534, 0.664362, 0.218885}
      {
        int idx[] = { 2,3,11,12,14,15,18,21,23,24,116,118,127,130,131,133,136,140,142,145,146,148,155,157,161,162,167,168,418,427,482,483, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii;
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //336 112 {-4.535238, 0.159280, -0.085091}
      //345 115 {-4.535238, 0.159280, -0.085091}
      //357 119 {-4.532741, 0.159278, 0.102805}
      //366 122 {-4.532741, 0.159278, 0.102805}
      //372 124 {-4.532741, 0.159278, 0.102805}
      //378 126 {-4.523820, 0.323676, 0.001114}
      //411 137 {-4.568282, 0.724235, 0.008114}
      //417 139 {-4.568282, 0.724235, 0.008114}
      //447 149 {-4.525612, 0.323678, 0.018667}
      //450 150 {-4.535238, 0.159280, -0.085091}
      //459 153 {-4.535238, 0.159280, -0.085091}
      //468 156 {-4.532741, 0.159278, 0.102805}
      //477 159 {-4.532741, 0.159278, 0.102805}
      //492 164 {-4.568282, 0.724235, 0.008114}
      //495 165 {-4.568282, 0.724235, 0.008114}
      //510 170 {-4.535238, 0.159280, -0.085091}
      //1251 417 {-4.525612, 0.323678, 0.018667}
      //1257 419 {-4.535238, 0.159280, -0.085091}
      //1260 420 {-4.525612, 0.323678, 0.018667}
      //1263 421 {-4.535238, 0.159280, -0.085091}
      //1272 424 {-4.532741, 0.159278, 0.102805}
      //1275 425 {-4.523820, 0.323676, 0.001114}
      //1278 426 {-4.532741, 0.159278, 0.102805}
      //1284 428 {-4.523820, 0.323676, 0.001114}
      {
        int idx[] = { 112,115,119,122,124,126,137,139,149,150,153,156,159,164,165,170,417,419,420,421,424,425,426,428, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii;
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //339 113 {-4.638330, 0.149347, 0.007086}
      //342 114 {-4.638330, 0.149347, 0.007086}
      //351 117 {-4.638330, 0.149347, 0.007086}
      //363 121 {-4.638330, 0.149347, 0.007086}
      //384 128 {-4.654118, 0.449342, 0.001114}
      //387 129 {-4.654118, 0.449342, 0.001114}
      //396 132 {-4.654118, 0.449342, 0.001114}
      //429 143 {-4.655901, 0.449344, 0.018667}
      //432 144 {-4.655901, 0.449344, 0.018667}
      //441 147 {-4.655901, 0.449344, 0.018667}
      {
        int idx[] = { 113,114,117,121,128,129,132,143,144,147, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii;
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //333 111 {-4.788940, 0.265594, 0.009013}
      //360 120 {-4.788940, 0.265594, 0.009013}
      //369 123 {-4.788940, 0.265594, 0.009013}
      //375 125 {-4.718528, 0.351371, 0.001114}
      //402 134 {-4.784564, 0.579400, 0.009013}
      //405 135 {-4.784564, 0.579400, 0.009013}
      //414 138 {-4.784564, 0.579400, 0.009013}
      //423 141 {-4.784564, 0.579400, 0.009013}
      //453 151 {-4.788940, 0.265594, 0.009013}
      //456 152 {-4.720216, 0.351373, 0.018667}
      //1266 422 {-4.720216, 0.351373, 0.018667}
      //1269 423 {-4.718528, 0.351371, 0.001114}
      {
        int idx[] = { 111,120,123,125,134,135,138,141,151,152,422,423, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii;
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //738 246 {-3.207148, -0.049295, -0.440878}
      //747 249 {-3.207148, -0.049295, -0.440878}
      //831 277 {-3.207148, -0.049295, -0.440878}
      //837 279 {-3.207148, -0.049295, -0.440878}
      //861 287 {-3.207148, -0.049295, -0.440878}
      //870 290 {-3.207148, -0.049295, -0.440878}
      {
        int idx[] = { 246,249,277,279,287,290, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii;
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }
      //582 194 {0.041021, 1.354216, -0.010364}
      //585 195 {0.041021, 1.354216, -0.010364}
      //594 198 {0.041021, 1.354216, -0.010364}
      //732 244 {0.041021, 1.354216, -0.010364}
      //984 328 {0.041021, 1.354216, -0.010364}
      //990 330 {0.041021, 1.354216, -0.010364}
      //1005 335 {0.041021, 1.354216, -0.010364}
      //1011 337 {0.041021, 1.354216, -0.010364}
      {
        int idx[] = { 194,195,198,244,328,330,335,337, };
        float width = getMoveWidth(IwashiData.vertices[0+3*idx[0]]) * s;
        int ii;
        for (ii=0; ii<idx.length; ii++) {
          IwashiData.vertices[2+3*idx[ii]] = IwashiData.org_vertices[2+3*idx[ii]] + width;
        }
      }


    ByteBuffer vbb = ByteBuffer.allocateDirect(IwashiData.vertices.length * 4);
    vbb.order(ByteOrder.nativeOrder());
    mVertexBuffer = vbb.asFloatBuffer();
    mVertexBuffer.put(IwashiData.vertices);
    mVertexBuffer.position(0);

      tick = current;
    }
  }

  int angle = 0;
  public void draw(GL10 gl10) {
    gl10.glPushMatrix();

    animate();

    // forDebug
    //gl10.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
    gl10.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
    //gl10.glRotatef((float)(angle % 360), 0.0f, 0.0f, 1.0f);
    //angle += 10;
    //gl10.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);

    gl10.glColor4f(1,1,1,1);
    gl10.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
    gl10.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);
    gl10.glEnable(GL10.GL_TEXTURE_2D);
    gl10.glBindTexture(GL10.GL_TEXTURE_2D, texid);
    gl10.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
    gl10.glDrawArrays(GL10.GL_TRIANGLES, 0, IwashiData.iwashiNumVerts);
    gl10.glPopMatrix();
  }
}
