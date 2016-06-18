import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.ArrayList.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class funLibraryMaker extends PApplet {



/*
 * Author : Yasunori SANNOMIYA
 * Created: 2015.05.28
 */

final String FILE_NAME = "../201606/A5.csv";
ArrayList<Book> books;

public void setup(){
  books = new ArrayList<Book>();
  noLoop();
}

public void draw(){
  readFile( FILE_NAME );

  ArrayList<String> output = new ArrayList<String>();
  for( Book b: books ){
    output.add( "<tr>" );
    output.add( "    <td>" );
    output.add( "        <div>" );
    output.add( "            <a href=\"http://lib-auth.fun.ac.jp/webopac/" + b.getID() + "\" style=\"text-decoration: none;\">" );
    output.add( "                " + b.getName() );
    output.add( "            </a>" );
    output.add( "        </div>" );
    output.add( "    </td>" );
    output.add( "    <td>" );
    output.add( "        <div>" );
    output.add( "                " + b.getAuthor() );
    output.add( "        </div>" );
    output.add( "    </td>" );
    output.add( "    <td>" );
    output.add( "        <div>" );
    output.add( "                " + b.getPublish() );
    output.add( "        </div>" );
    output.add( "    </td>" );
    output.add( "</tr>" );
  }

  saveStrings("A5.html", (String[])output.toArray(new String[0]));
  exit();
}

public void readFile( String filePath ){
  String[] lines = loadStrings( filePath );
  for( int i=0; i<lines.length; i++ ){
    String[] tmp = split( lines[i], "," );
    books.add( new Book(tmp[0], tmp[1], tmp[2], tmp[3]) );
  }
}

class Book{
  private String name;
  private String author;
  private String publish;
  private String id;

  public Book( String name, String author, String publish, String id ){
    // ; -> ","
    this.name = name.replaceAll(";", ",");
    this.author = author.replaceAll(";", ",");
    this.publish = publish.replaceAll(";", ",");
    this.id = id;
  }

  public String getName(){ return name; }
  public String getAuthor(){ return author; }
  public String getPublish(){ return publish; }
  public String getID(){ return id; }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "funLibraryMaker" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
