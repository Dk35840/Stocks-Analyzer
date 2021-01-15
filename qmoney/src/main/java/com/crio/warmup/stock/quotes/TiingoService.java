
package com.crio.warmup.stock.quotes;

import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.client.RestTemplate;

public class TiingoService implements StockQuotesService {


  private RestTemplate restTemplate;

  protected TiingoService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to) throws JsonProcessingException {
    if(from.isAfter(to)) throw new RuntimeException();

    String url=buildUri(symbol, from, to);

    Candle[] stocks =restTemplate.getForObject(url, Candle[].class);

    
    return Arrays.asList(stocks);
  }

  protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {

    String token="a064066c97f5c60827346ef971c029e28a396c07";
    StringBuilder sb= new StringBuilder("https:api.tiingo.com/tiingo/daily/");
    sb.append(symbol).append("/prices?startDate=").append(startDate).append("&endDate=").append(endDate).append("&token=").append(token);

            return sb.toString();
  }


  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Implement getStockQuote method below that was also declared in the interface.

  // Note:
  // 1. You can move the code from PortfolioManagerImpl#getStockQuote inside newly created method.
  // 2. Run the tests using command below and make sure it passes.
  //    ./gradlew test --tests TiingoServiceTest


  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Write a method to create appropriate url to call the Tiingo API.

}
