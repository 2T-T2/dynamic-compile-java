# dynamic-compile-java
Javaで動的コンパイルを行う。

|  |  |
----|---- 
| モジュール名 | t_panda.compiler |

# 使用方法
モジュールパスに追加して使用

# コンパイル済バイナリ
https://github.com/2T-T2/dynamic-compile-java/releases/download/0.0.0.0/t_panda.compiler.jarhttps://github.com/2T-T2/dynamic-compile-java/releases/download/0.0.0.0/t_panda.compiler.jar

# ビルド方法
1. intellij ideaでビルド
2. build.batでビルド
## 前提条件
* 環境変数 PATH に JAVA_HOME/bin を追加済
   ```bat
   rem ビルドを行う
   build.bat
   rem その他オプションも確認
   build.bat help
   ```

# 使用サンプル
```java
import t_panda.compiler.CompileResult;
import t_panda.compiler.CompileTask;
import t_panda.compiler.TPCompiler;

import javax.tools.ToolProvider;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        TPCompiler compiler = new TPCompiler(ToolProvider.getSystemJavaCompiler());
        CompileTask task = compiler. createTask();
        final String pkgName    = "pkg.dynamic";
        final String className  = "Sample";
        final String fqcnSample = pkgName + "." + className;
        final String srcSample  = String.format(
                """
                package %s;
        
                public class %s implements java.util.Comparator<String> {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                }
                """, pkgName, className)
                ;

        compiler.getVersion().ifPresent(System.out::println);

        CompileResult result = task. addCompileTarget(fqcnSample , srcSample)  // ソース指定
                .addOption("-parameters")                                      // オプション追加
                .addOption("-g:source")
                .run();
        if( !result. isSuccess() ) {
            throw new Exception("コンパイルエラー\n" + result. getOutMessage() + "\n" + result. getErrMessage() );
        }

        Class<Comparator<String>> comparator = result.getCompiledClassAs(fqcnSample);
        List<String> strList = new ArrayList<>() {
            {
                add("bbb");
                add("aaa");
                add("ccc");
            }
        };
        strList.sort(comparator.getConstructor().newInstance());

        System.out.println(strList.toString());     // output [aaa, bbb, ccc]
    }
}
```
