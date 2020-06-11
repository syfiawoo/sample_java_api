package com.callia.test.controller;

import com.callia.test.Event;
import com.callia.test.service.EventService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.callia.test.EventList.SIXTY_SECONDS;

@RestController
public class TestController {

    private EventService eventService;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    @ApiOperation(value = "Default landing page")
    public String index(){
        return "Welcome to Seth's implementation! :)";
    }

    @PostMapping(value = "/event", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    @ApiOperation(value = "Adds an order event", response = Map.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added item"),
            @ApiResponse(code = 204, message = "Event is older than sixty seconds"),
            @ApiResponse(code = 400, message = "Event does not have a valid timestamp"),
    })
    public Map<String, Object> postEvent(@RequestBody Event event){
        eventService.addEvent(event);
        Map<String, Object> response = new HashMap<>();
        long currentTime = System.currentTimeMillis();
        long inLastSixtySecs = currentTime - SIXTY_SECONDS;
        if(event.getTimestamp() > inLastSixtySecs && event.getTimestamp() <= currentTime)
            response.put("status", 201);  //event is not older than 60s
        else if (event.getTimestamp() < inLastSixtySecs)
            response.put("status", 204);  // event is more than 60s in the past
        else
            response.put("status", 400);  // event is in the future, bad request
        return response;
    }

    @GetMapping(value = "/event", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object>  getLastSixty(){
        long currentTime = System.currentTimeMillis();
        Map<String, Object> response = new HashMap<>();
        double mean = eventService.eventMeanAmount(eventService.getLastSixtySeconds(currentTime));
        response.put("average", mean);
        response.put("status", 200);
        return response;
    }

}
