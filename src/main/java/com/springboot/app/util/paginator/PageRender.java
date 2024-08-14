package com.springboot.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {
    
    private String url;

    private Page<T> page;

    private int totalPages;

    private int maxPagesInPaginationBar; 

    private int pageCurrent;

    private List<PageItem> pages;

    public PageRender(String url, Page<T> page) {

        this.url = url;
        this.page = page;

        this.pages = new ArrayList<PageItem>();

        //GetSize contiene la cantidad de elementos por pagina
        maxPagesInPaginationBar = page.getSize();
        //Obtenemos la cantidad de paginas
        totalPages = page.getTotalPages();
        pageCurrent = page.getNumber() +1;
        int from, until;
        //totalPage es el cantidad que se mostrara en cada pagina 
        if(totalPages <= maxPagesInPaginationBar) {
            from = 1;
            until = totalPages;
        }else{
            if(pageCurrent <= maxPagesInPaginationBar/2) {
                from = 1;
                until = maxPagesInPaginationBar;
            }else if(pageCurrent >= totalPages - maxPagesInPaginationBar/2){
                from = totalPages - maxPagesInPaginationBar + 1;
                until = maxPagesInPaginationBar;
            } else{
                from = pageCurrent - maxPagesInPaginationBar/2;
                until = maxPagesInPaginationBar;
            }
            }
        

        for(int i = 0; i < until; i++){
            pages.add(new PageItem(from + i, pageCurrent == from + i));
        }
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Page<T> getPage() {
        return this.page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getMaxPagesInPaginationBar() {
        return this.maxPagesInPaginationBar;
    }

    public void setMaxPagesInPaginationBar(int maxPagesInPaginationBar) {
        this.maxPagesInPaginationBar = maxPagesInPaginationBar;
    }

    public int getPageCurrent() {
        return this.pageCurrent;
    }

    public void setPageCurrent(int pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public List<PageItem> getPages() {
        return this.pages;
    }

    public void setPages(List<PageItem> pages) {
        this.pages = pages;
    }

    public boolean isFirst(){
        return page.isFirst();
    }

    public boolean isLast(){
        return page.isLast();
    }

    public boolean isHasNext(){
        return page.hasNext();
    }
    public boolean isHasPrevious(){
        return page.hasPrevious();
    }

}