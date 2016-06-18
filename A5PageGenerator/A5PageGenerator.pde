import java.util.ArrayList.*;

/*
 * Author : Yasunori SANNOMIYA
 * Created: 2015.05.28
 */

final String FILE_NAME = "filePath";
ArrayList<Book> books;

void setup(){
  books = new ArrayList<Book>();
  noLoop();
}

void draw(){
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

void readFile( String filePath ){
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