# Setup:



1- Add database credintials into application.yml:

datasource:

    url: jdbc:mysql://localhost:3306/your-db-name
    username: db_user_name
    password: db_pass
    driver-class-name: com.mysql.cj.jdbc.Driver

# APIs:


*1-* Add new Land api (used for create new land) -> **POST /api/lands**
request body:

    {
        "location": "land"
    }

response body:


    {
    "id": 12,
    "location": "land 15",
    "plots": null
	}



*2-* Add new plot api -> **POST api/lands/{land_id}/plots**
request body:



    {
    "name" : "name",
    }

response body:

	{
    "id": 9,
    "name": "New Plot",
    "timeSlots": null,
    "sensor": null
	}

(after create new plot we need to configure this plot)



*3-* Configure a plot api -> **POST api/plots/{plot_id}/configure**
request body:


    {
	    "slots" : [
	        {
	            "startTime" : 1670803200000,
	            "waterAmount" : 1,
	            "duration" : 10
	        }
	     ]
     }

response body:


    {
    "id": 9,
    "name": "New Plot",
    "timeSlots": [
        {
            "id": 15,
            "startTime": "2022-12-12T00:00:00.000+00:00",
            "duration": 15,
            "waterAmount": 14.0,
            "status": "PENDING"
        }
    ],
    "sensor": null
	}

*4-* Get all plots and it's details api **GET /api/plots**
response body:

    [
    {
        "id": 7,
        "name": "name",
        "timeSlots": [
            {
                "id": 14,
                "startTime": "2022-10-13T22:30:00.000+00:00",
                "duration": 10,
                "waterAmount": 1.0,
                "status": "DONE"
            }
        ],
        "sensor": {
            "id": 7,
            "deviceId": "device two",
            "url": "url"
        }
    },
    {
        "id": 8,
        "name": "name",
        "timeSlots": [
            {
                "id": 12,
                "startTime": "2022-12-13T05:30:01.000+00:00",
                "duration": 18,
                "waterAmount": 8.0,
                "status": "PENDING"
            },
            {
                "id": 11,
                "startTime": "2022-10-13T22:28:00.000+00:00",
                "duration": 10,
                "waterAmount": 1.0,
                "status": "DONE"
            }
        ],
        "sensor": {
            "id": 10,
            "deviceId": "device tweoi",
            "url": "jfgwekrtregfuwke"
        }
    }
	]



*5-* Add new device (sensor) api **POST /api/plots/{plot_id/device** pass into body request:

   	{
	    "deviceId": "device one",
	    "url" : "device_url"
	}

**The Irrigation Tasks which do the following:**

1. Retrieve the ready time slots which are between now and 10 minutes before and change their status from pending to processing.

2. Next we need to call or trigger all devices to these timeslots which configured before to specific plots and pass to them (the amount of water & the duration also).

3. Then we need to check if the sensor responded successfully or not if responded successfully then we need to change the timeslot status to be done. If not, we will change it to be error. And in this case, we should call the sensor again (3 times).

7. After 3 times (Can be configured) of trying to call the sensor and it not responding then we need to alert the system.