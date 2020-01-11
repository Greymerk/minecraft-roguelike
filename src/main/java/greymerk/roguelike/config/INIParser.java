package greymerk.roguelike.config;


import java.io.BufferedReader;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Parses configuration files using syntax usually associated with INI files.
 *
 * That is, key/value pairs are separated by an equals sign, on their own line, empty lines and
 * lines that begin with a number sign are disregarded.
 */
public class INIParser implements ConfigurationParser {


  private static final String comment_regex = "^#";
  private static final String line_regex = "^([^=]+)=(.*)$";


  private Pattern line_pattern;
  private Pattern comment_pattern;


  /**
   * Creates a new INIParser.
   */
  public INIParser() {

    line_pattern = Pattern.compile(line_regex);
    comment_pattern = Pattern.compile(comment_regex);

  }


  public Configuration Parse(BufferedReader reader) throws Exception {

    //	Loop until we end or extract a line
    for (; ; ) {

      //	Get the next line
      String line = reader.readLine();

      //	If there's nothing we fail out
      //	at once
      if (line == null) {
        return null;
      }

      //	Strip leading/trailing whitespace
      //	from the line
      line = line.trim();

      if (
        //	If this line is empty or...
          (line.length() == 0) ||
              //	...if this line is a comment, skip
              comment_pattern.matcher(line).find()
      ) {
        continue;
      }

      //	Attempt to extract configuration
      //	information from this line
      Matcher matcher = line_pattern.matcher(line);

      //	Skip this line if it doesn't match
      //	the pattern
      if (!matcher.find()) {
        continue;
      }

      //	Extract the key
      String key = matcher.group(1).trim();
      //	If the key is empty, skip
      if (key.length() == 0) {
        continue;
      }
      //	Extract the value
      String value = matcher.group(2).trim();
      //	If the value is empty, skip
      if (value.length() == 0) {
        continue;
      }

      return new Configuration(key, value);

    }

  }


  public void Write(Writer writer, Configuration config) throws Exception {

    writer.write(config.Key);
    writer.write("=");
    writer.write(config.Value);
    writer.write(System.getProperty("line.separator"));

  }


}
