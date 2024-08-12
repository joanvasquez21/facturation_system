package com.springboot.app.util.paginator;

import org.springframework.data.domain.Page;

public class PageRender<T> {
    
    private String url;
    private Page<T> page;


    private int totalPages;
    private int numElementsByPage; 
    private int pageCurrent;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;

        numElementsByPage = page.getSize();
        totalPages = page.getTotalPages();
        pageCurrent = page.getNumber() +1;
        int from, until;
        if(totalPages <= numElementsByPage) {
            from = 1;
            until = totalPages;
        }else{
            if(pageCurrent <= numElementsByPage / 2) {
                from = 1;
                until = numElementsByPage;
            }else if(pageCurrent >= totalPages - numElementsByPage/2){
                from = totalPages - numElementsByPage + 1;
                until = numElementsByPage;
            } else{
                from = pageCurrent - numElementsByPage/2;
                until = numElementsByPage;
            }
            }
        }


    }

}
