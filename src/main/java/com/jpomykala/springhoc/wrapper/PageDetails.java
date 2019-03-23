package com.jpomykala.springhoc.wrapper;

import org.springframework.data.domain.Page;

public final class PageDetails {

  private long totalElements;
  private int totalPages;
  private int currentPage;
  private boolean hasNext;
  private boolean isFirst;
  private boolean isLast;

  public PageDetails() {
    this.totalElements = 0L;
    this.totalPages = 0;
    this.currentPage = 0;
    this.hasNext = false;
    this.isFirst = true;
    this.isLast = true;
  }

  private PageDetails(long totalElements, int totalPages, int currentPage, boolean hasNext, boolean isFirst, boolean isLast) {
    this.totalElements = totalElements;
    this.totalPages = totalPages;
    this.currentPage = currentPage;
    this.hasNext = hasNext;
    this.isFirst = isFirst;
    this.isLast = isLast;
  }

  public static PageDetails of(Page page) {
    return new PageDetails(
            page.getTotalElements(),
            page.getTotalPages(),
            page.getNumber(),
            page.hasNext(),
            page.isFirst(),
            page.isLast());
  }

  public static PageDetails empty() {
    return new PageDetails(0L, 0, 0, false, true, true);
  }

  public long getTotalElements() {
    return totalElements;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public boolean getHasNext() {
    return hasNext;
  }

  public boolean getFirst() {
    return isFirst;
  }

  public boolean getLast() {
    return isLast;
  }
}
