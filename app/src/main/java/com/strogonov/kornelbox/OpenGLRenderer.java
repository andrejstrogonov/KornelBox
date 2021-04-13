package com.strogonov.kornelbox;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLES32;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import org.json.JSONArray;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderer implements GLSurfaceView.Renderer {

    private final int mBytesPerFloat = 4;
    private final FloatBuffer mFloorVerticesData;
    private final FloatBuffer mLightVerticesData;
    private final FloatBuffer mCellingVerticesData;
    private final FloatBuffer mBackwallVerticesData;
    private final FloatBuffer mRightwallVerticesData;
    private final FloatBuffer mLeftwallVerticesData;
    private final FloatBuffer mShortblockData;
    private final FloatBuffer mTallblockData;


    private float[] mViewMatrix=new float[16];
    private float[] mProjectionMatrix=new float[16];

    public OpenGLRenderer() {
        final float[] FloorVerticesData={
              0.5528f,0.0f,0.0f,
                0.0f,0.0f,0.0f,
                0.0f,0.0f,0.5592f,
                0.5496f,0.0f,0.5592f
        };
         mFloorVerticesData = ByteBuffer.allocateDirect(FloorVerticesData.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mFloorVerticesData.put(FloorVerticesData).position(0);

        final float[]LightVerticesData={
                0.346f,0.5488f,0.227f,
                0.343f,0.5488f,0.332f,
                0.213f,0.5488f,0.332f,
                0.213f,0.5488f,0.227f
        };
        mLightVerticesData = ByteBuffer.allocateDirect(LightVerticesData.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mLightVerticesData.put(LightVerticesData).position(0);
        final float[]CellingVerticesData={
            0.556f,0.5488f,0.0f,
            0.556f,0.5488f,0.5592f,
            0.0f,0.5488f,0.5592f,
            0.0f,0.5488f,0.5592f,
            0.0f,0.548f,0.0f
        };
        mCellingVerticesData = ByteBuffer.allocateDirect(CellingVerticesData.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCellingVerticesData.put(CellingVerticesData).position(0);


        final float[]BackwallVerticesData={
             0.5496f,0.0f,0.5592f,
             0.0f,0.0f,0.5592f,
             0.0f,0.5488f,0.5592f,
             0.556f,0.5488f,0.5592f
        };
        mBackwallVerticesData = ByteBuffer.allocateDirect(BackwallVerticesData.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mBackwallVerticesData.put(BackwallVerticesData).position(0);
        final float[]RightwallVerticesData={
                0.0f,0.0f,0.5592f,
                0.0f,0.0f,0.0f,
                0.0f,0.5488f,0.0f,
                0.0f,0.5488f,0.5592f
        };
        mRightwallVerticesData = ByteBuffer.allocateDirect(RightwallVerticesData.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mRightwallVerticesData.put(RightwallVerticesData).position(0);
        final float[]LeftwallVerticesData={
                0.5528f,0.0f,0.0f,
                0.5496f,0.0f,0.5492f,
                0.556f,0.5488f,0.5592f,
                0.556f,0.5488f,0.0f
        };
        mLeftwallVerticesData = ByteBuffer.allocateDirect(LeftwallVerticesData.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mLeftwallVerticesData.put(LeftwallVerticesData).position(0);
        final float[]ShortblockData={
                0.130f, 0.165f, 0.065f,
                0.82f, 0.165f, 0.225f,
                0.240f,0.165f, 0.272f,
                0.290f,0.165f, 0.114f,

                0.290f, 0.0f, 0.114f,
                0.290f, 0.165f, 0.114f,
                0.240f, 0.165f, 0.272f,
                0.240f, 0.0f, 0.272f,

                0.130f, 0.0f,0.65f,
                0.130f, 0.165f, 0.065f,
                0.290f, 0.165f,0.114f,
                0.290f, 0.0f, 0.114f,

                0.082f,0.0f, 0.225f,
                0.082f, 0.165f, 0.225f,
                0.130f, 0.165f,  0.065f,
                0.130f, 0.0f,0.065f,

                0.240f, 0.0f, 0.272f,
                0.240f, 0.165f, 0.272f,
                0.082f, 0.165f, 0.225f,
                0.82f, 0.0f, 0.225f
        };
        mShortblockData = ByteBuffer.allocateDirect(ShortblockData.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mShortblockData.put(ShortblockData).position(0);

        final float[] TallblockData={
            0.423f, 0.330f, 0.247f,
            0.265f, 0.330f, 0.296f,
            0.314f, 0.330f, 0.456f,
            0.472f, 0.330f, 0.406f,

            0.423f,0.0f, 0.247f,
            0.423f, 0.330f, 0.247f,
            0.472f, 0.330f, 0.406f,
            0.472f, 0.0f, 0.406f,

            0.472f, 0.0f, 0.406f,
            0.472f, 0.330f, 0.406f,
            0.314f, 0.330f, 0.456f,
            0.314f, 0.0f,0.456f,

            0.314f, 0.0f, 0.456f,
            0.314f,0.330f, 0.456f,
            0.265f, 0.330f, 0.296f,
            0.265f, 0.0f,0.296f,

            0.265f, 0.0f,0.296f,
            0.265f, 0.330f, 0.296f,
            0.423f, 0.330f, 0.247f,
            0.423f, 0.0f,0.247f
        };
        mTallblockData = ByteBuffer.allocateDirect(TallblockData.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mTallblockData.put(TallblockData).position(0);
    };


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        int width = 1;//не могу найти соответствий с фотоаппаратом
        int height= 1;
        final float ratio = (float) (width / height);
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 10.0f;
        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
        GLES20.glViewport(0, 0, width, height);
        // Position the eye behind the origin.
        final float eyeX=0.0f;
        final float eyeY=0.0f;
        final float eyeZ=1.0f;
        // We are looking toward the distance
        final float lookX=0.0f;
        final float lookY=0.0f;
        final float lookZ=-5.0f;
        // Set our up vector.
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;
        Matrix.setLookAtM(mViewMatrix,0,eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        drawFloor(mFloorVerticesData);
        drawLight(mLightVerticesData);
        drawCeiling(mCellingVerticesData);
        drawBackwall(mBackwallVerticesData);
        drawRightwall(mRightwallVerticesData);
        drawLeftwall(mLeftwallVerticesData);
        drawShortblock(mShortblockData);
        drawTallblock(mTallblockData);
    }

    private void drawShortblock(FloatBuffer mShortblockData) {
      mShortblockData.position(mPositionOffset);
        //здесь менять на  TriangleStrips
      GLES32.glVertexAttribIPointer(mPositionHandle, mPositionDataSize, GLES32.GL_FLOAT, false,
              mStrideBytes, mShortblockData);
      GLES32.glEnableVertexAttribArray(mPositionHandle);
      mShortblockData.position(mColorOffset);
      GLES32.glVertexAttribPointer(mColorHandle, mColorDataSize, GLES32.GL_FLOAT, false,
                mStrideBytes, aTriangleBuffer);
      GLES32.glEnableVertexAttribArray(mColorHandle);
      GLES32.glDrawArrays(GLES32.GL_TRIANGLE_STRIP,0,4);
    }

    private void drawTallblock(FloatBuffer mTallblockData) {
    }

    private void drawLeftwall(FloatBuffer mLeftwallVerticesData) {
        mLeftwallVerticesData.position(mPositionOffset);
        //здесь менять на  TriangleStrips
        GLES32.glVertexAttribIPointer(mPositionHandle, mPositionDataSize, GLES32.GL_FLOAT, false,
                mStrideBytes, mLeftwallVerticesData);
        GLES32.glEnableVertexAttribArray(mPositionHandle);
        mShortblockData.position(mColorOffset);
        GLES32.glVertexAttribPointer(mColorHandle, mColorDataSize, GLES32.GL_FLOAT, false,
                mStrideBytes, aTriangleBuffer);
        GLES32.glEnableVertexAttribArray(mColorHandle);
        GLES32.glDrawArrays(GLES32.GL_TRIANGLE_STRIP,0,4);
    }

    private void drawRightwall(FloatBuffer mRightwallVerticesData) {
        mRightwallVerticesData.position(mPositionOffset);
        //здесь менять на  TriangleStrips
        GLES32.glVertexAttribIPointer(mPositionHandle, mPositionDataSize, GLES32.GL_FLOAT, false,
                mStrideBytes, mRightwallVerticesData);
        GLES32.glEnableVertexAttribArray(mPositionHandle);
        mShortblockData.position(mColorOffset);
        GLES32.glVertexAttribPointer(mColorHandle, mColorDataSize, GLES32.GL_FLOAT, false,
                mStrideBytes, aTriangleBuffer);
        GLES32.glEnableVertexAttribArray(mColorHandle);
        GLES32.glDrawArrays(GLES32.GL_TRIANGLE_STRIP,0,4);
    }

    private void drawBackwall(FloatBuffer mBackwallVerticesData) {
        mRightwallVerticesData.position(mPositionOffset);
        //здесь менять на  TriangleStrips
        GLES32.glVertexAttribIPointer(mPositionHandle, mPositionDataSize, GLES32.GL_FLOAT, false,
                mStrideBytes, mBackwallVerticesData);
        GLES32.glEnableVertexAttribArray(mPositionHandle);
        mShortblockData.position(mColorOffset);
        GLES32.glVertexAttribPointer(mColorHandle, mColorDataSize, GLES32.GL_FLOAT, false,
                mStrideBytes, aTriangleBuffer);
        GLES32.glEnableVertexAttribArray(mColorHandle);
        GLES32.glDrawArrays(GLES32.GL_TRIANGLE_STRIP,0,4);
    }

    private void drawCeiling(FloatBuffer mCellingVerticesData) {
        mCellingVerticesData.position(mPositionOffset);
        //здесь менять на  TriangleStrips
        GLES32.glVertexAttribIPointer(mPositionHandle, mPositionDataSize, GLES32.GL_FLOAT, false,
                mStrideBytes, mCellingVerticesData);
        GLES32.glEnableVertexAttribArray(mPositionHandle);
        mShortblockData.position(mColorOffset);
        GLES32.glVertexAttribPointer(mColorHandle, mColorDataSize, GLES32.GL_FLOAT, false,
                mStrideBytes, aTriangleBuffer);
        GLES32.glEnableVertexAttribArray(mColorHandle);
        GLES32.glDrawArrays(GLES32.GL_TRIANGLE_STRIP,0,4);
    }

    private void drawLight(FloatBuffer mLightVerticesData) {
        mLightVerticesData.position(mPositionOffset);
        //здесь менять на  TriangleStrips
        GLES32.glVertexAttribIPointer(mPositionHandle, mPositionDataSize, GLES32.GL_FLOAT, false,
                mStrideBytes, mLightVerticesData);
        GLES32.glEnableVertexAttribArray(mPositionHandle);
        mShortblockData.position(mColorOffset);
        GLES32.glVertexAttribPointer(mColorHandle, mColorDataSize, GLES32.GL_FLOAT, false,
                mStrideBytes, aTriangleBuffer);
        GLES32.glEnableVertexAttribArray(mColorHandle);
        GLES32.glDrawArrays(GLES32.GL_TRIANGLE_STRIP,0,4);
    }

    private void drawFloor(FloatBuffer mFloorVerticesData) {
        mFloorVerticesData.position(mPositionOffset);
        //здесь менять на  TriangleStrips
        GLES32.glVertexAttribIPointer(mPositionHandle, mPositionDataSize, GLES32.GL_FLOAT, false,
                mStrideBytes, mFloorVerticesData);
        GLES32.glEnableVertexAttribArray(mPositionHandle);
        mShortblockData.position(mColorOffset);
        GLES32.glVertexAttribPointer(mColorHandle, mColorDataSize, GLES32.GL_FLOAT, false,
                mStrideBytes, aTriangleBuffer);
        GLES32.glEnableVertexAttribArray(mColorHandle);
        GLES32.glDrawArrays(GLES32.GL_TRIANGLE_STRIP,0,4);
    }
    
}
