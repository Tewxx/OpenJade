#version 120
uniform sampler2D inTexture;
uniform vec2 offset, halfpixel, iResolution;
void main() {
    vec2 uv = gl_FragCoord.xy / iResolution;
    vec2 diagonal = halfpixel * offset;
    vec2 crossed = vec2(halfpixel.x, -halfpixel.y) * offset;
    vec4 samples = texture2D(inTexture, uv) * 4.0;
    samples += texture2D(inTexture, uv - diagonal);
    samples += texture2D(inTexture, uv + diagonal);
    samples += texture2D(inTexture, uv + crossed);
    samples += texture2D(inTexture, uv - crossed);
    gl_FragColor = vec4(samples.rgb / 8.0, 1.0);
}
