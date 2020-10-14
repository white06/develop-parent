package com.tdu.develop.resource.pojo;


public class Word {
    private String id;

    private String word;
    private String soundmark;
    private String sectionId;
    private String translate;
    private String classLevel;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSoundmark() {
        return soundmark;
    }

    public void setSoundmark(String soundmark) {
        this.soundmark = soundmark;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(String classLevel) {
        this.classLevel = classLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Word [id=" + id + ", word=" + word + ", soundmark=" + soundmark + ", sectionId=" + sectionId
                + ", translate=" + translate + ", classLevel=" + classLevel + ", getId()=" + getId() + ", getWord()="
                + getWord() + ", getSoundmark()=" + getSoundmark() + ", getSectionId()=" + getSectionId()
                + ", getTranslate()=" + getTranslate() + ", getClassLevel()=" + getClassLevel() + ", getClass()="
                + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }

}
