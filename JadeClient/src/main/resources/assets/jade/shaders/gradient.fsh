#version 120
uniform vec2 location;
uniform vec2 rectSize;
uniform vec4 color1, color2, color3, color4;
float dither(vec2 p) { return mix(0.5/255.0, -0.5/255.0, fract(sin(dot(p, vec2(12.9898, 78.233))) * 43758.5453)); }
void main() {
    vec2 uv = (gl_FragCoord.xy - location) / rectSize;
    vec4 verticalLeft = mix(color1, color2, uv.y);
    vec4 verticalRight = mix(color3, color4, uv.y);
    gl_FragColor = mix(verticalLeft, verticalRight, uv.x) + dither(uv);
}
