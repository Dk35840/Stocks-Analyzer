
package com.crio.warmup.stock.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.*;
import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TiingoCandle implements Candle {

  private Double open;
  private Double close;
  private Double high;
  private Double low;
  private LocalDate date;

  @Override
  public Double getOpen() {
    return open;
  }

  public void setOpen(Double open) {
    this.open = open;
  }

  @Override
  public Double getClose() {
    return close;
  }

  public void setClose(Double close) {
    this.close = close;
  }

  @Override
  public Double getHigh() {
    return high;
  }

  public void setHigh(Double high) {
    this.high = high;
  }

  @Override
  public Double getLow() {
    return low;
  }

  public void setLow(Double low) {
    this.low = low;
  }

  @Override
  public LocalDate getDate() {
    return date;
  }

 
  public void setDate(Date timeStamp) {
    
    this.date = timeStamp.toInstant()
    .atZone(ZoneId.systemDefault())
    .toLocalDate();;
  }

  @Override
  public String toString() {
    return "TiingoCandle{"
            + "open=" + open
            + ", close=" + close
            + ", high=" + high
            + ", low=" + low
            + ", date=" + date
            + '}';
  }
}
