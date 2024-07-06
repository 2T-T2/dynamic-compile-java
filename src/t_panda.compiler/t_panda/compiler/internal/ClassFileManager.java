package t_panda.compiler.internal;

import javax.tools.DiagnosticListener;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import java.security.SecureClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ClassFileManager extends ForwardingJavaFileManager<JavaFileManager> {
    private final Map<String, ClassFileObject> map;

    public ClassFileManager(JavaCompiler compiler, DiagnosticListener<? super JavaFileObject> listener) {
        super(compiler.getStandardFileManager(listener, null, null));
        this.map = new HashMap<>();
    }

    @Override
    public ClassLoader getClassLoader(Location location) {
        return new SecureClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                final ClassFileObject co = map.get(name);
                if (co == null) {
                    return super.findClass(name);
                }

                return co.getDefinedClass().orElseGet(() -> {
                    byte[] b = co.getCompiledBytes();
                    Class<?> cls = super.defineClass(name, b, 0, b.length);
                    co.setDefinedClass(cls);
                    return cls;
                });

            }
        };
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling) {
        ClassFileObject co = new ClassFileObject(className, kind);
        map.put(className, co);
        return co;
    }
}
