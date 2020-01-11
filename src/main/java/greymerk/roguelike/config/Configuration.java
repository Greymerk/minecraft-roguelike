package greymerk.roguelike.config;


/**
 * Stores a configuration as a key/value pair.
 */
public class Configuration {


  /**
   * The key which identifies this configuration.
   */
  public String Key;
  /**
   * The value associated with this configuration.
   */
  public String Value;


  /**
   * Creates a new configuration key/value pair.
   *
   * \param [in] key The key associated with this configuration. \param [in] value The value
   * associated with this configuration.
   */
  public Configuration(String key, String value) {

    Key = key;
    Value = value;

  }


}