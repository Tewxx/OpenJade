#version 120
uniform sampler2D inTexture, textureToCheck;
uniform vec2 halfpixel, offset, iResolution;
uniform bool check;
uniform float lastPass, exposure;
vec4 premultipliedSample(vec2 uv) { vec4 c = texture2D(inTexture, uv); return vec4(c.rgb * c.a, c.a); }
void main() {
    if (check && texture2D(textureToCheck, gl_TexCoord[0].st).a != 0.0) discard;
    vec2 uv = gl_FragCoord.xy / iResolution;
    vec2 h = halfpixel * offset;
    vec4 total = premultipliedSample(uv + vec2(-2.0*h.x, 0.0));
    total += 2.0 * premultipliedSample(uv + vec2(-h.x, h.y));
    total += premultipliedSample(uv + vec2(0.0, 2.0*h.y));
    total += 2.0 * premultipliedSample(uv + h);
    total += premultipliedSample(uv + vec2(2.0*h.x, 0.0));
    total += 2.0 * premultipliedSample(uv + vec2(h.x, -h.y));
    total += premultipliedSample(uv + vec2(0.0, -2.0*h.y));
    total += 2.0 * premultipliedSample(uv - h);
    vec4 average = total / 12.0;
    float exposed = 1.0 - exp(-average.a * exposure);
    gl_FragColor = vec4(average.rgb / average.a, mix(average.a, exposed, step(0.0, lastPass)));
}
