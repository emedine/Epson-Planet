/////////////////////////////////////////////////////////////////
// IMPORTANT: In the Processing PDE this class needs to be stored
// in its own tab and named "MyURL.java"
/////////////////////////////////////////////////////////////////
 
import javax.xml.bind.annotation.*;
 
// this class is a simple pairing of 2 strings:
// a url and its description
public class MyURL {
 
  @XmlAttribute
  String name;
 
  @XmlValue
  String url;
}