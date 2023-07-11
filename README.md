# Tomorrow
The Weather application has the following API endpoint:

GET /weather-conditions:
    
    params:
        1. location(required): geo location
        2. rule(required): set of rules for the following params:
            i. temperature
            ii. humidity
            iii. windSpeed
            iv. rainIntensity
        3. operator(optional): receives "AND" or "OR".
            Only uppercase version ("and" will not work).
            If empty defaults to "AND".

How to run the application:
1. You will need Java 17 and maven.
2. Run: mvn clean install
3. Run: mvn spring-boot:run
   * this will start your server
4. In another terminal run ```curl http://localhost:8080/weather-conditions?location=40.7,-73.9&rule=temperature%3E20,windSpeed%3C4&operator=AND```
5. Or you can try to run it in postman or your browser.


Example Response:
```json
{
"status": "success",
"data": {
    "timeline": [
        {
          "startTime": "2023-07-11T16:00:00Z",
          "endTime": "2023-07-12T19:00:00Z",
          "condition_met": true
        },
        {
          "startTime": "2023-07-12T19:00:00Z",
          "endTime": "2023-07-13T01:00:00Z",
          "condition_met": false
        },
        {
          "startTime": "2023-07-13T01:00:00Z",
          "endTime": "2023-07-13T15:00:00Z",
          "condition_met": true
        },
        {
          "startTime": "2023-07-13T15:00:00Z",
          "endTime": "2023-07-14T08:00:00Z",
          "condition_met": false
        },
        {
          "startTime": "2023-07-14T08:00:00Z",
          "endTime": "2023-07-14T09:00:00Z",
          "condition_met": true
        }
    ]
  }
}
```