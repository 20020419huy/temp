package com.example.solution;

import java.util.Objects;

public class Word {
    public Word() {
        this.word_target = "a";
        this.word_explain = "a";
    }

    public Word(String target, String explain) {
        this.word_target = target;
        this.word_explain = explain;
    }

    private
        String word_target;
        String word_explain;

    public void setWord_target(String target) {
        this.word_target = target;
    }
    public void setWord_explain(String explain) {
        this.word_explain = explain;
    }
    public String getWord_target() {
        return  word_target;
    }
    public String getWord_explain() {
        return  word_explain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(word_target, word.word_target) && Objects.equals(word_explain, word.word_explain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word_target, word_explain);
    }

}