package com.avito.avitoweatherforecast.utils

//Наименование ключа API
const val YANDEX_API_KEY_NAME = "X-Yandex-API-Key"

//Колличество дней для запроса прогноза погоды (1-7)
const val FORECAST_DAYS_NUM = 7

//Константы направления ветра
const val DIRECTION_NORTH = "n"
const val DIRECTION_SOUTH = "s"
const val DIRECTION_WEST = "w"
const val DIRECTION_EAST = "e"
const val DIRECTION_NORTH_WEST = "nw"
const val DIRECTION_NORTH_EAST = "ne"
const val DIRECTION_SOUTH_WEST = "sw"
const val DIRECTION_SOUTH_EAST = "se"

//Константы для Permission
const val REQUEST_CODE_GEOLOCATION = 111

//Константа задержки появления текста в приветственных фрагментах
const val VISIBLE_DELAY = 2000L

//Константа для хранения флага первого запуска приложения
const val INITIALIZATION = "init"
const val FIRST_ACTIVE = "first_active"

/**
 * Группа констант для фрагмента "Настройки"
 */
const val PREF_SETTINGS = "PREF_SETTINGS"
//Настройки темы приложения
const val PREF_THEME_INT = "PREF_THEME_INT"
const val THEME_LIGHT = 0
const val THEME_DARK = 1
//Настройки первого отображаемого города
const val PREF_SETTINGS_LAST_CITY = "Last_City"
const val PREF_SETTINGS_FAVORITE_CITY = "Favorite_City"
const val PREF_SETTINGS_DEFAULT_CITY = "Moscow"

//константы для работы боковой навигации
const val TAG_FRAGMENT_ABOUT = "About_Fragment"
const val TAG_FRAGMENT_SETTINGS = "Settings_Fragment"