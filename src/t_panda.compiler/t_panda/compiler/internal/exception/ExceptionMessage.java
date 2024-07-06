package t_panda.compiler.internal.exception;

import t_panda.compiler.internal.resource.Resource;
import t_panda.compiler.internal.util.Messenger;
import t_panda.compiler.internal.util.Switch;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Properties;

public enum ExceptionMessage implements Messenger {
    ENCODING_ERROR,
    NOT_FOUND_CLASS_IN_SOURCE_ERROR,
    NOT_SUPPORTED_OPTION_ERROR,
    OPTION_NECESSARY_ARGUMENT_NOTHING_ERROR,
    OPTION_UNNECESSARY_ARGUMENT_ASSIGNMENT_ERROR;

    private final static Properties properties = new Properties();

    static {
        try (var reader = new InputStreamReader(Resource.EXCEPTION_MESSAGE_PROPERTIES.readAsStream(), StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getMessage(Object... args) {
        return Switch.<String, String>of(Locale.getDefault().getLanguage())
                .then(Locale.JAPANESE.getLanguage(), () -> String.format(properties.getProperty(this.name() + "_ﾆﾎﾟﾝｺﾞ"), args))
                .then(Locale.ENGLISH.getLanguage(), () -> String.format(properties.getProperty(this.name() + "_english"), args))
                .then(Locale.FRENCH.getLanguage(), () -> String.format(properties.getProperty(this.name() + "_français"), args))
                .then(Locale.CHINESE.getLanguage(), () -> String.format(properties.getProperty(this.name() + "_中文"), args))
                .then(Locale.SIMPLIFIED_CHINESE.getLanguage(), () -> String.format(properties.getProperty(this.name() + "_中文"), args))
                .then(Locale.TRADITIONAL_CHINESE.getLanguage(), () -> String.format(properties.getProperty(this.name() + "_中文"), args))
                .then(Locale.GERMAN.getLanguage(), () -> String.format(properties.getProperty(this.name() + "_Deutsch"), args))
                .then(Locale.ITALIAN.getLanguage(), () -> String.format(properties.getProperty(this.name() + "_Italiano"), args))
                .then(Locale.KOREAN.getLanguage(), () -> String.format(properties.getProperty(this.name() + "_한국어"), args))
                .defaultValue(() -> String.format(properties.getProperty(this.name() + "_ﾆﾎﾟﾝｺﾞ"), args))
                .end()
                ;
    }
}
