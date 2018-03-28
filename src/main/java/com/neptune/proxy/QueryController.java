package com.neptune.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class QueryController {
    private static ObjectMapper mapper = new ObjectMapper();
    
    @Value("${endpoint}")
    String endpoint = "http://your-end-point:8182";
        
    
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "", method = RequestMethod.POST,consumes ={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE},produces = "application/json")
    public ResponseEntity<Object> query(@RequestBody Map body) {
        

        try {

            String json = mapper.writeValueAsString(body);
            HttpPost postRequest = new HttpPost(endpoint);
            postRequest.setEntity(new StringEntity(json));
            postRequest.setHeader("Content-Type", "application/json");
//            request.addParameter("size", "100");
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(new HttpHost("josh-small.cnyckbjgsrik.us-east-1-beta.rds.amazonaws.com", 8182), postRequest);
            String responseString = EntityUtils.toString(response.getEntity());
            response.close();
            client.close();
            return ResponseEntity.ok(mapper.readValue(responseString, Map.class));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
