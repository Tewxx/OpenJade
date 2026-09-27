$ErrorActionPreference = 'Stop'
Add-Type @'
using System;
using System.Text;
using System.Collections.Generic;
using System.Runtime.InteropServices;
public class JadeWindows {
    public delegate bool Callback(IntPtr window, IntPtr parameter);
    [DllImport("user32.dll")] static extern bool EnumWindows(Callback callback, IntPtr parameter);
    [DllImport("user32.dll", CharSet = CharSet.Unicode)] static extern int GetClassName(IntPtr window, StringBuilder text, int length);
    [DllImport("user32.dll", CharSet = CharSet.Unicode)] static extern int GetWindowText(IntPtr window, StringBuilder text, int length);
    [DllImport("user32.dll")] static extern uint GetWindowThreadProcessId(IntPtr window, out uint pid);
    public uint Pid;
    public string ClassName;
    public static JadeWindows[] Find() {
        var results = new List<JadeWindows>();
        EnumWindows((window, parameter) => {
            var name = new StringBuilder(256);
            var title = new StringBuilder(512);
            GetClassName(window, name, name.Capacity);
            GetWindowText(window, title, title.Capacity);
            uint pid;
            GetWindowThreadProcessId(window, out pid);
            if (name.ToString() == "LWJGL" || name.ToString() == "GLFW30"
                || title.ToString().IndexOf("minecraft", StringComparison.OrdinalIgnoreCase) >= 0)
                results.Add(new JadeWindows { Pid = pid, ClassName = name.ToString() });
            return true;
        }, IntPtr.Zero);
        return results.ToArray();
    }
}
'@
$seen = @{}
foreach ($window in [JadeWindows]::Find()) {
    if ($seen.ContainsKey($window.Pid)) { continue }
    try {
        $game = Get-Process -Id $window.Pid -ErrorAction Stop
        $modules = @($game.Modules | ForEach-Object { $_.ModuleName.ToLowerInvariant() })
        if ($modules -notcontains 'jvm.dll') { continue }
        $seen[$window.Pid] = $true
        if ($window.ClassName -eq 'GLFW30' -or $modules -contains 'glfw.dll') {
            Write-Output "INFO Modern LWJGL game detected (PID $($window.Pid)); Jade requires Minecraft 1.8.9."
        } elseif ($modules -contains 'lwjgl64.dll' -or $modules -contains 'lwjgl.dll') {
            Write-Output "PID $($window.Pid)"
        } else {
            Write-Output "INFO JVM game window detected (PID $($window.Pid)); wait until the game reaches its title screen."
        }
    } catch {
        Write-Output "INFO Could not inspect game window PID $($window.Pid). Run the loader under the same account and permissions as the game."
    }
}
