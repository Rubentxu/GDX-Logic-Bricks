package com.indignado.logicbricks.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.indignado.logicbricks.core.data.Transform2D;

/**
 * @author Rubentxu.
 */
public class GUIBatch {
    private Mesh mesh;
    private final VerticesData verticesData;
    private final float [] vertices;

    Texture lastTexture = null;
    float invTexWidth = 0, invTexHeight = 0;

    boolean drawing = false;

    private final Matrix4 transformMatrix = new Matrix4();
    private final Matrix4 projectionMatrix = new Matrix4();
    private final Matrix4 combinedMatrix = new Matrix4();

    private boolean blendingDisabled = false;
    private int blendSrcFunc = GL20.GL_SRC_ALPHA;
    private int blendDstFunc = GL20.GL_ONE_MINUS_SRC_ALPHA;

    private final ShaderProgram shader;
    private ShaderProgram customShader = null;
    private boolean ownsShader;

    private float color;

    /** Number of render calls since the last {@link #begin()}. **/
    public int renderCalls = 0;

    /** Number of rendering calls, ever. Will not be reset unless set manually. **/
    public int totalRenderCalls = 0;

    /** The maximum number of sprites rendered in one batch so far. **/
    public int maxSpritesInBatch = 0;


    public GUIBatch () {
        this(1000, null);
    }


    public GUIBatch (int size) {
        this(size, null);
    }


    public GUIBatch (int size, ShaderProgram defaultShader) {
        // 32767 is max index, so 32767 / 6 - (32767 / 6 % 3) = 5460.
        if (size > 5460) throw new IllegalArgumentException("Can't have more than 5460 sprites per batch: " + size);

        mesh = new Mesh(Mesh.VertexDataType.VertexArray, false, size * 4, size * 6, new VertexAttribute(
                VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(
                VertexAttributes.Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE), new VertexAttribute(
                VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE + "0"));

        projectionMatrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        verticesData = new VerticesData();
        vertices = new float[size * 24];
        color = new Color().toFloatBits();

        short[] indices = new short[size * 6];
        int v = 0;
        for (int i = 0; i < indices.length; i += 6, v += 4) {
            indices[i] = (short)(v);
            indices[i + 1] = (short)(v + 2);
            indices[i + 2] = (short)(v + 1);
            indices[i + 3] = (short)(v + 1);
            indices[i + 4] = (short)(v + 2);
            indices[i + 5] = (short)(v + 3);
        }
        mesh.setIndices(indices);

        if (defaultShader == null) {
            shader = createDefaultShader();
            ownsShader = true;
        } else
            shader = defaultShader;


    }

    /** Returns a new instance of the default shader used by SpriteBatch for GL2 when no shader is specified. */
    static public ShaderProgram createDefaultShader () {
        String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
                + "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
                + "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
                + "uniform mat4 u_projTrans;\n" //
                + "varying vec4 v_color;\n" //
                + "varying vec2 v_texCoords;\n" //
                + "\n" //
                + "void main()\n" //
                + "{\n" //
                + "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
                + "   v_color.a = v_color.a * (255.0/254.0);\n" //
                + "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
                + "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
                + "}\n";
        String fragmentShader = "#ifdef GL_ES\n" //
                + "#define LOWP lowp\n" //
                + "precision mediump float;\n" //
                + "#else\n" //
                + "#define LOWP \n" //
                + "#endif\n" //
                + "varying LOWP vec4 v_color;\n" //
                + "varying vec2 v_texCoords;\n" //
                + "uniform sampler2D u_texture;\n" //
                + "void main()\n"//
                + "{\n" //
                + "  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n" //
                + "}";

        ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
        if (shader.isCompiled() == false) throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
        return shader;
    }


    public void begin () {
        if (drawing) throw new IllegalStateException("SpriteBatch.end must be called before begin.");
        renderCalls = 0;

        if (customShader != null)
            customShader.begin();
        else
            shader.begin();
        setupMatrices();

        drawing = true;
    }


    public void end () {
        if (!drawing) throw new IllegalStateException("GUIBatch.begin must be called before end.");
        if (vertices.length > 0) flush();
        lastTexture = null;
        drawing = false;

        GL20 gl = Gdx.gl;
        if (isBlendingEnabled()) gl.glDisable(GL20.GL_BLEND);

        if (customShader != null)
            customShader.end();
        else
            shader.end();
    }


    public void setColor (Color tint) {
        color = tint.toFloatBits();
        verticesData.C1 = color;
        verticesData.C2 = color;
        verticesData.C3 = color;
        verticesData.C4 = color;

    }


    protected void draw (TextureRegion texture, Transform2D transform) {

        verticesData.C1 = color;
        verticesData.C2 = color;
        verticesData.C3 = color;
        verticesData.C4 = color;

        verticesData.U1 = texture.getU();
        verticesData.V1 = texture.getV();
        // right top
        verticesData.U2 = texture.getU2();
        verticesData.V2 = texture.getV();
        // left bot
        verticesData.U3 = texture.getU();
        verticesData.V3 = texture.getV2();
        // right bot
        verticesData.U4 = texture.getU2();
        verticesData.V4 = texture.getV2();

        resetVertices(transform);
        transformVertices(transform);

        this.verticesData.addVertices(vertices);

    }

    private void transformVertices(Transform2D transform) {
        float x, y, z, w;
        float tx, ty;

        tx = -transform.pivot.x;
        ty = -transform.pivot.y;

        x = (verticesData.X1 + tx) * transform.scaleX;
        y = (verticesData.Y1 + ty) * transform.scaleY;
        z = verticesData.Z1;

        verticesData.X1 = 1 * x + transform.pitch * z - transform.roll * y;
        verticesData.Y1 = 1 * y + transform.roll * x - transform.yaw * z;
        verticesData.Z1 = 1 * z + transform.yaw * y - transform.pitch * x;
        w = -transform.yaw * x - transform.pitch * y - transform.roll * z;

        x = verticesData.X1;
        y = verticesData.Y1;
        z = verticesData.Z1;
        verticesData.X1 = w * -transform.yaw + x * 1 + y * -transform.roll - z * -transform.pitch;
        verticesData.Y1 = w * -transform.pitch + y * 1 + z * -transform.yaw - x * -transform.roll;
        verticesData.Z1 = w * -transform.roll + z * 1 + x * -transform.pitch - y * -transform.yaw;


        verticesData.X1 += transform.x - tx;
        verticesData.Y1 += transform.y - ty;
        verticesData.Z1 += transform.z;

        x = (verticesData.X2 + tx) * transform.scaleX;
        y = (verticesData.Y2 + ty) * transform.scaleY;
        z = verticesData.Z2;

        verticesData.X2 = 1 * x + transform.pitch * z - transform.roll * y;
        verticesData.Y2 = 1 * y + transform.roll * x - transform.yaw * z;
        verticesData.Z2 = 1 * z + transform.yaw * y - transform.pitch * x;
        w = -transform.yaw * x - transform.pitch * y - transform.roll * z;


        x = verticesData.X2;
        y = verticesData.Y2;
        z = verticesData.Z2;
        verticesData.X2 = w * -transform.yaw + x * 1 + y * -transform.roll - z * -transform.pitch;
        verticesData.Y2 = w * -transform.pitch + y * 1 + z * -transform.yaw - x * -transform.roll;
        verticesData.Z2 = w * -transform.roll + z * 1 + x * -transform.pitch - y * -transform.yaw;

        verticesData.X2 += transform.x - tx;
        verticesData.Y2 += transform.y - ty;
        verticesData.Z2 += transform.z;

        x = (verticesData.X3 + tx) * transform.scaleX;
        y = (verticesData.Y3 + ty) * transform.scaleY;
        z = verticesData.Z3;

        verticesData.X3 = 1 * x + transform.pitch * z - transform.roll * y;
        verticesData.Y3 = 1 * y + transform.roll * x - transform.yaw * z;
        verticesData.Z3 = 1 * z + transform.yaw * y - transform.pitch * x;
        w = -transform.yaw * x - transform.pitch * y - transform.roll * z;

        x = verticesData.X3;
        y = verticesData.Y3;
        z = verticesData.Z3;
        verticesData.X3 = w * -transform.yaw + x * 1 + y * -transform.roll - z * -transform.pitch;
        verticesData.Y3 = w * -transform.pitch + y * 1 + z * -transform.yaw - x * -transform.roll;
        verticesData.Z3 = w * -transform.roll + z * 1 + x * -transform.pitch - y * -transform.yaw;


        verticesData.X3 += transform.x - tx;
        verticesData.Y3 += transform.y - ty;
        verticesData.Z3 += transform.z;
        x = (verticesData.X4 + tx) * transform.scaleX;
        y = (verticesData.Y4 + ty) * transform.scaleY;
        z = verticesData.Z4;

        verticesData.X4 = 1 * x + transform.pitch * z - transform.roll * y;
        verticesData.Y4 = 1 * y + transform.roll * x - transform.yaw * z;
        verticesData.Z4 = 1 * z + transform.yaw * y - transform.pitch * x;
        w = -transform.yaw * x - transform.pitch * y - transform.roll * z;


        x = verticesData.X4;
        y = verticesData.Y4;
        z = verticesData.Z4;
        verticesData.X4 = w * -transform.yaw + x * 1 + y * -transform.roll - z * -transform.pitch;
        verticesData.Y4 = w * -transform.pitch + y * 1 + z * -transform.yaw - x * -transform.roll;
        verticesData.Z4 = w * -transform.roll + z * 1 + x * -transform.pitch - y * -transform.yaw;

        verticesData.X4 += transform.x - tx;
        verticesData.Y4 += transform.y - ty;
        verticesData.Z4 += transform.z;
    }


    private void resetVertices (Transform2D transform) {
        float left = -transform.bounds.width / 2f;
        float right = left + transform.bounds.width;
        float top = transform.bounds.height / 2f;
        float bottom = top - transform.bounds.height;

        // left top
        verticesData.X1 = left;
        verticesData.Y1 = top;
        verticesData.Z1 = 0;
        // right top
        verticesData.X2 = right;
        verticesData.Y2 = top;
        verticesData.Z2 = 0;
        // left bot
        verticesData.X3 = left;
        verticesData.Y3 = bottom;
        verticesData.Z3 = 0;
        // right bot
        verticesData.X4 = right;
        verticesData.Y4 = bottom;
        verticesData.Z4 = 0;

    }


    public void flush () {
        if (vertices.length == 0) return;

        renderCalls++;
        totalRenderCalls++;
        int spritesInBatch = vertices.length / 24;
        if (spritesInBatch > maxSpritesInBatch) maxSpritesInBatch = spritesInBatch;
        int count = spritesInBatch * 6;

        lastTexture.bind();
        Mesh mesh = this.mesh;
        mesh.setVertices(vertices, 0, vertices.length);
        mesh.getIndicesBuffer().position(0);
        mesh.getIndicesBuffer().limit(count);

        if (blendingDisabled) {
            Gdx.gl.glDisable(GL20.GL_BLEND);
        } else {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            if (blendSrcFunc != -1) Gdx.gl.glBlendFunc(blendSrcFunc, blendDstFunc);
        }

        mesh.render(customShader != null ? customShader : shader, GL20.GL_TRIANGLES, 0, count);

    }


    public void disableBlending () {
        if (blendingDisabled) return;
        flush();
        blendingDisabled = true;

    }


    public void enableBlending () {
        if (!blendingDisabled) return;
        flush();
        blendingDisabled = false;

    }


    public void setBlendFunction (int srcFunc, int dstFunc) {
        if (blendSrcFunc == srcFunc && blendDstFunc == dstFunc) return;
        flush();
        blendSrcFunc = srcFunc;
        blendDstFunc = dstFunc;

    }


    public void dispose () {
        mesh.dispose();
        if (ownsShader && shader != null) shader.dispose();
    }


    public Matrix4 getProjectionMatrix () {
        return projectionMatrix;

    }


    public Matrix4 getTransformMatrix () {
        return transformMatrix;

    }


    public void setProjectionMatrix (Matrix4 projection) {
        if (drawing) flush();
        projectionMatrix.set(projection);
        if (drawing) setupMatrices();

    }


    public void setTransformMatrix (Matrix4 transform) {
        if (drawing) flush();
        transformMatrix.set(transform);
        if (drawing) setupMatrices();

    }


    private void setupMatrices () {
        combinedMatrix.set(projectionMatrix).mul(transformMatrix);
        if (customShader != null) {
            customShader.setUniformMatrix("u_projTrans", combinedMatrix);
            customShader.setUniformi("u_texture", 0);
        } else {
            shader.setUniformMatrix("u_projTrans", combinedMatrix);
            shader.setUniformi("u_texture", 0);
        }

    }

    protected void switchTexture (Texture texture) {
        flush();
        lastTexture = texture;
        invTexWidth = 1.0f / texture.getWidth();
        invTexHeight = 1.0f / texture.getHeight();

    }


    public void setShader (ShaderProgram shader) {
        if (drawing) {
            flush();
            if (customShader != null)
                customShader.end();
            else
                this.shader.end();
        }
        customShader = shader;
        if (drawing) {
            if (customShader != null)
                customShader.begin();
            else
                this.shader.begin();
            setupMatrices();
        }

    }


    public boolean isBlendingEnabled () {
        return !blendingDisabled;

    }

    public boolean isDrawing () {
        return drawing;

    }


    public class VerticesData {

        public float X1;
        public float Y1;
        public float Z1;
        public float C1;
        public float U1;
        public float V1;
        public float X2;
        public float Y2;
        public float Z2;
        public float C2;
        public float U2;
        public float V2;
        public float X3;
        public float Y3;
        public float Z3;
        public float C3;
        public float U3;
        public float V3;
        public float X4;
        public float Y4;
        public float Z4;
        public float C4;
        public float U4;
        public float V4;

        public void addVertices(float [] vertices) {
            int idx = vertices.length;
            vertices[idx++] = X1;
            vertices[idx++] = Y1;
            vertices[idx++] = Z1;
            vertices[idx++] = C1;
            vertices[idx++] = U1;
            vertices[idx++] = V1;

            vertices[idx++] = X2;
            vertices[idx++] = Y2;
            vertices[idx++] = Z2;
            vertices[idx++] = C2;
            vertices[idx++] = U2;
            vertices[idx++] = V2;

            vertices[idx++] = X3;
            vertices[idx++] = Y3;
            vertices[idx++] = Z3;
            vertices[idx++] = C3;
            vertices[idx++] = U3;
            vertices[idx++] = V3;

            vertices[idx++] = X4;
            vertices[idx++] = Y4;
            vertices[idx++] = Z4;
            vertices[idx++] = C4;
            vertices[idx++] = U4;
            vertices[idx++] = V4;

        }

    }

}
