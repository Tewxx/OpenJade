#version 120
uniform vec2 rectSize;
uniform vec2 texMin;
uniform vec2 texMax;
uniform sampler2D textureIn;
uniform float radius;
uniform float alpha;
float roundedDistance(vec2 p, vec2 extent, float r) {
    return length(max(abs(p) - extent, vec2(0.0))) - r;
}
void main() {
    vec2 normalized = (gl_TexCoord[0].st - texMin) / (texMax - texMin);
    vec2 halfExtent = rectSize * 0.5;
    float d = roundedDistance(halfExtent - normalized * rectSize, halfExtent - radius - 1.0, radius);
    vec4 sampled = texture2D(textureIn, gl_TexCoord[0].st);
    gl_FragColor = vec4(sampled.rgb, sampled.a * (1.0 - smoothstep(0.0, 2.0, d)) * alpha);
}
