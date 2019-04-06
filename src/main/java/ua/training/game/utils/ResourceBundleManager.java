package ua.training.game.utils;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Managing resources using resource bundle files.
 *
 * @version 1.0 04 Apr 2019
 * @author  Igor Klapatnjuk
 */
public enum ResourceBundleManager implements ResourceManager {
    /**
     * Single instance that application uses for managing resources.
     */
    INSTANCE;

    /**
     * Provides resources.
     */
    private ResourceBundle resourceBundle;

    /**
     * The file that contains dialog values.
     */
    private final String resourceName = "dialogs";

    /**
     * Creates ResourceBundleManager that will be used.
     * PropertyResourceBundle with default Locale.
     */
    ResourceBundleManager() {
        resourceBundle = PropertyResourceBundle.getBundle(resourceName);
    }

    /**
     * Change resource locale.
     * @param locale locale that will be used.
     */
    public void changeResource(final Locale locale) {
        resourceBundle = PropertyResourceBundle.getBundle(resourceName, locale);
    }

    /**
     * Returns the key value.
     * @param key key withc value will ber returned.
     * @return value.
     */
    public String getString(final String key) {
        return resourceBundle.getString(key);
    }
}
