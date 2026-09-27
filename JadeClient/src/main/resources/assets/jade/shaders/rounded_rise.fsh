#version 120
uniform vec2 u_size;
uniform float u_radius;
uniform vec4 u_color;
uniform vec4 u_edges;
uniform vec4 u_corner_radii;
uniform vec2 u_connected_edges;
void main() {
    vec2 uv = gl_TexCoord[0].st;
    bool squareCorner = (uv.x < .5 && uv.y > .5 && u_edges.x == 0.0)
        || (uv.x > .5 && uv.y > .5 && u_edges.y == 0.0)
        || (uv.x > .5 && uv.y < .5 && u_edges.z == 0.0)
        || (uv.x < .5 && uv.y < .5 && u_edges.w == 0.0);
    if (squareCorner) { gl_FragColor = u_color; return; }
    float upperRadius = uv.x < .5 ? u_corner_radii.x : u_corner_radii.y;
    float lowerRadius = uv.x < .5 ? u_corner_radii.w : u_corner_radii.z;
    float cornerRadius = uv.y > .5 ? upperRadius : lowerRadius;
    vec2 distance = (abs(uv - .5) + .5) * u_size - u_size + cornerRadius;
    distance.y -= uv.y < .5 ? u_connected_edges.x : u_connected_edges.y;
    float coverage = smoothstep(1.0, 0.0, length(max(distance, vec2(0.0))) - cornerRadius + .5);
    gl_FragColor = vec4(u_color.rgb, u_color.a * coverage);
}
