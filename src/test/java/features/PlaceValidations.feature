Feature: Validating Place APIs

@AddPlace
Scenario: Validate place is being successfully added
	Given Add Place Payload "<name>""<language>""<address>"
	When User calls "AddPlaceAPI" with "POST" http request
	Then The API call is success with status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And verify place_Id created maps to "<name>" using "getPlaceAPI"
	
	
Examples:
	|name 	 | language |address		   |
	|AAhouse |  English |World cross center|
	#|BBhouse | Spanish  |Sea cross center  |
	
@DeletePlace
Scenario: Verify if Delete Place functionality is working
	Given DeletePlace Payload
	When User calls "deletePlaceAPI" with "POST" http request
	Then The API call is success with status code 200
	And "status" in response body is "OK"
	
	


