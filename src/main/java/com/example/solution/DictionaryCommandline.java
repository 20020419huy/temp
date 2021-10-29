package com.example.solution;
import javafx.scene.control.ListView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class DictionaryCommandline  {
    public static int spaceword = 30;
    public List<String> showAllWordsWord(Dictionary dictionar) {
        List<String> line = new ArrayList<>();
        for(int i = 0; i < dictionar.list_word.size(); i++) {
            String english = dictionar.list_word.get(i).getWord_target() + "\n" + dictionar.list_word.get(i).getWord_explain();
            line.add(english);
        }
        return line;
    }

   public Word dictionarySearcher(Dictionary dictionary, String input) {
       for (int i = 0; i < dictionary.size(); i++) {
            if (dictionary.get(i).getWord_target().equals(input)) {
                return dictionary.get(i);
            }
        }
        return null;
       /*int n = dictionary.size();
       int l = 0;
       int r = n - 1;
       while(l < r) {
           int mid = (l+r)/2;
           if (dictionary.get(mid).getWord_target().compareTo(input) < 0) {
               l = mid + 1;
           }
           else if (dictionary.get(mid).getWord_target().compareTo(input) > 0) {
               r = mid - 1;
           }
           else {
               return dictionary.get(mid);
           }
       }
       return null;*/
    }


    public boolean wordInDictionary(Dictionary dictionary, String word) {
        for (int i = 0; i < dictionary.size(); i++) {
            if (dictionary.get(i).getWord_explain().equals(word) || dictionary.get(i).getWord_target().equals(word)) {
                return true;
            }
        }
        return false;
    }

    public void Delete(Dictionary dictionary, String delete) {
        for (int i = 0; i < dictionary.size(); i++) {
            if (dictionary.get(i).getWord_explain().equals(delete) || dictionary.get(i).getWord_target().equals(delete)) {
                dictionary.remove(i);
                break;
            }
        }
    }

    public boolean checkInDictionary(Dictionary dictionary, Word word) {
        if (word == null) return true;
        for (int i = 0; i < dictionary.size(); i++) {
            if (dictionary.get(i).getWord_target().equals(word.getWord_target()) && dictionary.get(i).getWord_explain().equals(word.getWord_explain())) return true;
        }
        return false;
    }

    public void Add(Dictionary dictionary, Word word) {
        if (!checkInDictionary(dictionary,word)){
            dictionary.add(word);
        }
    }

    public void AddNote(Dictionary dictionary, Word word) {
        if(!checkInDictionary(dictionary,word)) {
            if(dictionary.size() == 0) {
                dictionary.add(word);
            } else if(dictionary.get(0).getWord_target().compareTo(word.getWord_target()) > 0) {
                dictionary.list_word.add(0, word);
            } else if (dictionary.get(dictionary.size() - 1).getWord_target().compareTo(word.getWord_target()) < 0) {
                dictionary.add(word);
            } else {
                for (int i = 0; i < dictionary.size() - 1; i++) {
                    if (dictionary.get(i).getWord_target().compareTo(word.getWord_target()) <= 0 && dictionary.get(i + 1).getWord_target().compareTo(word.getWord_target()) > 0) {
                        dictionary.list_word.add(i + 1, word);
                        break;
                    }
                }
            }
        }
    }

}