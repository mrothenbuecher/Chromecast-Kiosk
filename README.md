# Chromecast-Kiosk
A system to create a digital signage system with google chromecasts. 

![screenshot](https://raw.githubusercontent.com/mrothenbuecher/Chromecast-Kiosk/master/img/overview-screenshot.png "screenshot")

### release state
beta

## requirements
* fixed ip address for each chromecast 

## recommendations
* Use ethernet instead of wlan, see [DIY Ethernet Adapter](https://productforums.google.com/forum/#!topic/chromecast/xo_NDh5CZA8) or the offical adapter.
* Set a static ip address to the chromecast.
* Use subneting to protect your casts from unwanted access.

## benefits
* see what your casts are doing
* discover the chromecast in your network
* set the website which shall be displayed on your chromecast based on [chromecast-dashboard](https://github.com/boombatower/chromecast-dashboard)
* you can manage multiple chromecast in one browser
* cronjobs so you could plan what your casts should do
* multi lang support

## installation
All you need todo is deploy the *.war - File in the webapps directory of a Tomcat 8.0.

## upgrading
1. Copy the `*.json` files from WEB-INF/config as backup.
2. use the Tomcat manager to undeploy the current webapp and deploy the new one
3. copy the json files back to WEB-INF/config

## start casting
1. add a ChromeCast, by IP and Name
2. set target chromecast, the url and the refresh rate of the website you want to be displayed (for internal sides use the IP of the server not his name)
3. see if they are still alive an playing

## tips for using with google slides
Example URL `https://docs.google.com/presentation/.../embed?start=true&loop=true&delayms=10000&rm=minimal`
* start=true - start after loading
* loop=true - loop the presentation
* delayms=10000 - wait 10.000 ms between slides
* rm=minimal - show minimal interface (don't show slidenumber etc.)


## Thanks to
* [DIY Ethernet Adapter](https://productforums.google.com/forum/#!topic/chromecast/xo_NDh5CZA8)
* ["C" is for Chromecast: hacking digital signage](http://labs.cooperhewitt.org/2013/c-is-for-chromecast-hacking-digital-signage/)
* [ChromeCast Java API v2](https://github.com/vitalidze/chromecast-java-api-v2)
* [chromecast-dashboard](https://github.com/boombatower/chromecast-dashboard)
