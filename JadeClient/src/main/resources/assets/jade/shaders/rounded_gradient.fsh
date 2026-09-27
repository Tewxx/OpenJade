#version 120
uniform vec2 rectSize;
uniform vec4 color1, color2, color3, color4;
uniform float radius;
float roundedDistance(vec2 p, vec2 extent, float r) { return length(max(abs(p) - extent, vec2(0.0))) - r; }
float noise(vec2 p) { return mix(0.5/255.0, -0.5/255.0, fract(sin(dot(p, vec2(12.9898,78.233))) * 43758.5453)); }
void main() {
    vec2 uv = gl_TexCoord[0].st;
    vec2 halfExtent = rectSize * 0.5;
    float coverage = 1.0 - smoothstep(0.0, 2.0, roundedDistance(halfExtent - uv * rectSize, halfExtent - radius - 1.0, radius));
    vec4 shade = mix(mix(color1, color2, uv.y), mix(color3, color4, uv.y), uv.x) + noise(uv);
    gl_FragColor = vec4(shade.rgb, shade.a * coverage);
}
