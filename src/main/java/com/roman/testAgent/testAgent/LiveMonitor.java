package com.roman.testAgent.testAgent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//import java.net.http.HttpHeaders;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LiveMonitor {
@RequestMapping("/ip")
    public String getData(HttpServletRequest request){

        String ip = request.getRemoteAddr();
    System.out.println("----------------" + ip) ;
        return ip;
    }
  @RequestMapping("/url")
  public String testHPlainJavaCall(){
      URL url = null;
      String response = null;
      HttpURLConnection con = null;
      try {
          url = new URL("http://192.168.56.1:5000/agent");
          //url = new URL("http://localhost:5000/agent");
          con = (HttpURLConnection) url.openConnection();
          con.setRequestMethod("GET");

          con.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

          BufferedReader in = new BufferedReader(
                  new InputStreamReader(con.getInputStream()));
          String inputLine;
          StringBuffer content = new StringBuffer();
          while ((inputLine = in.readLine()) != null) {
              content.append(inputLine);
          }
          in.close();
          response =  content.toString();
          return response;

      } catch (IOException e) {
          e.printStackTrace();
          return "£exception";
      }

  }

    @RequestMapping("/do")
   public String doWork() {
       String response;
       ChromeOptions chromeOptions = new ChromeOptions();

      Proxy proxy = new org.openqa.selenium.Proxy();
      proxy.setSslProxy("shtek:YvJ9b@uTV9AZ@Xs@us-wa.proxymesh.com:31280");
      proxy.setHttpProxy("shtek:YvJ9b@uTV9AZ@Xs@us-wa.proxymesh.com:31280");
     //proxy.setHttpProxy("shtek:YvJ9b@us-wa.proxymesh.com:31280");
     chromeOptions.setCapability("proxy", proxy);
//      chromeOptions.setCapability("user-agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2919.83 Safari/537.36");
       chromeOptions.addArguments("user-agent=\"Mozilla/5.0 (X11; Ubuntu; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2919.83 Safari/537.36\"");
        chromeOptions.addArguments("--headless", "--window-size=1920,1200");
       WebDriver driver = new ChromeDriver(chromeOptions);

       driver.manage().deleteAllCookies();
      //String url = "https://uk.louisvuitton.com/eng-gb/products/onthego-mm-bicolor-monogram-empreinte-leather-nvprod2400040v";
       String url = "http://192.168.56.1:5000/ip";
       driver.get(url);

       //By by = new By.ByClassName("lv-stock-indicator");

       //WebElement webElement = driver.findElement(by);

       //response = webElement.getText();
       //System.out.println("response" + response);
       driver.close();
       //return response;
        return "ff";
   }
    @GetMapping("/agent")
    public String newOrder(@RequestHeader(value = "User-Agent") String userAgent, RedirectAttributes redirectAttributes, Model model) {
    System.out.println("------>" +userAgent);
    return userAgent;

    }
    @GetMapping("/all-cookies")
    public String readAllCookies(HttpServletRequest request) {

        javax.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .map(c -> c.getName() + "=" + c.getValue()).collect(Collectors.joining(", "));
        }

        return "No cookies";
    }

}

