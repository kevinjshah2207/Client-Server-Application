@echo off
cls
chcp 65001

javac app/*.java
echo Compiled Successfully.

cmd /c START java loadbalancer/Server 6001
cmd /c START java loadbalancer/Server 6002
cmd /c START java loadbalancer/Server 6003



cmd /c START java loadbalancer/Loadbalancer
cmd /c START java loadbalancer/Client
cmd /c START java loadbalancer/Client
cmd /c START java loadbalancer/Client
cmd /c START java loadbalancer/Client
cmd /c START java loadbalancer/Client
cmd /c START java loadbalancer/Client


echo.
pause