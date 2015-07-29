[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/mkuerbis/chromecast-kiosk/trend.png)](https://bitdeli.com/free "Bitdeli Badge")
# Chromecast-Kiosk
A system to create a digital signage system with google chromecasts. 

![screenshot](https://raw.githubusercontent.com/mkuerbis/Chromecast-Kiosk/master/img/overview-screenshot.png "screenshot")

### release state
alpha

## Recommendations
* Use ethernet instead of wlan, see [DIY Ethernet Adapter](https://productforums.google.com/forum/#!topic/chromecast/xo_NDh5CZA8).
* Set a static ip address to the chromecast.
* Use subneting to protect your casts from unwanted access.
* use Google Chrome and the Google Cast (beta) extension

## Benefits
* see what your casts are doing
* set the website which shall be displayed based on [chromecast-dashboard](https://github.com/boombatower/chromecast-dashboard)
* you don't need a system which runs chrome all the time, because if you set the stream it runs alone independent of the browser
* you can manage multiple chromecast in one browser

## Installation
All you need todo is deploy the presenter.war in the webapps directory of a Tomcat 8.0.

## Start casting
1. add a ChromeCast, by IP and Name
2. set the url and the refresh rate of the website you want to be displayed (requires Google Chrome with Cast Ext.)
3. see if they are still alive an playing

## Thanks to
* [DIY Ethernet Adapter](https://productforums.google.com/forum/#!topic/chromecast/xo_NDh5CZA8)
* ["C" is for Chromecast: hacking digital signage](http://labs.cooperhewitt.org/2013/c-is-for-chromecast-hacking-digital-signage/)
* [ChromeCast Java API v2](https://github.com/vitalidze/chromecast-java-api-v2)
* [chromecast-dashboard](https://github.com/boombatower/chromecast-dashboard)
