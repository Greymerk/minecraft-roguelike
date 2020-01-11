package greymerk.roguelike.config;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;


/**
 * Allows configurations to be written to config files.
 */
public class ConfigFileWriter {


  /**
   * Writes a particular set of configurations to a particular file according to a particular
   * representation.
   *
   * \param [in] filename The name of the file to which configuration data shall be written. \param
   * [in] config The configurations which shall be written to the given file. \param [in] writer A
   * ConfigurationParser which implements the desired strategy for representing configuration data
   * in the file.
   */
  public static void Write(String filename, ConfigurationProvider config, ConfigurationParser writer) throws Exception {

    //	Open file for writing
    FileOutputStream stream = new FileOutputStream(filename, true);

    //	Truncate file in case it already
    //	existed
    stream.getChannel().truncate(0);

    //	Create a writer
    BufferedWriter buffered = new BufferedWriter(
        new OutputStreamWriter(stream)
    );


    //	Write all configurations
    for (Configuration c : config) {
      writer.Write(buffered, c);
    }

    //	Close the stream and flush out
    //	all written configurations
    buffered.close();

  }


}