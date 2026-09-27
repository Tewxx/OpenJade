#version 120
uniform sampler2D u_texture;
uniform sampler2D u_texture2;
void main() {
    vec2 uv = gl_TexCoord[0].st;
    gl_FragColor = vec4(texture2D(u_texture2, uv).rgb, texture2D(u_texture, uv).a);
}
