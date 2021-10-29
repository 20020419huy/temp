package com.google.cloud.examples.translate.snippets;

import java.util.ArrayList;

public class OxfordData {
    public ArrayList<String> ipa;
    public ArrayList<String>audio;
    public ArrayList<com.google.cloud.examples.translate.snippets.DefineExample> defineExamples;
    public OxfordData () {
        this.audio = new ArrayList<String>();
        this.ipa = new ArrayList<String>();
        this.defineExamples = new ArrayList<com.google.cloud.examples.translate.snippets.DefineExample>();
    }
}
