package greymerk.roguelike.config;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


/**
 * Provides configuration information from a file.
 */
public class ConfigFile extends ConfigurationProvider {


  private String filename;
  private ConfigurationParser parser;


  /**
   * Creates a configuration file by parsing a certain file.
   *
   * \param [in] filename The name of the file to read. \param [in] parser A ConfigurationParser
   * which will be used to parse the data in the given file.
   */
  public ConfigFile(String filename, ConfigurationParser parser) throws Exception {

    this.filename = filename;
    this.parser = parser;

    //	Open a stream to the file-in-question
    FileInputStream stream;
    try {

      stream = new FileInputStream(filename);

    } catch (Exception e) {

      //	If the file could not be opened,
      //	we just create an empty set of
      //	configurations
      return;

    }

    //	Create an input stream reader
    //	to read from the specified file
    //	stream
    BufferedReader reader = new BufferedReader(
        new InputStreamReader(stream)
    );

    //	Loop until all settings have
    //	been extracted
    for (; ; ) {

      //	Attempt to extract setting
      Configuration config = parser.Parse(reader);

      //	If setting could not be extracted,
      //	the file is done, break
      if (config == null) {
        break;
      }

      //	We extracted a setting, insert it
      //	into the hash map
      kvp.put(
          config.Key,
          config.Value
      );

    }

  }


  /**
   * Writes configurations back to the file from which they were originally drawn, according to the
   * strategy used to parse them.
   */
  public void Write() throws Exception {

    ConfigFileWriter.Write(
        filename,
        this,
        parser
    );

  }


}