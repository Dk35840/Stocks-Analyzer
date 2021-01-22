
package com.crio.warmup.stock.quotes;

import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.crio.warmup.stock.exception.StockQuoteServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class TiingoService implements StockQuotesService {


 
  private RestTemplate restTemplate;

  protected TiingoService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate  to) throws JsonProcessingException,
      StockQuoteServiceException {
   
    if(from.isAfter(to)) throw new RuntimeException();

    String url=buildUri(symbol, from, to);

    TiingoCandle[] stocks;

   try{ 

    String stocksString =restTemplate.getForObject(url, String.class);
    ObjectMapper objectMapper = new ObjectMapper();
    
    stocks =objectMapper.readValue(stocksString, TiingoCandle[].class);
      System.out.println(stocks);
    }catch(Exception e){
      throw new StockQuoteServiceException ("Error");
      }
    

  

    return Arrays.asList(stocks);

  }

  protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {

    String token="a064066c97f5c60827346ef971c029e28a396c07";
    StringBuilder sb= new StringBuilder("https:api.tiingo.com/tiingo/daily/");
    sb.append(symbol).append("/prices?startDate=").append(startDate).append("&endDate=").append(endDate).append("&token=").append(token);

            return sb.toString();
  }




  // TODO: CRIO_TASK_MODULE_EXCEPTIONS
  //  1. Update the method signature to match the signature change in the interface.
  //     Start throwing new StockQuoteServiceException when you get some invalid response from
  //     Tiingo, or if Tiingo returns empty results for whatever reason, or you encounter
  //     a runtime exception during Json parsing.
  //  2. Make sure that the exception propagates all the way from
  //     PortfolioManager#calculateAnnualisedReturns so that the external user's of our API
  //     are able to explicitly handle this exception upfront.

  //CHECKSTYLE:OFF


}
