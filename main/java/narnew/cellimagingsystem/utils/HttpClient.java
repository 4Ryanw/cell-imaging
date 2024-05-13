package narnew.cellimagingsystem.utils;

/**
 * @Author: WenRan
 * @Date: 2022/3/9
 * @description:
 */



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class HttpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);
    //POST  有参无参
    public static String sendPOSTRequest(String url, MultiValueMap<String, String> params) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        //  执行HTTP请求
        ResponseEntity<String> response = client.exchange(url,  HttpMethod.POST, requestEntity, String.class);
        return response.getBody();
    }
    //GET  有参无参
    public static String sendGETRequest(String url, MultiValueMap<String, String> params) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //  执行HTTP请求
        ResponseEntity<String> response = client.exchange(url,  HttpMethod.GET, requestEntity, String.class);
        return response.getBody();
    }
    //POST  有参无参  加请求头
    public static String sendPOSTRequest(String url, MultiValueMap<String, String> params,  HttpHeaders headers) {
        RestTemplate client = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        //  执行HTTP请求
        ResponseEntity<String> response = client.exchange(url,  HttpMethod.POST, requestEntity, String.class);
        return response.getBody();
    }
    //GET   有参无参 加请求头
    public static String sendGETRequest(String url, MultiValueMap<String, String> params,HttpHeaders headers) {
        RestTemplate client = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //  执行HTTP请求
        ResponseEntity<String> response = client.exchange(url,  HttpMethod.GET, requestEntity, String.class);
        return response.getBody();
    }




}


