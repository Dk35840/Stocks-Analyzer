
package com.crio.warmup.stock.portfolio;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.SECONDS;

import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;

public class PortfolioManagerImpl implements PortfolioManager {


  RestTemplate restTemplate;

  // Caution: Do not delete or modify the constructor, or else your build will break!
  // This is absolutely necessary for backward compatibility
  protected PortfolioManagerImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

 public PortfolioManagerImpl(){}

  //TODO: CRIO_TASK_MODULE_REFACTOR
  // 1. Now we want to convert our code into a module, so we will not call it from main anymore.
  //    Copy your code from Module#3 PortfolioManagerApplication#calculateAnnualizedReturn
  //    into #calculateAnnualizedReturn function here and ensure it follows the method signature.
  // 2. Logic to read Json file and convert them into Objects will not be required further as our
  //    clients will take care of it, going forward.

  // Note:
  // Make sure to exercise the tests inside PortfolioManagerTest using command below:
  // ./gradlew test --tests PortfolioManagerTest

  //CHECKSTYLE:OFF




  private Comparator<AnnualizedReturn> getComparator() {
    return Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed();
  }

  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_REFACTOR
  //  Extract the logic to call Tiingo third-party APIs to a separate function.
  //  Remember to fill out the buildUri function and use that.


  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws JsonProcessingException {

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

  @Override
  public List<AnnualizedReturn> calculateAnnualizedReturn(List<PortfolioTrade> portfolioTrades, LocalDate endDate) {
  
    List<AnnualizedReturn> returnList= new ArrayList<>();

    for(PortfolioTrade trade:portfolioTrades){

     
      
      AnnualizedReturn ar=null;
      try {
        ar = getAnnualizedReturn(trade, endDate);
      } catch (JsonProcessingException e) {
       
        e.printStackTrace();
      }

       returnList.add(ar);
    }
     
      Comparator<AnnualizedReturn> comparator=Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed();

      Collections.sort(returnList,comparator);

    return returnList;
  }

  public AnnualizedReturn getAnnualizedReturn(PortfolioTrade trade,LocalDate endDate) throws JsonProcessingException {

  
    if(endDate.isBefore(trade.getPurchaseDate()))  throw new RuntimeException();

    List<Candle> getStockQuoteList= getStockQuote(trade.getSymbol(),trade.getPurchaseDate(),endDate);

    Double buyPrice=getStockQuoteList.get(0).getOpen();
    Double sellPrice=getStockQuoteList.get(getStockQuoteList.size()-1).getClose();

    long noOfDaysBetween = ChronoUnit.DAYS.between(trade.getPurchaseDate(), endDate);
    double totalReturns=(sellPrice-buyPrice)*1.0/buyPrice;
    double annualized_returns=Math.pow(1+totalReturns, 1.0*365/(noOfDaysBetween))-1;

    //System.out.println("Get Days"+trade.getSymbol()+"period"+  daysSpend+":"+noOfDaysBetween+";"+trade.getPurchaseDate()+""+endDate);

  return new AnnualizedReturn(trade.getSymbol(), annualized_returns, totalReturns);
   
  }
}