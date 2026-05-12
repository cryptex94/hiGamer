package com.coding.higamerapp.common.util

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.coding.higamerapp.R

object Constants {

    const val BASE_URL = "https://higamerapp.link:8080/"
    const val QUERY_PAGE_SIZE = 12
    const val mInterval = 120000L

    //DATASTORE
    val PROFILE_NAME = stringPreferencesKey("PROFILE_NAME")
    val PROFILE_ROLE = intPreferencesKey("PROFILE_ROLE")
    val PROFILE_TIER = intPreferencesKey("PROFILE_TIER")
    val PROFILE_SERVER = intPreferencesKey("PROFILE_SERVER")
    val PROFILE_TEAM = booleanPreferencesKey("PROFILE_TEAM")
    val PROFILE_AVATAR = intPreferencesKey("PROFILE_AVATAR")
    val PROFILE_LANGUAGE = stringPreferencesKey("PROFILE_LANGUAGE")
    val PROFILE_CHAMPIONS = stringPreferencesKey("PROFILE_CHAMPIONS")

    val TERMS = booleanPreferencesKey("TERMS")

    //AVATAR
    val avatar = listOf(
        R.mipmap.icona_gamergirl_foreground,
        R.mipmap.icona_skater_foreground,
        R.mipmap.icona_cyber_foreground,
        R.mipmap.icona_flogger_foreground,
        R.mipmap.icona_ghotic_foreground,
        R.mipmap.icona_hiphop_foreground,
        R.mipmap.icona_furry_foreground,
        R.mipmap.icona_hacker_foreground,
        R.mipmap.icona_rastafari_foreground
    )

    val langList = listOf(
        "ENG",
        "FR",
        "SPA",
        "GER",
        "ITA",
        "PT",
        "PL",
        "RU",
        "KR",
        "CN",
        "ARA",
        ""
    )

    val serverList = listOf(
        R.string.br,
        R.string.eune,
        R.string.euw,
        R.string.jp,
        R.string.kr,
        R.string.lan,
        R.string.las,
        R.string.na,
        R.string.oce,
        R.string.ru,
        R.string.tr,
        R.string.pbe,
    )

    val roleList = listOf(
        R.string.top,
        R.string.jungle,
        R.string.mid,
        R.string.adc,
        R.string.support
    )

    val tierList = listOf(
        R.string.iron,
        R.string.bronze,
        R.string.silver,
        R.string.gold,
        R.string.platinum,
        R.string.diamond
    )

    val champions = listOf(
        "Aatrox",
        "Ahri",
        "Akali",
        "Akshan",
        "Alistar",
        "Amumu",
        "Anivia",
        "Annie",
        "Aphelios",
        "Ashe",
        "Aurelion Sol",
        "Azir",
        "Bard",
        "Blitzcrank",
        "Brand",
        "Braum",
        "Caitlyn",
        "Camille",
        "Cassiopeia",
        "Cho'Gath",
        "Corki",
        "Darius",
        "Diana",
        "Dr. Mundo",
        "Draven",
        "Ekko",
        "Elise",
        "Evelynn",
        "Ezreal",
        "Fiddlesticks",
        "Fiora",
        "Fizz",
        "Galio",
        "Gangplank",
        "Garen",
        "Gnar",
        "Gragas",
        "Graves",
        "Gwen",
        "Hecarim",
        "Heimerdinger",
        "Illaoi",
        "Irelia",
        "Ivern",
        "Janna",
        "Jarvan IV",
        "Jax",
        "Jayce",
        "Jhin",
        "Jinx",
        "Kai'Sa",
        "Kalista",
        "Karma",
        "Karthus",
        "Kassadin",
        "Katarina",
        "Kayle",
        "Kayn",
        "Kennen",
        "Kha'Zix",
        "Kindred",
        "Kled",
        "Kog'Maw",
        "LeBlanc",
        "Lee Sin",
        "Leona",
        "Lillia",
        "Lissandra",
        "Lucian",
        "Lulu",
        "Lux",
        "Malphite",
        "Malzahar",
        "Maokai",
        "Master Yi",
        "Miss Fortune",
        "Mordekaiser",
        "Morgana",
        "Nami",
        "Nasus",
        "Nautilus",
        "Neeko",
        "Nidalee",
        "Nocturne",
        "Nunu & Willump",
        "Olaf",
        "Orianna",
        "Ornn",
        "Pantheon",
        "Poppy",
        "Pyke",
        "Qiyana",
        "Quinn",
        "Rakan",
        "Rammus",
        "Rek'Sai",
        "Rell",
        "Renekton",
        "Rengar",
        "Riven",
        "Rumble",
        "Ryze",
        "Samira",
        "Sejuani",
        "Senna",
        "Seraphine",
        "Sett",
        "Shaco",
        "Shen",
        "Shyvana",
        "Singed",
        "Sion",
        "Sivir",
        "Skarner",
        "Sona",
        "Soraka",
        "Swain",
        "Sylas",
        "Syndra",
        "Tahm Kench",
        "Taliyah",
        "Talon",
        "Taric",
        "Teemo",
        "Thresh",
        "Tristana",
        "Trundle",
        "Tryndamere",
        "Twisted Fate",
        "Twitch",
        "Udyr",
        "Urgot",
        "Varus",
        "Vayne",
        "Veigar",
        "Vel'Koz",
        "Vex",
        "Vi",
        "Viego",
        "Viktor",
        "Vladimir",
        "Volibear",
        "Warwick",
        "Wukong",
        "Xayah",
        "Xerath",
        "Xin Zhao",
        "Yasuo",
        "Yone",
        "Yorick",
        "Yuumi",
        "Zac",
        "Zed",
        "Zeri",
        "Ziggs",
        "Zilean",
        "Zoe",
        "Zyra"
    )

}