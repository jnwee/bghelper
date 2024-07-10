@echo off
IF EXIST .needs-setup (
    echo First time launching application, setting up database...
    call setup-db.bat
    DEL /F /Q .needs-setup
) ELSE (
    echo Database already set up, continuing...
)
docker compose up