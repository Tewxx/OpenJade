#version 120
uniform sampler2D inTexture, textureToCheck;
uniform vec2 halfpixel, offset, iResolution;
uniform int check;
vec4 premultipliedSample(vec2 uv) { vec4 c = texture2D(inTexture, uv); return vec4(c.rgb * c.a, c.a); }
void main() {
    vec2 uv = gl_FragCoord.xy / iResolution;
    vec2 h = halfpixel * offset;
    vec4 total = premultipliedSample(uv + vec2(-h.x, 0.0));
    total += 2.0 * premultipliedSample(uv + vec2(-h.x, h.y));
    total += premultipliedSample(uv + vec2(0.0, 2.0*h.y));
    total += 2.0 * premultipliedSample(uv + h);
    total += premultipliedSample(uv + vec2(2.0*h.x, 0.0));
    total += 2.0 * premultipliedSample(uv + vec2(h.x, -h.y));
    total += premultipliedSample(uv + vec2(0.0, -2.0*h.y));
    total += 2.0 * premultipliedSample(uv - h);
    vec4 average = total / 12.0;
    float alpha = average.a;
    if (check == 1) alpha *= 1.0 - texture2D(textureToCheck, gl_TexCoord[0].st).a;
    gl_FragColor = vec4(average.rgb / max(average.a, 0.001), alpha);
}
