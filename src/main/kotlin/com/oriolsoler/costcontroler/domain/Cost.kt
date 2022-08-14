package com.oriolsoler.costcontroler.domain

import com.oriolsoler.costcontroler.domain.CostSubCategorises.ACTIVITIES
import com.oriolsoler.costcontroler.domain.CostSubCategorises.AIR_TRAVEL
import com.oriolsoler.costcontroler.domain.CostSubCategorises.BANK_FEE
import com.oriolsoler.costcontroler.domain.CostSubCategorises.BIKE_RENT
import com.oriolsoler.costcontroler.domain.CostSubCategorises.BOOKS
import com.oriolsoler.costcontroler.domain.CostSubCategorises.CAR_RENT
import com.oriolsoler.costcontroler.domain.CostSubCategorises.CINEMA
import com.oriolsoler.costcontroler.domain.CostSubCategorises.CLOTHES_AND_SHOES
import com.oriolsoler.costcontroler.domain.CostSubCategorises.CLOTHING
import com.oriolsoler.costcontroler.domain.CostSubCategorises.DEPOSIT
import com.oriolsoler.costcontroler.domain.CostSubCategorises.DOCUMENTATION
import com.oriolsoler.costcontroler.domain.CostSubCategorises.ELECTRICITY
import com.oriolsoler.costcontroler.domain.CostSubCategorises.ELECTRONICS_AND_SOFTWARE
import com.oriolsoler.costcontroler.domain.CostSubCategorises.ESTABLISHMENT
import com.oriolsoler.costcontroler.domain.CostSubCategorises.EVENTS
import com.oriolsoler.costcontroler.domain.CostSubCategorises.FOOD_DELIVERY
import com.oriolsoler.costcontroler.domain.CostSubCategorises.FURNITURE_DECORATION
import com.oriolsoler.costcontroler.domain.CostSubCategorises.GAS
import com.oriolsoler.costcontroler.domain.CostSubCategorises.GAS_FUEL
import com.oriolsoler.costcontroler.domain.CostSubCategorises.GIFTS
import com.oriolsoler.costcontroler.domain.CostSubCategorises.GROCERIES
import com.oriolsoler.costcontroler.domain.CostSubCategorises.GYM
import com.oriolsoler.costcontroler.domain.CostSubCategorises.HAIRDRESSER
import com.oriolsoler.costcontroler.domain.CostSubCategorises.HOBBIES
import com.oriolsoler.costcontroler.domain.CostSubCategorises.HOTEL
import com.oriolsoler.costcontroler.domain.CostSubCategorises.HOUSEHOLD_APPLIANCE
import com.oriolsoler.costcontroler.domain.CostSubCategorises.HOUSE_FEE
import com.oriolsoler.costcontroler.domain.CostSubCategorises.INSURANCE
import com.oriolsoler.costcontroler.domain.CostSubCategorises.INTERNET
import com.oriolsoler.costcontroler.domain.CostSubCategorises.MOTO_RENT
import com.oriolsoler.costcontroler.domain.CostSubCategorises.PARKING
import com.oriolsoler.costcontroler.domain.CostSubCategorises.PAYBACK
import com.oriolsoler.costcontroler.domain.CostSubCategorises.PAYCHECK
import com.oriolsoler.costcontroler.domain.CostSubCategorises.RENT
import com.oriolsoler.costcontroler.domain.CostSubCategorises.RENTAL_CAR_AND_TAXI
import com.oriolsoler.costcontroler.domain.CostSubCategorises.REPLACEMENT
import com.oriolsoler.costcontroler.domain.CostSubCategorises.RESTAURANTS
import com.oriolsoler.costcontroler.domain.CostSubCategorises.RETURNED_PURCHASE
import com.oriolsoler.costcontroler.domain.CostSubCategorises.ROBOADVISOR
import com.oriolsoler.costcontroler.domain.CostSubCategorises.SPORTING_GOODS
import com.oriolsoler.costcontroler.domain.CostSubCategorises.SPORTS
import com.oriolsoler.costcontroler.domain.CostSubCategorises.STATE_TAX
import com.oriolsoler.costcontroler.domain.CostSubCategorises.STREAMING_SERVICES
import com.oriolsoler.costcontroler.domain.CostSubCategorises.SUBSCRIPTIONS
import com.oriolsoler.costcontroler.domain.CostSubCategorises.VIDEO_GAMES
import com.oriolsoler.costcontroler.domain.CostSubCategorises.WATER
import java.time.LocalDate

data class Cost(
    val date: LocalDate,
    val description: Description,
    val category: String,
    val subcategory: String,
    val comment: String,
    val amount: Double,
    val username: String
)

data class Description(val value: String)

enum class CostCategories(subtypes: List<CostSubCategorises>) {
    INCOME(listOf(PAYCHECK, RETURNED_PURCHASE, PAYBACK)),
    SHOPPING(listOf(CLOTHING, BOOKS, ELECTRONICS_AND_SOFTWARE, HOBBIES, SPORTING_GOODS, HOUSEHOLD_APPLIANCE, CLOTHES_AND_SHOES, FURNITURE_DECORATION, REPLACEMENT)),
    EDUCATION(listOf(BOOKS, EVENTS)),
    TRAVEL(listOf(AIR_TRAVEL, HOTEL, RENTAL_CAR_AND_TAXI)),
    TRANSPORT(listOf(GAS_FUEL, BIKE_RENT, MOTO_RENT, CAR_RENT, PARKING)),
    HOUSING(listOf(RENT, WATER, ELECTRICITY, GAS, INTERNET, INSURANCE, DEPOSIT)),
    TAXES_AND_FEES(listOf(STATE_TAX, BANK_FEE, HOUSE_FEE, DOCUMENTATION)),
    FOOD(listOf(GROCERIES, RESTAURANTS, FOOD_DELIVERY, ESTABLISHMENT)),
    PERSONAL_SPENDING(listOf(GYM, SPORTS, GIFTS, HAIRDRESSER)),
    RECREATION_ENTERTAINMENT(listOf(STREAMING_SERVICES, ACTIVITIES, VIDEO_GAMES, CINEMA, SUBSCRIPTIONS)),
    INVESTMENTS(listOf(ROBOADVISOR)),
    CASH(listOf())
}

enum class CostSubCategorises {
    PAYCHECK, RETURNED_PURCHASE, PAYBACK,
    CLOTHING, BOOKS, ELECTRONICS_AND_SOFTWARE, HOBBIES, SPORTING_GOODS, HOUSEHOLD_APPLIANCE, REPLACEMENT,
    EVENTS,
    AIR_TRAVEL, HOTEL, RENTAL_CAR_AND_TAXI,
    GAS_FUEL, BIKE_RENT, MOTO_RENT, CAR_RENT, PARKING,
    RENT, WATER, ELECTRICITY, GAS, INTERNET, INSURANCE, FURNITURE_DECORATION, DEPOSIT,
    STATE_TAX, BANK_FEE, HOUSE_FEE, DOCUMENTATION,
    GROCERIES, RESTAURANTS, FOOD_DELIVERY, ESTABLISHMENT,
    GYM, SPORTS, GIFTS, CLOTHES_AND_SHOES, HAIRDRESSER,
    STREAMING_SERVICES, ACTIVITIES, VIDEO_GAMES, CINEMA, SUBSCRIPTIONS,
    ROBOADVISOR
}
