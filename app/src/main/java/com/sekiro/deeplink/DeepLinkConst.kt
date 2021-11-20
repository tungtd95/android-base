package com.sekiro.deeplink

object DeepLinkConst {
    // deepLink params
    const val PARAM_CITY_ID = "cityId"

    // deepLinks
    const val HOME = "sekiro://weather.sekiro.com"
    const val CITY_WEATHER_DETAIL = "sekiro://weather.sekiro.com/{$PARAM_CITY_ID}"
}
