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

public class ThesisPageGenerator extends PApplet {



/*
 * Author : Yasunori SANNOMIYA
 * Created: 2016.05.18
 */

final String FILE_NAME = "/data/list.csv";
ArrayList<Thesis> theses;

public void setup(){
  theses = readFile( FILE_NAME );
  noLoop();
}

public void draw(){
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

public ArrayList<String> generateNav( int currentItemNum, int currentTotalItemNum, int totalItemNum ){
  ArrayList<String> html = new ArrayList<String>();
  html.add( "<p style=\"text-align:center;\">" );
  html.add( "  <a href=\"http://library.fun.ac.jp/?page_id=511\" style=\"text-decoration: none;\">" );
  html.add( "    &lt;&lt;\u5148\u982d\u3078" );
  html.add( "  </a>" );
  html.add( "  <a href=\"http://library.fun.ac.jp/?page_id=118\" style=\"text-decoration: none;\">" );
  html.add( "    &nbsp;&lt;\u524d\u3078" );
  html.add( "  </a>");
  html.add( "    &nbsp;&nbsp;"+currentItemNum+" - "+currentTotalItemNum+"\u4ef6 / "+totalItemNum+"\u4ef6&nbsp;&nbsp;" );
  html.add( "  <a href=\"http://library.fun.ac.jp/?page_id=118\" style=\"text-decoration: none;\">" );
  html.add( "    \u6b21\u3078&gt;&nbsp;" );
  html.add( "  </a>");
  html.add( "  <a href=\"http://library.fun.ac.jp/?page_id=1190\" style=\"text-decoration: none;\">" );
  html.add( "    \u672b\u5c3e&gt;&gt;" );
  html.add( "  </a>" );
  html.add( "</p>" );
  return html;
}

public ArrayList<String> generateTableHead(){
  ArrayList<String> html = new ArrayList<String>();
  html.add( "<tr>" );
  html.add( "    <th style=\"width: 20%;\">" );
  html.add( "       \u57f7\u7b46\u8005" );
  html.add( "    </th>" );
  html.add( "    <th style=\"width: 60%;\">" );
  html.add( "        \u8ad6\u6587\u540d");
  html.add( "    </th>" );
  html.add( "    <th style=\"width: 10%;\">" );
  html.add( "        \u5168\u6587" );
  html.add( "    </th>" );
  html.add( "    <th style=\"width: 10%;\">" );
  html.add( "        \u8981\u65e8");
  html.add( "    </th>" );
  html.add( "</tr>" );
  return html;
}

public ArrayList<String> generateFooter(){
  ArrayList<String> html = new ArrayList<String>();
  html.add("<div style=\"text-align: right;\"><a href=\"http://library.fun.ac.jp/?page_id=259\"><img hspace=\"0\" src=\"./?action=common_download_main&upload_id=513\" alt=\"\" style=\"border: 0px solid rgb(204, 204, 204); float: none;\" title=\"\" /></a></div>");
  return html;
}

public ArrayList<String> toHTML( Thesis t ){
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

public void merge( ArrayList<String> target, ArrayList<String> src ){
  for( String str: src ){
    target.add(str);
  }
}

public void saveHTML( ArrayList<String> list, String savePath ){
  saveStrings( savePath, (String[])list.toArray(new String[0]));
}

public ArrayList<Thesis> readFile( String filePath ){
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
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "ThesisPageGenerator" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
