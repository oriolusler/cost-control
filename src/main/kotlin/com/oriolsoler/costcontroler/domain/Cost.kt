package com.oriolsoler.costcontroler.domain

import com.oriolsoler.costcontroler.NoArgAnnotation
import com.oriolsoler.costcontroler.domain.Subcategorises.ACTIVITIES
import com.oriolsoler.costcontroler.domain.Subcategorises.AIR_TRAVEL
import com.oriolsoler.costcontroler.domain.Subcategorises.BANK_FEE
import com.oriolsoler.costcontroler.domain.Subcategorises.BIKE_RENT
import com.oriolsoler.costcontroler.domain.Subcategorises.BOOKS
import com.oriolsoler.costcontroler.domain.Subcategorises.CAR_RENT
import com.oriolsoler.costcontroler.domain.Subcategorises.CINEMA
import com.oriolsoler.costcontroler.domain.Subcategorises.CLOTHES_AND_SHOES
import com.oriolsoler.costcontroler.domain.Subcategorises.DEPOSIT
import com.oriolsoler.costcontroler.domain.Subcategorises.DOCUMENTATION
import com.oriolsoler.costcontroler.domain.Subcategorises.ELECTRICITY
import com.oriolsoler.costcontroler.domain.Subcategorises.ELECTRONICS_AND_SOFTWARE
import com.oriolsoler.costcontroler.domain.Subcategorises.ESTABLISHMENT
import com.oriolsoler.costcontroler.domain.Subcategorises.EVENTS
import com.oriolsoler.costcontroler.domain.Subcategorises.FOOD_DELIVERY
import com.oriolsoler.costcontroler.domain.Subcategorises.FURNITURE_DECORATION
import com.oriolsoler.costcontroler.domain.Subcategorises.GAS
import com.oriolsoler.costcontroler.domain.Subcategorises.GAS_FUEL
import com.oriolsoler.costcontroler.domain.Subcategorises.GIFTS
import com.oriolsoler.costcontroler.domain.Subcategorises.GROCERIES
import com.oriolsoler.costcontroler.domain.Subcategorises.GYM
import com.oriolsoler.costcontroler.domain.Subcategorises.HAIRDRESSER
import com.oriolsoler.costcontroler.domain.Subcategorises.HOBBIES
import com.oriolsoler.costcontroler.domain.Subcategorises.HOTEL
import com.oriolsoler.costcontroler.domain.Subcategorises.HOUSEHOLD_APPLIANCE
import com.oriolsoler.costcontroler.domain.Subcategorises.HOUSE_FEE
import com.oriolsoler.costcontroler.domain.Subcategorises.INSURANCE
import com.oriolsoler.costcontroler.domain.Subcategorises.INTERNET
import com.oriolsoler.costcontroler.domain.Subcategorises.MOTO_RENT
import com.oriolsoler.costcontroler.domain.Subcategorises.PARKING
import com.oriolsoler.costcontroler.domain.Subcategorises.PAYBACK
import com.oriolsoler.costcontroler.domain.Subcategorises.PAYCHECK
import com.oriolsoler.costcontroler.domain.Subcategorises.RENT
import com.oriolsoler.costcontroler.domain.Subcategorises.RENTAL_CAR_AND_TAXI
import com.oriolsoler.costcontroler.domain.Subcategorises.REPLACEMENT
import com.oriolsoler.costcontroler.domain.Subcategorises.RESTAURANTS
import com.oriolsoler.costcontroler.domain.Subcategorises.RETURNED_PURCHASE
import com.oriolsoler.costcontroler.domain.Subcategorises.ROBOADVISOR
import com.oriolsoler.costcontroler.domain.Subcategorises.SPORTING_GOODS
import com.oriolsoler.costcontroler.domain.Subcategorises.SPORTS
import com.oriolsoler.costcontroler.domain.Subcategorises.STATE_TAX
import com.oriolsoler.costcontroler.domain.Subcategorises.STREAMING_SERVICES
import com.oriolsoler.costcontroler.domain.Subcategorises.SUBSCRIPTIONS
import com.oriolsoler.costcontroler.domain.Subcategorises.VIDEO_GAMES
import com.oriolsoler.costcontroler.domain.Subcategorises.WATER
import java.math.BigDecimal
import java.time.LocalDate

@NoArgAnnotation
data class Cost(
    val date: LocalDate?,
    val description: Description?,
    val category: Categories?,
    val subcategory: Subcategorises?,
    val comment: String?,
    val amount: BigDecimal?,
    val username: String?,
    val shared: List<SharedCost>? = ArrayList(20)
)

@NoArgAnnotation
data class SharedCost(
    var amount: BigDecimal?,
    var isPaid: Boolean?,
    var debtor: String?
)

data class Description(val value: String)

enum class Categories(val displayName: String, val subtypes: List<Subcategorises>) {
    INCOME("Income", listOf(PAYCHECK, RETURNED_PURCHASE, PAYBACK)),
    SHOPPING(
        "Shopping", listOf(
            BOOKS,
            ELECTRONICS_AND_SOFTWARE,
            HOBBIES,
            SPORTING_GOODS,
            HOUSEHOLD_APPLIANCE,
            CLOTHES_AND_SHOES,
            FURNITURE_DECORATION,
            REPLACEMENT
        )
    ),
    EDUCATION("Education", listOf(BOOKS, EVENTS)),
    TRAVEL("Travel", listOf(AIR_TRAVEL, HOTEL, RENTAL_CAR_AND_TAXI)),
    TRANSPORT("Transport", listOf(GAS_FUEL, BIKE_RENT, MOTO_RENT, CAR_RENT, PARKING)),
    HOUSING("Housing", listOf(RENT, WATER, ELECTRICITY, GAS, INTERNET, INSURANCE, DEPOSIT)),
    TAXES_AND_FEES("Taxes and fees", listOf(STATE_TAX, BANK_FEE, HOUSE_FEE, DOCUMENTATION)),
    FOOD("Food", listOf(GROCERIES, RESTAURANTS, FOOD_DELIVERY, ESTABLISHMENT)),
    PERSONAL_SPENDING("Personal spending", listOf(GYM, SPORTS, GIFTS, HAIRDRESSER)),
    RECREATION_ENTERTAINMENT(
        "Recreation and entertainment",
        listOf(STREAMING_SERVICES, ACTIVITIES, VIDEO_GAMES, CINEMA, SUBSCRIPTIONS)
    ),
    INVESTMENTS("Investments", listOf(ROBOADVISOR)),
    CASH("Cash", listOf())
}

enum class Subcategorises(val displayName: String) {
    PAYCHECK("Paycheck"), RETURNED_PURCHASE("Returned purchase"), PAYBACK("Payback"), BOOKS("Books"), ELECTRONICS_AND_SOFTWARE(
        "Electronics and software"
    ),
    HOBBIES("Hobbies"), SPORTING_GOODS("Sporting goods"), HOUSEHOLD_APPLIANCE("Household appliance"), REPLACEMENT("Replacement"),
    EVENTS("Events"),
    AIR_TRAVEL("Air travel"), HOTEL("Hotel"), RENTAL_CAR_AND_TAXI("Rental car and taxi"),
    GAS_FUEL("Gas fuel"), BIKE_RENT("Bike rent"), MOTO_RENT("Moto rent"), CAR_RENT("Car rent"), PARKING("Parking"),
    RENT("Rent"), WATER("Water"), ELECTRICITY("Electricity"), GAS("Gas"), INTERNET("Internet"), INSURANCE("Insurance"), FURNITURE_DECORATION(
        "Furniture decoration"
    ),
    DEPOSIT("Deposit"),
    STATE_TAX("State tax"), BANK_FEE("Bank fee"), HOUSE_FEE("House fee"), DOCUMENTATION("Documentation"),
    GROCERIES("Groceries"), RESTAURANTS("Restaurants"), FOOD_DELIVERY("Food delivery"), ESTABLISHMENT("Establishment"),
    GYM("Gym"), SPORTS("Sports"), GIFTS("Gifts"), CLOTHES_AND_SHOES("Clothes and shoes"), HAIRDRESSER("Hairdresser"),
    STREAMING_SERVICES("Streaming services"), ACTIVITIES("Activities"), VIDEO_GAMES("Video games"), CINEMA("Cinema"), SUBSCRIPTIONS(
        "Subscription"
    ),
    ROBOADVISOR("Roboadvisor")
}
