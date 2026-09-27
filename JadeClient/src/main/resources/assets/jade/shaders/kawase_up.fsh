#version 120
uniform sampler2D inTexture, textureToCheck;
uniform vec2 halfpixel, offset, iResolution;
uniform int check;
void main() {
    vec2 uv = gl_FragCoord.xy / iResolution;
    vec2 h = halfpixel * offset;
    vec4 total = texture2D(inTexture, uv + vec2(-2.0*h.x, 0.0));
    total += 2.0 * texture2D(inTexture, uv + vec2(-h.x, h.y));
    total += texture2D(inTexture, uv + vec2(0.0, 2.0*h.y));
    total += 2.0 * texture2D(inTexture, uv + h);
    total += texture2D(inTexture, uv + vec2(2.0*h.x, 0.0));
    total += 2.0 * texture2D(inTexture, uv + vec2(h.x, -h.y));
    total += texture2D(inTexture, uv + vec2(0.0, -2.0*h.y));
    total += 2.0 * texture2D(inTexture, uv - h);
    vec4 average = total / 12.0;
    float maskAlpha = texture2D(textureToCheck, gl_TexCoord[0].st).a;
    gl_FragColor = vec4(average.rgb, mix(1.0, maskAlpha, check));
}
