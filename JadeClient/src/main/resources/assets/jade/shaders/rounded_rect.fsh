#version 120
uniform vec2 rectSize;
uniform vec4 color;
uniform float radius;
uniform bool blur;
float edgeDistance(vec2 q, vec2 halfExtent, float rounding) {
    vec2 outside = max(abs(q) - halfExtent, vec2(0.0));
    return length(outside) - rounding;
}
void main() {
    vec2 halfExtent = rectSize * 0.5;
    vec2 fromCenter = halfExtent - gl_TexCoord[0].st * rectSize;
    float distanceToEdge = edgeDistance(fromCenter, halfExtent - radius - 1.0, radius);
    float coverage = (1.0 - smoothstep(0.0, 1.0, distanceToEdge)) * color.a;
    gl_FragColor = vec4(color.rgb, coverage);
}
