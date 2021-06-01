echo curl
for /l %%i in (1, 1, 500) do (
echo %%i/500
echo %DATE% %TIME%
gradle build static -Dkey=GRADLE -Dserver=211.116.223.195
)