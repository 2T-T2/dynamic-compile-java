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
    echo     %out_dir%, %dst_dir%, %doc_dir% をクリーンします
    echo.
    echo build compile
    echo     %src_dir% の内容をコンパイルします
    echo     出力先フォルダ %out_dir%
    echo.
    echo build archive
    echo     %out_dir%, %src_dir%, %res_dir% をアーカイブ化してjarを作成します
    echo     出力先ファイル名 %dst_dir%%jar_name%
    echo.
    echo build javadoc
    echo     ドキュメントを生成します
    echo     出力先フォルダ %doc_dir%
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
    echo =============== クリーン開始 ===============
    del /s /q %out_dir%
    del /s /q %dst_dir%
    del /s /q %doc_dir%
    rmdir /s /q %out_dir%
    rmdir /s /q %dst_dir%
    rmdir /s /q %doc_dir%
    mkdir %out_dir%
    mkdir %dst_dir%
    mkdir %doc_dir%
    echo =============== クリーン終了 ===============
exit /b

:compile
    echo =============== コンパイル開始 ===============
    set javacCmd=javac^
        -d %out_dir%^
        -encoding utf8^
        -parameters^
        --module-source-path %src_dir%^
        --module %mdl_name%
    echo %javacCmd%
    %javacCmd%
    if %errorlevel% neq 0 (
        set error_msg=コンパイルエラー
        goto :echo_error
    )
    echo =============== コンパイル終了 ===============
exit /b

:archive
    echo =============== アーカイブ化開始 ===============
    set jarCmd=jar^
        -cf %dst_dir%%jar_name%^
        -C %out_dir%%mdl_name% .^
        -C %src_dir%%mdl_name% .^
        %res_dir%
    echo %jarCmd%
    %jarCmd%
    if %errorlevel% neq 0 (
        set error_msg=アーカイブ化エラー
        goto :echo_error
    )
    echo =============== アーカイブ化開始 ===============
exit /b

:javadoc
    echo =============== ドキュメント生成開始 ===============
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
        set error_msg=ドキュメント生成エラー
        goto :echo_error
    )
    echo =============== ドキュメント生成開始 ===============
exit /b

:echo_error
    echo %error_msg%
goto :end

:end
    ENDLOCAL