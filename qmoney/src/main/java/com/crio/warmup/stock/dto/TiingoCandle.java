
package com.crio.warmup.stock.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TiingoCandle implements Candle {

  private Double open;
  private Double close;
  private Double high;
  private Double low;
 
  private Date privatedate;

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
  public String toString() {
    return "TiingoCandle{"
            + "open=" + open
            + ", close=" + close
            + ", high=" + high
            + ", low=" + low
           
            + '}';
  }

  public Date getPrivatedate() {
    return privatedate;
  }

  public void setPrivatedate(Date privatedate) {
    this.privatedate = privatedate;
  }

  @Override
  public LocalDate getDate() {
   return privatedate.toInstant().atZone(ZoneId.systemDefault())
   .toLocalDate();
   
  }
}
