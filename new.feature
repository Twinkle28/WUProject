Feature: create a new booking and perform CRUD operations

Background:
    * url 'https://restful-booker.herokuapp.com'
    * def requestPayload =
    """
    {
      "firstname" : "Twinkle",
      "lastname" : "Brown",
      "totalprice" : 111,
      "depositpaid" : true,
      "bookingdates" : {
            "checkin" : "2018-01-01",
            "checkout" : "2019-01-01"
      },
      "additionalneeds" : "Breakfast"
    }
    """

Scenario: Create a new booking
Given path '/booking'
And request requestPayload
And header  Content-Type = 'application/json'
When method POST
Then status 201
And match $.totalprice == '#present'
And match $.firstname == '#present'
And match $.lastname == 'Brown'
  * def id = $.bookingid
  * print id


  #Get API with Query Parameters
  Scenario: Get above booking details
    * def query = {lastname: 'Brown', id:23}
  Given url 'https://restful-booker.herokuapp.com' +'/booking/' +id
  When method GET
  Then status 200
  * print response

#PUT call to update a booking
  * def requestPayload =
    """
    {
      "firstname" : "Twinkle",
      "lastname" : "Brown",
      "totalprice" : 111,
      "depositpaid" : false,
      "bookingdates" : {
        "checkin" : "2018-01-01",
        "checkout" : "2019-01-01"
      },
      "additionalneeds" : "Breakfast"
    }

    """
Scenario: Update a booking
  Given path '/booking/'+id
  And request requestPayload
  And header  Content-Type = 'application/json'
  When method PUT
  Then status 200
  And match $.firstname== 'James'

  #PATCH call to partially update a booking
  * def requestPayload =
    """
    {
      "firstname" : "Twinkle",
      "additionalneeds" : "Cleaning"
    }
    """
  Scenario: Partial update a booking
    Given path '/booking/'+id
    And request requestPayload
    And header  Content-Type = 'application/json'
    And configure ssl = true
    When method PATCH
    Then status 200
    And match $.firstname== 'Twinkle'
    And match $.additionalneeds== 'Cleaning'

  #DELETE same booking
  Scenario: Delete a booking
    Given path '/booking/'+id
    And header  Content-Type = 'application/json'
    When method DELETE
    Then status 204

    #Get the same booking with bookingid
    Given url 'https://restful-booker.herokuapp.com' +'/booking/' +id
    When method GET
    Then status 404
    And match $.data.message == 'Resource not found'

