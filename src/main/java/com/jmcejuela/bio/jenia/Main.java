package com.jmcejuela.bio.jenia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.jmcejuela.bio.jenia.common.Sentence;

/**
 * From main.cpp
 */
public class Main {
	
  public static String input ="GTP hydrolysis triggers the Ras protein pathway.";;

  public static final String version = "0.4.0";

  public static final String ENDL = System.getProperty("line.separator");

  public static void line(StringBuilder s, String msg) {
    s.append(msg);
    s.append(ENDL);
  }

  public static String help() {
    StringBuilder s = new StringBuilder();
    line(s, "jeniatagger " + version + " -- https://github.com/jmcejuela/geniatagger");
    line(s, "");
    line(s, "Usage: jeniatagger --models path [option] [file]");
    line(s, "");
    line(s, "Analyze sentences assumed to be in English and come from the biomedicine domain.");
    line(s, "Print the base forms, part-of-speech tags, chunk tags, and named entity tags.");
    line(s, "");
    line(s, "Options:");
    line(s, "  --models     local path where to find the jeniatagger models");
    line(s, "  --nt         don't tokenize (the input is assumed to be already tokenized, space-separated)");
    line(s, "  --help       display this help and exit");
    line(s, "");
    return s.toString();
  }

  public static void printHelpAndExit() {
    printHelpAndExit(null);
  }

  public static void printHelpAndExit(Exception e) {
    PrintStream out = System.out;
    int exitStatus = 0;
    if (e != null) {
      out = System.err;
      out.println("ERROR  " + e.getLocalizedMessage() + ENDL);
      exitStatus = -1;
    }
    out.println(help());
    System.exit(exitStatus);
  }

  /**
   * @param args
   * @throws IOException
   */
  
  
  public static void main(String[] args) throws IOException {
    boolean dont_tokenize = true;
    String ifilename = null;
    // String ofilename;

    try {
      for (int i = 0; i < args.length; i++) {
        String arg = args[i];
        if (arg.equals("--models"))
          JeniaTagger.setModelsPath(args[++i]);
        else if (arg.equals("--nt"))
          dont_tokenize = true;
        else if (arg.equals("--help"))
          printHelpAndExit();
        else
          ifilename = arg;
      }
      JeniaTagger.getModelsPath(); //prove it is set
    } catch (Exception e) {
      printHelpAndExit(e);
    }

    //IF YOU WANT TO USE BUFFEREDREADER, UNCOMMENT THE COMMENTS, AND SET 'line' = 'in'
// default, standard input
//    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//    if (ifilename != null && !ifilename.isEmpty() && !ifilename.equals("-")) {
//      in = new BufferedReader(new FileReader(new File(ifilename)));
//    }
    

    // ----------------------------------------------------------------------------
    System.out.println("Sentence: "+input);
    System.err.println("Parsing... (the first sentence will take longer until all dictionaries are loaded)");

    String line=input;
    int n = 1;
//    while ((line = in.readLine()) != null) {
//      if (line.length() > 1024) {
//        System.err.println("warning: the sentence seems to be too long at line " + n +
//            " (please note that the input should be one-sentence-per-line).");
//      }
      Sentence analysis = JeniaTagger.analyzeAll(line, dont_tokenize);
      System.out.println(analysis);
      n++;
    }

//    in.close();
//    System.out.flush();
  }
//}
