[CmdletBinding()]
param([string]$JavaHome = $env:JADE_JAVA_HOME)
$ErrorActionPreference = 'Stop'
if ($JavaHome) { $env:JADE_JAVA_HOME = $JavaHome }
# the JDK is discovered by tools/compile_source.py (JADE_JAVA_HOME, then JAVA_HOME, then the usual install folders)
$jdk = & python -c "import sys; sys.path.insert(0, r'$PSScriptRoot\tools'); import compile_source as c; print(c.JDK)"
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
Write-Host "JDK: $jdk"
& python (Join-Path $PSScriptRoot 'tools\prepare_dependencies.py')
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
& python (Join-Path $PSScriptRoot 'tools\build_source.py')
exit $LASTEXITCODE
