precision mediump float;        // Set the default precision to medium. We don't need as high of a
                                // precision in the fragment shader.
uniform sampler2D u_Texture;    // The input texture.

varying vec3 v_Position;        // Interpolated position for this fragment.
varying vec2 v_TexCoordinate;   // Interpolated texture coordinate per fragment.
uniform vec4 u_Color;

// The entry point for our fragment shader.
void main() {
    vec4 color;
    if(v_TexCoordinate.y < texture2D(u_Texture, v_TexCoordinate).r){
        color = u_Color;
    }
    gl_FragColor = color;
}