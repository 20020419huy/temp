package com.example.solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class Dictionary {
    public List <Word> list_word = new Vector<>();

    public void add(Word word) {
        list_word.add(word);
    }

    public Word get(int i) {
        return list_word.get(i);
    }

    public void set(Word new_word, int i) {
        Word word = list_word.get(i);

        word.setWord_target(new_word.getWord_target());
        word.setWord_explain(new_word.getWord_explain());
    }

    public int size() {
        return list_word.size();
    }

    public void remove(int i) {
        list_word.remove(i);
    }

    public void remove(Word word) {
        list_word.remove(word);
    }

}