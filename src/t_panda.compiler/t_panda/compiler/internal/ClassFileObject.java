package t_panda.compiler.internal;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Optional;

public class ClassFileObject extends SimpleJavaFileObject {
    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();
    private Class<?> definedClass;

    public ClassFileObject(String name, Kind kind) {
        super(URI.create("string:///" + name.replace('.', '/') + kind.extension), kind);
    }

    @Override
    public OutputStream openOutputStream() {
        return bos;
    }

    public byte[] getCompiledBytes() {
        return bos.toByteArray();
    }

    public Optional<Class<?>> getDefinedClass() {
        return Optional.ofNullable(definedClass);
    }

    public void setDefinedClass(Class<?> definedClass) {
        this.definedClass = definedClass;
    }

}
