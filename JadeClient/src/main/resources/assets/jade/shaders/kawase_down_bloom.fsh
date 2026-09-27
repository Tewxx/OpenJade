#version 120
uniform sampler2D inTexture;
uniform vec2 offset, halfpixel, iResolution;
vec4 premultipliedSample(vec2 uv) { vec4 c = texture2D(inTexture, uv); return vec4(c.rgb * c.a, c.a); }
void main() {
    vec2 uv = gl_FragCoord.xy / iResolution;
    vec2 diagonal = halfpixel * offset;
    vec2 crossed = vec2(halfpixel.x, -halfpixel.y) * offset;
    vec4 total = premultipliedSample(uv) * 4.0;
    total += premultipliedSample(uv - diagonal) + premultipliedSample(uv + diagonal);
    total += premultipliedSample(uv + crossed) + premultipliedSample(uv - crossed);
    vec4 average = total / 8.0;
    gl_FragColor = vec4(average.rgb / average.a, average.a);
}
