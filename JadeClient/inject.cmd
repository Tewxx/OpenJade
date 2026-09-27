@echo off
setlocal
rem Runs the Attach API injector. Needs a JDK 8: the Attach API lives in <jdk>\lib\tools.jar,
rem which later JDKs removed.
if defined JADE_JAVA_HOME set "JADE_JDK=%JADE_JAVA_HOME%"
if not defined JADE_JDK if defined JAVA_HOME set "JADE_JDK=%JAVA_HOME%"
if not defined JADE_JDK call :findjdk
if not defined JADE_JDK (
  echo No JDK 8 found. Install one and set JADE_JAVA_HOME to it, for example:
  echo   set JADE_JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk8u472-b08
  exit /b 1
)
"%JADE_JDK%\bin\java.exe" -cp "%~dp0dist\jade-injector.jar;%JADE_JDK%\lib\tools.jar" JadeInjector %*
exit /b %errorlevel%

:findjdk
rem Scan the usual vendor folders. lib\tools.jar exists only in JDK 8, so it also serves as the version check.
for %%r in (
  "%ProgramFiles%\Eclipse Adoptium" "%ProgramFiles%\Eclipse Foundation" "%ProgramFiles%\AdoptOpenJDK"
  "%ProgramFiles%\Java" "%ProgramFiles%\Amazon Corretto" "%ProgramFiles%\Zulu" "%ProgramFiles%\Microsoft"
  "%ProgramFiles%\BellSoft" "%ProgramFiles%\Semeru" "%ProgramFiles(x86)%\Java" "%ProgramFiles(x86)%\Zulu"
) do (
  for /d %%d in ("%%~r\*") do (
    if exist "%%~d\bin\java.exe" if exist "%%~d\lib\tools.jar" set "JADE_JDK=%%~d"
  )
)
exit /b 0
