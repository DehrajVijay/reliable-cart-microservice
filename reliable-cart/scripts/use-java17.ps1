# Enables Java 17 in THIS PowerShell window only.
# It does not change Windows settings and it does not affect other terminals.
# Usage from the project root:   . .\scripts\use-java17.ps1

$java17Home = "D:\VJ\LEARNING\L\java17"

if (-not (Test-Path "$java17Home\bin\javac.exe")) {
    Write-Host "ERROR: No JDK found at $java17Home" -ForegroundColor Red
    Write-Host "Open scripts\use-java17.ps1 and set java17Home to your real JDK 17 folder." -ForegroundColor Red
    return
}

$env:JAVA_HOME = $java17Home
$env:Path = "$java17Home\bin;$env:Path"

Write-Host "Java 17 is now active in THIS window only." -ForegroundColor Green
java -version