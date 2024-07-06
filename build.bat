setlocal enabledelayedexpansion

@echo off

if "%~1"=="" (
echo.
    call :start
echo.
    call :clean
echo.
    call :compile
echo.
    call :archive
echo.
    call :javadoc
echo.
    goto :end
echo.
)

if %1==help (
    echo.
    call :start

    echo build clean
    echo     %out_dir%, %dst_dir%, %doc_dir% ���N���[�����܂�
    echo.
    echo build compile
    echo     %src_dir% �̓��e���R���p�C�����܂�
    echo     �o�͐�t�H���_ %out_dir%
    echo.
    echo build archive
    echo     %out_dir%, %src_dir%, %res_dir% ���A�[�J�C�u������jar���쐬���܂�
    echo     �o�͐�t�@�C���� %dst_dir%%jar_name%
    echo.
    echo build javadoc
    echo     �h�L�������g�𐶐����܂�
    echo     �o�͐�t�H���_ %doc_dir%
    echo.

    goto :end
    echo.
)

call :start
call :%1
goto :end

:start
    set error_msg=0
    set out_dir=.\out\release\
    set dst_dir=.\dst\
    set doc_dir=.\javadoc\
    set src_dir=.\src\
    set res_dir=.\res\
    set jar_name=t_panda.compiler.jar
    set mdl_name=t_panda.compiler
exit /b

:clean
    echo =============== �N���[���J�n ===============
    del /s /q %out_dir%
    del /s /q %dst_dir%
    del /s /q %doc_dir%
    rmdir /s /q %out_dir%
    rmdir /s /q %dst_dir%
    rmdir /s /q %doc_dir%
    mkdir %out_dir%
    mkdir %dst_dir%
    mkdir %doc_dir%
    echo =============== �N���[���I�� ===============
exit /b

:compile
    echo =============== �R���p�C���J�n ===============
    set javacCmd=javac^
        -d %out_dir%^
        -encoding utf8^
        -parameters^
        --module-source-path %src_dir%^
        --module %mdl_name%
    echo %javacCmd%
    %javacCmd%
    if %errorlevel% neq 0 (
        set error_msg=�R���p�C���G���[
        goto :echo_error
    )
    echo =============== �R���p�C���I�� ===============
exit /b

:archive
    echo =============== �A�[�J�C�u���J�n ===============
    set jarCmd=jar^
        -cf %dst_dir%%jar_name%^
        -C %out_dir%%mdl_name% .^
        -C %src_dir%%mdl_name% .^
        %res_dir%
    echo %jarCmd%
    %jarCmd%
    if %errorlevel% neq 0 (
        set error_msg=�A�[�J�C�u���G���[
        goto :echo_error
    )
    echo =============== �A�[�J�C�u���J�n ===============
exit /b

:javadoc
    echo =============== �h�L�������g�����J�n ===============
    set javadocCmd=javadoc ^
        --allow-script-in-comments ^
        -d %doc_dir% ^
        -encoding utf8^
        --module-source-path %src_dir%^
        --module %mdl_name%^
        -subpackages t_panda.compiler ^
        -exclude t_panda.compiler.internal ^
        -exclude t_panda.compiler.internal.exception ^
        -exclude t_panda.compiler.internal.util ^
        -exclude t_panda.compiler.internal.resource ^
        -header "<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.15.10/styles/vs.min.css'><script src='https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.15.10/highlight.min.js'></script><script>hljs.initHighlightingOnLoad();</script>"
    echo %javadocCmd%
    %javadocCmd%
    if %errorlevel% neq 0 (
        set error_msg=�h�L�������g�����G���[
        goto :echo_error
    )
    echo =============== �h�L�������g�����J�n ===============
exit /b

:echo_error
    echo %error_msg%
goto :end

:end
    ENDLOCAL