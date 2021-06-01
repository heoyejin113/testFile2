echo curl
for /l %%i in (1, 1, 500) do (
echo %%i/500
echo %DATE% %TIME%
mvn clean compile && mvn com.codescroll.acp:static-maven-plugin:static -Dkey=YSJAVA -Dserver=211.116.222.189
)