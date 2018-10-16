package me.jpomykala.starters.springhoc.api;

import org.springframework.data.domain.Page;

/**
 * Created by evelan on 25/04/2017.
 */
public class PageDetails {

  private Long totalElements;
  private Integer totalPages;
  private Integer currentPage;
  private Boolean hasNext;
  private Boolean isFirst;
  private Boolean isLast;

  public PageDetails() {
    this.totalElements = 0L;
    this.totalPages = 0;
    this.currentPage = 0;
    this.hasNext = false;
    this.isFirst = true;
    this.isLast = true;
  }

  public PageDetails(Long totalElements, Integer totalPages, Integer currentPage, Boolean hasNext, Boolean isFirst, Boolean isLast) {
    this.totalElements = totalElements;
    this.totalPages = totalPages;
    this.currentPage = currentPage;
    this.hasNext = hasNext;
    this.isFirst = isFirst;
    this.isLast = isLast;
  }

  public PageDetails(Page page) {
    this.currentPage = page.getNumber();
    this.totalElements = page.getTotalElements();
    this.totalPages = page.getTotalPages();
    this.hasNext = page.hasNext();
    this.isFirst = page.isFirst();
    this.isLast = page.isLast();
  }
}
