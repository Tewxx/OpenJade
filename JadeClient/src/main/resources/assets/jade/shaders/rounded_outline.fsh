#version 120
uniform vec2 location;
uniform vec2 rectSize;
uniform vec4 color;
uniform vec4 outlineColor;
uniform float radius;
uniform float outlineThickness;
float signedRoundedDistance(vec2 p, vec2 extent, float r) {
    return length(max(abs(p) - extent + r, vec2(0.0))) - r;
}
void main() {
    vec2 center = location + rectSize * 0.5;
    vec2 extent = rectSize * 0.5 + outlineThickness * 0.5 - 1.0;
    float d = signedRoundedDistance(gl_FragCoord.xy - center, extent, radius);
    float edgeMix = smoothstep(0.0, 2.0, abs(d) - outlineThickness * 0.5);
    vec4 interior = d < 0.0 ? color : vec4(outlineColor.rgb, 0.0);
    gl_FragColor = mix(outlineColor, interior, edgeMix);
}
