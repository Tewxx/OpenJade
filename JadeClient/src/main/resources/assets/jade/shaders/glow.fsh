#version 120
uniform sampler2D textureIn, textureToCheck;
uniform vec2 texelSize, direction;
uniform vec3 color;
uniform bool avoidTexture;
uniform float exposure, radius;
uniform float weights[256];
void main() {
    vec2 uv = gl_TexCoord[0].st;
    if (direction.y == 1.0 && avoidTexture && texture2D(textureToCheck, uv).a != 0.0) discard;
    vec2 stride = direction * texelSize;
    vec4 center = texture2D(textureIn, uv);
    vec4 accumulated = vec4(center.rgb * center.a, center.a) * weights[0];
    for (float distance = 1.0; distance <= radius; distance += 1.0) {
        vec4 positive = texture2D(textureIn, uv + stride * distance);
        vec4 negative = texture2D(textureIn, uv - stride * distance);
        positive.rgb *= positive.a;
        negative.rgb *= negative.a;
        accumulated += (positive + negative) * weights[int(distance)];
    }
    float exposed = 1.0 - exp(-accumulated.a * exposure);
    gl_FragColor = vec4(accumulated.rgb / accumulated.a,
        mix(accumulated.a, exposed, step(0.0, direction.y)));
}
