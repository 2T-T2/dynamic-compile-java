package t_panda.compiler;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * コンパイル結果
 */
public class CompileResult {
    private final ByteArrayOutputStream out;
    private final ByteArrayOutputStream err;
    private boolean success;
    private ClassLoader clsLoader;

    CompileResult() {
        this.out = new ByteArrayOutputStream();
        this.err = new ByteArrayOutputStream();
    }

    OutputStream getErr() {
        return this.err;
    }

    OutputStream getOut() {
        return this.out;
    }

    /**
     * コンパイルが成功したかどうかを取得します。
     *
     * @return コンパイルが成功したかどうか
     */
    public boolean isSuccess() {
        return this.success;
    }

    void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * コンパイル実行結果メッセージを取得します。
     *
     * @return コンパイル実行結果メッセージを
     */
    public String getOutMessage() {
        return this.getOutMessage(Charset.defaultCharset());
    }

    /**
     * コンパイル実行結果メッセージを取得します。
     *
     * @param charset デコードに使用されるcharset
     * @return コンパイル実行結果メッセージを
     */
    public String getOutMessage(Charset charset) {
        return this.out.toString(charset);
    }

    /**
     * コンパイル実行結果エラーメッセージを取得します。
     *
     * @return コンパイル実行結果エラーメッセージを
     */
    public String getErrMessage() {
        return this.getErrMessage(Charset.defaultCharset());
    }

    /**
     * コンパイル実行結果エラーメッセージを取得します。
     *
     * @param charset デコードに使用されるcharset
     * @return コンパイル実行結果エラーメッセージを
     */
    public String getErrMessage(Charset charset) {
        return this.err.toString(charset);
    }

    /**
     * コンパイル結果のクラスローダーを取得します。
     *
     * @return コンパイル結果のクラスローダー
     */
    public ClassLoader getClassLoader() {
        return this.clsLoader;
    }

    void setClassLoader(ClassLoader clsLoader) {
        this.clsLoader = clsLoader;
    }

    /**
     * コンパイル結果のクラスローダーからクラス名(fqcn)を検索し指定された型のクラスにキャストしてを取得します。
     *
     * @param <T>  キャスト先
     * @param fqcn パッケージ名を付けて指定したクラス名の書き方のことを、完全修飾クラス名（Fully Qualified Class Name：FQCN）あるいは完全限定名（fully qualified name：FQN）
     * @return コンパイル結果のクラスローダーから検索されたクラス
     * @throws ClassNotFoundException 指定されたfqcnが存在しなかった場合
     */
    @SuppressWarnings("unchecked")
    public <T> Class<T> getCompiledClassAs(String fqcn) throws ClassNotFoundException {
        return (Class<T>) this.getCompiledClass(fqcn);
    }

    /**
     * コンパイル結果のクラスローダーからクラス名(fqcn)を検索してを取得します。
     *
     * @param fqcn パッケージ名を付けて指定したクラス名の書き方のことを、完全修飾クラス名（Fully Qualified Class Name：FQCN）あるいは完全限定名（fully qualified name：FQN）
     * @return コンパイル結果のクラスローダーから検索されたクラス
     * @throws ClassNotFoundException 指定されたfqcnが存在しなかった場合
     */
    public Class<?> getCompiledClass(String fqcn) throws ClassNotFoundException {
        return this.clsLoader.loadClass(fqcn);
    }
}
