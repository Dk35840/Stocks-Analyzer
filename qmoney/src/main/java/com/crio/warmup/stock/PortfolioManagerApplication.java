
package com.crio.warmup.stock;


import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TotalReturnsDto;
import com.crio.warmup.stock.log.UncaughtExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.ResponseEntity;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.client.RestTemplate;


public class PortfolioManagerApplication {

  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Read the json file provided in the argument[0]. The file will be available in the classpath.
  //    1. Use #resolveFileFromResources to get actual file from classpath.
  //    2. Extract stock symbols from the json file with ObjectMapper provided by #getObjectMapper.
  //    3. Return the list of all symbols in the same order as provided in json.

  //  Note:
  //  1. There can be few unused imports, you will need to fix them to make the build pass.
  //  2. You can use "./gradlew build" to check if your code builds successfully.

  public static List<String> mainReadFile(String[] args) throws IOException, URISyntaxException {
    
    File Filename=resolveFileFromResources(args[0]);
    //System.out.print(filename);

    PortfolioTrade[] allvalue=getObjectMapper().readValue(Filename, PortfolioTrade[].class);
    List<String> list= new ArrayList<>();
    
    for(PortfolioTrade pf:allvalue){
      list.add(pf.getSymbol());
    }
      return list;
  }



  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  Now that you have the list of PortfolioTrade and their data, calculate annualized returns
  //  for the stocks provided in the Json.
  //  Use the function you just wrote #calculateAnnualizedReturns.
  //  Return the list of AnnualizedReturns sorted by annualizedReturns in descending order.

  // Note:
  // 1. You may need to copy relevant code from #mainReadQuotes to parse the Json.
  // 2. Remember to get the latest quotes from Tiingo API.





  // Note:
  // 1. You may have to register on Tiingo to get the api_token.
  // 2. Look at args parameter and the module instructions carefully.
  // 2. You can copy relevant code from #mainReadFile to parse the Json.
  // 3. Use RestTemplate#getForObject in order to call the API,
  //    and deserialize the results in List<Candle>



  private static void printJsonObject(Object object) throws IOException {
    Logger logger = Logger.getLogger(PortfolioManagerApplication.class.getCanonicalName());
    ObjectMapper mapper = new ObjectMapper();
    logger.info(mapper.writeValueAsString(object));
  }

  private static File resolveFileFromResources(String filename) throws URISyntaxException {
    return Paths.get(
        Thread.currentThread().getContextClassLoader().getResource(filename).toURI()).toFile();
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }


  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Follow the instructions provided in the task documentation and fill up the correct values for
  //  the variables provided. First value is provided for your reference.
  //  A. Put a breakpoint on the first line inside mainReadFile() which says
  //    return Collections.emptyList();
  //  B. Then Debug the test #mainReadFile provided in PortfoliomanagerApplicationTest.java
  //  following the instructions to run the test.
  //  Once you are able to run the test, perform following tasks and record the output as a
  //  String in the function below.
  //  Use this link to see how to evaluate expressions -
  //  https://code.visualstudio.com/docs/editor/debugging#_data-inspection
  //  1. evaluate the value of "args[0]" and set the value
  //     to the variable named valueOfArgument0 (This is implemented for your reference.)
  //  2. In the same window, evaluate the value of expression below and set it
  //  to resultOfResolveFilePathArgs0
  //     expression ==> resolveFileFromResources(args[0])
  //  3. In the same window, evaluate the value of expression below and set it
  //  to toStringOfObjectMapper.
  //  You might see some garbage numbers in the output. Dont worry, its expected.
  //    expression ==> getObjectMapper().toString()
  //  4. Now Go to the debug window and open stack trace. Put the name of the function you see at
  //  second place from top to variable functionNameFromTestFileInStackTrace
  //  5. In the same window, you will see the line number of the function in the stack trace window.
  //  assign the same to lineNumberFromTestFileInStackTrace
  //  Once you are done with above, just run the corresponding test and
  //  make sure its working as expected. use below command to do the same.
  //  ./gradlew test --tests PortfolioManagerApplicationTest.testDebugValues

  public static List<String> debugOutputs() {

     String valueOfArgument0 = "trades.json";
     String resultOfResolveFilePathArgs0 = "";
     String toStringOfObjectMapper = "";
     String functionNameFromTestFileInStackTrace = "";
     String lineNumberFromTestFileInStackTrace = "";


    return Arrays.asList(new String[]{valueOfArgument0, resultOfResolveFilePathArgs0,
        toStringOfObjectMapper, functionNameFromTestFileInStackTrace,
        lineNumberFromTestFileInStackTrace});
  }


  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.
  public static List<AnnualizedReturn> mainCalculateSingleReturn(String[] args)
      throws IOException, URISyntaxException {
     return Collections.emptyList();
  }

  // TODO: CRIO_TASK_MODULE_CALCULATIONS
  //  Return the populated list of AnnualizedReturn for all stocks.
  //  Annualized returns should be calculated in two steps:
  //   1. Calculate totalReturn = (sell_value - buy_value) / buy_value.
  //      1.1 Store the same as totalReturns
  //   2. Calculate extrapolated annualized returns by scaling the same in years span.
  //      The formula is:
  //      annualized_returns = (1 + total_returns) ^ (1 / total_num_years) - 1
  //      2.1 Store the same as annualized_returns
  //  Test the same using below specified command. The build should be successful.
  //     ./gradlew test --tests PortfolioManagerApplicationTest.testCalculateAnnualizedReturn

  public static AnnualizedReturn calculateAnnualizedReturns(LocalDate endDate,
      PortfolioTrade trade, Double buyPrice, Double sellPrice) {

        int year=endDate.getYear()-trade.getPurchaseDate().getYear();
        double totalReturns=(sellPrice-buyPrice)*1.0/buyPrice;
        double annualized_returns=Math.pow(1+totalReturns, 1.0/year)-1;

      return new AnnualizedReturn(trade.getSymbol(), annualized_returns, totalReturns);
  }













  public static void main(String[] args) throws Exception {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    ThreadContext.put("runId", UUID.randomUUID().toString());

    printJsonObject(mainReadQuotes(args));



  }


  // TODO: CRIO_TASK_MODULE_REST_API
  //  Find out the closing price of each stock on the end_date and return the list
  //  of all symbols in ascending order by its close value on end date.

  // Note:
  // 1. You may have to register on Tiingo to get the api_token.
  // 2. Look at args parameter and the module instructions carefully.
  // 2. You can copy relevant code from #mainReadFile to parse the Json.
  // 3. Use RestTemplate#getForObject in order to call the API,
  //    and deserialize the results in List<Candle>

  public static List<String> mainReadQuotes(String[] args) throws IOException, URISyntaxException {
    RestTemplate restTemplate = new RestTemplate();
    ArrayList<Candle> al= new ArrayList<>();

    File Filename=resolveFileFromResources(args[0]);
    String enddate=args[1];
    //System.out.print(filename);

    PortfolioTrade[] allvalue=getObjectMapper().readValue(Filename, PortfolioTrade[].class);
    
    
    for(PortfolioTrade pf:allvalue){

      LocalDate date = LocalDate.parse(enddate);

      if(!pf.getPurchaseDate().isBefore(date)) throw new RuntimeException();

     String url ="https://api.tiingo.com/tiingo/daily/"+pf.getSymbol()+"/prices?endDate="+enddate+"+&startDate="+pf.getPurchaseDate()+"+&token=a064066c97f5c60827346ef971c029e28a396c07&columns=close";
    //System.out.println(url);
     ResponseEntity<Candle[]> response = restTemplate.getForEntity(url,Candle[].class);
     Candle[] employees = response.getBody();

     if(employees.length==0) 
       continue;

       Candle c=employees[employees.length-1];
       al.add(new Candle(c.getDate(),c.getClose(),pf.getSymbol())); 
     
      
    }

   Collections.sort(al,(a,b)->Double.compare(a.getClose(),b.getClose()));
   List<String> sym= new ArrayList<>();


    for(Candle c:al)
      sym.add(c.getSymbol());

    return sym;
    
 }


}

class Candle{
  private String date;
private Double  close;
private String symbol;

public String getDate() {
  return date;
}

public void setDate(String date) {
  this.date = date;
}

public Double getClose() {
  return close;
}

public void setClose(Double close) {
  this.close = close;
}

Candle(){}

public Candle(String date, Double close, String symbol) {
  this.date = date;
  this.close = close;
  this.symbol = symbol;
}

public String getSymbol() {
  return symbol;
}

public void setSymbol(String symbol) {
  this.symbol = symbol;
}

@Override
public String toString() {
  return "Candle [close=" + close + ", date=" + date + ", symbol=" + symbol + "]";
}


   
}

