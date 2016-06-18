import java.util.ArrayList.*;

/*
 * Author : Yasunori SANNOMIYA
 * Created: 2016.05.18
 */

final String FILE_NAME = "/data/list.csv";
ArrayList<Thesis> theses;

void setup(){
  theses = readFile( FILE_NAME );
  noLoop();
}

void draw(){
  ArrayList<String> html = new ArrayList<String>();
  ArrayList<String> table = new ArrayList<String>();

  for( int i=0, c=0; i<theses.size(); i++, c++ ){
    merge( table, toHTML(theses.get(i)) );
    if( c==50 || i==theses.size()-1 ){
      println("page split > "+ (i+1-c) +" - " + (i) + " / " + theses.size() );
      if( c==50 ){
        merge( html, generateNav((i+1-c), i, theses.size()));
      }else{
        merge( html, generateNav((i+1-c), i+1, theses.size()));
      }
      html.add("<br>");

      html.add("<table class=\"table\">");
      merge( html, generateTableHead() );
      merge( html, table );
      html.add("</table>");

      html.add("<br>");
      if( c==50 ){
        merge( html, generateNav((i+1-c), i, theses.size()));
      }else{
        merge( html, generateNav((i+1-c), i+1, theses.size()));
      }
      html.add("<br>");
      html.add("<br>");

      merge( html, generateFooter() );
      html.add("<br>");

      if( i==theses.size()-1 ){
        saveHTML( html, "page"+(i/50+1)+".html" );
      }else{
        saveHTML( html, "page"+(i/50)+".html" );
      }
      c=0;
      html.clear();
      table.clear();
    }
  }
  exit();
}

ArrayList<String> generateNav( int currentItemNum, int currentTotalItemNum, int totalItemNum ){
  ArrayList<String> html = new ArrayList<String>();
  html.add( "<p style=\"text-align:center;\">" );
  html.add( "  <a href=\"http://library.fun.ac.jp/?page_id=511\" style=\"text-decoration: none;\">" );
  html.add( "    &lt;&lt;先頭へ" );
  html.add( "  </a>" );
  html.add( "  <a href=\"http://library.fun.ac.jp/?page_id=118\" style=\"text-decoration: none;\">" );
  html.add( "    &nbsp;&lt;前へ" );
  html.add( "  </a>");
  html.add( "    &nbsp;&nbsp;"+currentItemNum+" - "+currentTotalItemNum+"件 / "+totalItemNum+"件&nbsp;&nbsp;" );
  html.add( "  <a href=\"http://library.fun.ac.jp/?page_id=118\" style=\"text-decoration: none;\">" );
  html.add( "    次へ&gt;&nbsp;" );
  html.add( "  </a>");
  html.add( "  <a href=\"http://library.fun.ac.jp/?page_id=1190\" style=\"text-decoration: none;\">" );
  html.add( "    末尾&gt;&gt;" );
  html.add( "  </a>" );
  html.add( "</p>" );
  return html;
}

ArrayList<String> generateTableHead(){
  ArrayList<String> html = new ArrayList<String>();
  html.add( "<tr>" );
  html.add( "    <th style=\"width: 20%;\">" );
  html.add( "       執筆者" );
  html.add( "    </th>" );
  html.add( "    <th style=\"width: 60%;\">" );
  html.add( "        論文名");
  html.add( "    </th>" );
  html.add( "    <th style=\"width: 10%;\">" );
  html.add( "        全文" );
  html.add( "    </th>" );
  html.add( "    <th style=\"width: 10%;\">" );
  html.add( "        要旨");
  html.add( "    </th>" );
  html.add( "</tr>" );
  return html;
}

ArrayList<String> generateFooter(){
  ArrayList<String> html = new ArrayList<String>();
  html.add("<div style=\"text-align: right;\"><a href=\"http://library.fun.ac.jp/?page_id=259\"><img hspace=\"0\" src=\"./?action=common_download_main&upload_id=513\" alt=\"\" style=\"border: 0px solid rgb(204, 204, 204); float: none;\" title=\"\" /></a></div>");
  return html;
}

ArrayList<String> toHTML( Thesis t ){
  ArrayList<String> html = new ArrayList<String>();
  html.add( "<tr>" );
  html.add( "    <td style=\"width: 20%;\">" );
  html.add( "       "+t.author_ja+"<br>"+t.author_en );
  html.add( "    </td>" );
  html.add( "    <td style=\"width: 60%;\">" );
  html.add( "        "+t.title_ja+"<br>"+t.title_en );
  html.add( "    </td>" );
  html.add( "    <td style=\"width: 10%;\">" );
  html.add( "        "+t.existText );
  html.add( "    </td>" );
  html.add( "    <td style=\"width: 10%;\">" );
  html.add( "        " + t.existAbstract );
  html.add( "    </td>" );
  html.add( "</tr>" );
  return html;
}

void merge( ArrayList<String> target, ArrayList<String> src ){
  for( String str: src ){
    target.add(str);
  }
}

void saveHTML( ArrayList<String> list, String savePath ){
  saveStrings( savePath, (String[])list.toArray(new String[0]));
}

ArrayList<Thesis> readFile( String filePath ){
  ArrayList<Thesis> arr = new ArrayList<Thesis>();
  String[] list = loadStrings( sketchPath+filePath );
  for( int i=0; i<list.length; i++ ){
    String[] tmp = split( list[i], "," );
    if( tmp.length<5 ){
      println("error : line "+ i );
      continue;
    }
    arr.add( new Thesis(tmp[0], tmp[1], tmp[2], tmp[3], tmp[4], tmp[5]) );
  }
  return arr;
}

class Thesis{
  public String author_ja, author_en, title_ja, title_en, existText, existAbstract;

  public Thesis( String author_ja, String author_en, String title_ja, String title_en, String existText, String existAbstract ){
    this.author_ja = author_ja.replaceAll(";", ",");
    this.author_en = author_en.replaceAll(";", ",");
    this.title_ja = title_ja.replaceAll(";", ",");
    this.title_en = title_en.replaceAll(";", ",");
    this.existText = existText;
    this.existAbstract = existAbstract;
  }
}
