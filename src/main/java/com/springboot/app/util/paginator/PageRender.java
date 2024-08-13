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
        this.pages = new ArrayList<>();

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
            if(pageCurrent <= maxPagesInPaginationBar / 2) {
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

    }
