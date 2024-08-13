package com.springboot.app.util.paginator;

public class PageItem {
    
    private int numberPages;
    private boolean isCurrent;


    public PageItem(int numberPages, boolean isCurrent) {
        this.numberPages = numberPages;
        this.isCurrent = isCurrent;
    }

    public int getNumberPages() {
        return this.numberPages;
    }

    public void setNumberPages(int numberPages) {
        this.numberPages = numberPages;
    }

    public boolean isIsCurrent() {
        return this.isCurrent;
    }

    public boolean getIsCurrent() {
        return this.isCurrent;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

}
