package t_panda.compiler;

import javax.lang.model.SourceVersion;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Set;

/**
 * コンパイラ
 */
public class TPCompiler {
    private final JavaCompiler compiler;

    /**
     * システムコンパイラ({@link ToolProvider#getSystemJavaCompiler()})で初期化
     *
     * @see ToolProvider#getSystemJavaCompiler()
     */
    public TPCompiler() {
        this(ToolProvider.getSystemJavaCompiler());
    }

    /**
     * 指定されたコンパイラで初期化
     *
     * @param compiler コンパイラ
     */
    public TPCompiler(JavaCompiler compiler) {
        this.compiler = compiler;
    }

    /**
     * Returns the source versions of the Java programming language supported by this tool.
     *
     * @return a set of supported source versions
     * @see JavaCompiler#getSourceVersions()
     */
    public Set<SourceVersion> getSourceVersions() {
        return this.compiler.getSourceVersions();
    }

    /**
     * コンパイラのバージョンを取得します。
     *
     * @return コンパイラのバージョン
     */
    public Optional<String> getVersion() {
        return this.getVersion(Charset.defaultCharset());
    }

    /**
     * コンパイラのバージョンを取得します。
     *
     * @param charset デコードに使用されるcharset
     * @return コンパイラのバージョン
     */
    public Optional<String> getVersion(Charset charset) {
        String verStr;
        try (
                ByteArrayOutputStream bArrOutStrm = new ByteArrayOutputStream();
                ByteArrayInputStream bArrInStrm = new ByteArrayInputStream(new byte[]{})
        ) {
            this.compiler.run(bArrInStrm, bArrOutStrm, bArrOutStrm, "-version");
            verStr = bArrOutStrm.toString(charset);
        } catch (Exception e) {
            verStr = null;
        }
        return Optional.ofNullable(verStr);
    }

    /**
     * コンパイルタスクを生成します
     *
     * @return 生成されたコンパイルタスク
     */
    public CompileTask createTask() {
        return new CompileTask(this.compiler);
    }
}
