package t_panda.compiler.internal;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

public class SourceFileObject extends SimpleJavaFileObject {
    private final String code;

    public SourceFileObject(String fqcn, String code) {
        super(URI.create("string:///" + fqcn.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        // エンコーディングエラーは発生しないはず
        return this.code;
    }
}
