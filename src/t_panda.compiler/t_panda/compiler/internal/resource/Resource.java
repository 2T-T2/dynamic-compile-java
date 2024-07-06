package t_panda.compiler.internal.resource;

import t_panda.compiler.TPCompiler;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public enum Resource {
    EXCEPTION_MESSAGE_PROPERTIES("exceptionMessages.properties");

    private final URL resourceUrl;

    Resource(String fileName) {
        try {
            this.resourceUrl = new URL(
                    TPCompiler.class.getClassLoader().getResource("res/"),
                    fileName
            );
        } catch (MalformedURLException e) {
            throw new RuntimeException("リソース読込失敗", e);
        }
    }

    public InputStream readAsStream() throws IOException {
        return resourceUrl.openStream();
    }
}
