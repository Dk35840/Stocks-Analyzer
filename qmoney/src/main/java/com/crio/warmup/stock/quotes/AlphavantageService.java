
package com.crio.warmup.stock.quotes;



import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.SECONDS;

import com.crio.warmup.stock.dto.AlphavantageCandle;
import com.crio.warmup.stock.dto.AlphavantageDailyResponse;
import com.crio.warmup.stock.dto.Candle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class AlphavantageService implements StockQuotesService {

 
  private RestTemplate restTemplate;
  
  public AlphavantageService(RestTemplate restTemplate2) {
  this.restTemplate=restTemplate2;
}


@Override
  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to) throws JsonProcessingException {
   
    if(from.isAfter(to)) throw new RuntimeException();

    String url=buildUri(symbol);
    List<Candle> li= new ArrayList<>();
  
    AlphavantageDailyResponse stocks =restTemplate.getForObject(url, AlphavantageDailyResponse.class);
    System.out.println(stocks);
    for(Map.Entry<LocalDate, AlphavantageCandle> ac: stocks.getCandles().entrySet()){

      if(ac.getKey().isAfter(from) && ac.getKey().isBefore(to) || ac.getKey().isEqual(to)){
          ac.getValue().setDate(ac.getKey());
        li.add(0,ac.getValue());
      }
          
   }

   System.out.print("FDSFD"+li);
 
   
       return li;
    }

    

    
    
  
  /*
    for(int i=stocks.length-1;i>=0;i--){

      Candle ac=stocks[i];
      
     
*/
  

  


  protected String buildUri(String symbol) {

    String apiKey="0H5ZYX722SENYD0J";
    StringBuilder sb= new StringBuilder("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=");
    sb.append(symbol).append("&outputsize=full&apikey=").append(apiKey);

            return sb.toString();
  }

  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Implement the StockQuoteService interface as per the contracts. Call Alphavantage service
  //  to fetch daily adjusted data for last 20 years.
  //  Refer to documentation here: https://www.alphavantage.co/documentation/
  //  --
  //  The implementation of this functions will be doing following tasks:
  //    1. Build the appropriate url to communicate with third-party.
  //       The url should consider startDate and endDate if it is supported by the provider.
  //    2. Perform third-party communication with the url prepared in step#1
  //    3. Map the response and convert the same to List<Candle>
  //    4. If the provider does not support startDate and endDate, then the implementation
  //       should also filter the dates based on startDate and endDate. Make sure that
  //       result contains the records for for startDate and endDate after filtering.
  //    5. Return a sorted List<Candle> sorted ascending based on Candle#getDate
  // Note:
  // 1. Make sure you use {RestTemplate#getForObject(URI, String)} else the test will fail.
  // 2. Run the tests using command below and make sure it passes:
  //    ./gradlew test --tests AlphavantageServiceTest
  //CHECKSTYLE:OFF
    //CHECKSTYLE:ON
  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  1. Write a method to create appropriate url to call Alphavantage service. The method should
  //     be using configurations provided in the {@link @application.properties}.
  //  2. Use this method in #getStockQuote.

}

