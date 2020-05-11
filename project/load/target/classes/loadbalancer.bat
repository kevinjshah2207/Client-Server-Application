@echo off
cls
chcp 65001

javac com/manage/app/App.java

echo Compiled Successfully.
cmd /c START java com/manage/app/App


echo.
pause
