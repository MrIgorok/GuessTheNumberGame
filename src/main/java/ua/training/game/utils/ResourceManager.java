package ua.training.game.utils;

import java.util.Locale;

/**
 * Managing resources.
 *
 * @version 1.0 04 Apr 2019
 * @author  Igor Klapatnjuk
 */
public interface ResourceManager {

    /**
     * Changes resource locale.
     * @param locale locale tat will be used.
     */
    void changeResource(Locale locale);

    /**
     * Returns the key value.
     * @param key key with value will be returned.
     * @return value.
     */
    String getString(String key);
}
