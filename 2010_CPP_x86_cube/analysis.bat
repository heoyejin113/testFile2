call "C:\\Program Files (x86)\\Microsoft Visual Studio 10.0\\Common7\\Tools\\vsvars32.bat"
cd %~dp0
call csbuild -- msbuild cube.sln /t:rebuild