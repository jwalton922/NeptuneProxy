# NeptuneProxy
This project is to help avoid CORS issues when hosting [GraphExp](https://github.com/bricaud/graphexp). To get it to work, you will need to two things.

1 Set the host variable in QueryController, compile and run

2 In GraphExp, change your graphConf host to the server running NeptuneProxy and port to 8080 (assuming defaults)

3 In GraphExp, change graphioGremlin.js run_ajax_request_with_callback function so in the $.ajax request you set the content type by adding the following line:
  * contentType:"application/json; charset=utf-8" 