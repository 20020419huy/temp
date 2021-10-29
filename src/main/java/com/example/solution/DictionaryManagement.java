package com.example.solution;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.io.*;
import java.util.Scanner;

public class DictionaryManagement {
    public static String url = "C:\\Users\\nguye\\OneDrive\\Desktop\\workSpace\\dictionary\\input\\dictionaries.txt";
    public static String urlNote = "C:\\Users\\nguye\\OneDrive\\Desktop\\workSpace\\dictionary\\input\\note.txt";


    public Dictionary InsertFromFile(Dictionary dictionary, String url) throws FileNotFoundException {
        File input = new File(url);
        Scanner scanner = new Scanner(input);

        Word new_word = new Word();
        String target = "";
        String explain = "";
        while (scanner.hasNextLine()) {
            String m = scanner.nextLine();
            if (!target.isEmpty() && !m.isEmpty()) {
                if (m.charAt(0) == '@') {
                    new_word.setWord_explain(explain);
                    new_word.setWord_target(target);
                    dictionary.add(new_word);
                    new_word = new Word();
                    target = "";
                    explain = "";
                }

            }
            if (!m.isEmpty()) {
                if (m.charAt(0) == '@') {
                    int a = m.indexOf("/");
                    if (a != -1) {
                        target = target + m.substring(1,a-1);
                        explain = explain + m.substring(a,m.length()) + "\n";

                    } else {
                        target = target + m.substring(1,m.length());
                    }

                }
                else if (m.charAt(0) == '-' || m.charAt(0) == '*') {
                    explain = explain + m.substring(1,m.length()) + "\n";
                }
                else if (m.charAt(0) == '=') {
                    int vt = 0;
                    for (int i = 1; i < m.length(); i++) {
                        if (m.charAt(i) == '+') {
                            explain = explain + m.substring(1,i) + "\n";
                            explain = explain + m.substring(i + 1, m.length()) + "\n";
                            break;
                        }
                    }
                } else {
                    explain = explain + m.substring(1,m.length()) + "\n";
                }
            }
        }
        return dictionary;
    }

    public void InsertFromFileNote(Dictionary dictionary, String url) {
        String data = null;
        try {
            File myObj = new File(url);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                Word word = new Word();
                word.setWord_target(data.split("_")[0]);
                word.setWord_explain(data.split("_")[1]);
                dictionary.add(word);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void dictionaryExportToFile(Dictionary dictionary) throws IOException {
        FileWriter writer = new FileWriter(this.urlNote);
        for(int i = 0; i < dictionary.size(); i++) {
//            String temp = "@" + dictionary.get(i).getWord_target() + "\n" + " " + dictionary.get(i).getWord_explain() + "\n";
//            writer.write(temp);
//            if (i == dictionary.size() -1) {
//                writer.write("@");
//            }
            String data = dictionary.get(i).getWord_target() + "_" + dictionary.get(i).getWord_explain();
            if(i != dictionary.size() - 1) {
                data += '\n';
            }
            writer.write(data);
        }
        writer.close();
    }

    public String dictionaryLookup(Dictionary dictionary, String input) {
        String ouput = "";
        for (int i = 0; i < dictionary.size(); i++) {
            if (input.equals(dictionary.get(i).getWord_target())) {
                ouput += dictionary.get(i).getWord_explain();
                break;
            }
        }
        return ouput;
    }

    public static boolean check_in_listview(ListView <String> listView, String s) {
        ObservableList listOfItems = listView.getSelectionModel().getSelectedItems();

        for (Object item : listOfItems) {
            String test =  item.toString();
            if (s.equals((test))) {
                return true;
            }
        }
        return false;
    }
}