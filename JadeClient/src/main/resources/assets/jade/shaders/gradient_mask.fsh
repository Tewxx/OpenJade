#version 120
uniform vec2 location;
uniform vec2 rectSize;
uniform sampler2D tex;
uniform vec3 color1, color2, color3, color4;
uniform float alpha;
float noise(vec2 p) { return mix(0.5/255.0, -0.5/255.0, fract(sin(dot(p, vec2(12.9898,78.233))) * 43758.5453)); }
void main() {
    vec2 uv = (gl_FragCoord.xy - location) / rectSize;
    vec3 shade = mix(mix(color1, color2, uv.y), mix(color3, color4, uv.y), uv.x) + noise(uv);
    gl_FragColor = vec4(shade, texture2D(tex, gl_TexCoord[0].st).a * alpha);
}
