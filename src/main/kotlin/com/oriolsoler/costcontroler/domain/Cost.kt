package com.oriolsoler.costcontroler.domain

import com.oriolsoler.costcontroler.NoArgAnnotation
import com.oriolsoler.costcontroler.domain.CostSubCategorises.ACTIVITIES
import com.oriolsoler.costcontroler.domain.CostSubCategorises.AIR_TRAVEL
import com.oriolsoler.costcontroler.domain.CostSubCategorises.BANK_FEE
import com.oriolsoler.costcontroler.domain.CostSubCategorises.BIKE_RENT
import com.oriolsoler.costcontroler.domain.CostSubCategorises.BOOKS
import com.oriolsoler.costcontroler.domain.CostSubCategorises.CAR_RENT
import com.oriolsoler.costcontroler.domain.CostSubCategorises.CINEMA
import com.oriolsoler.costcontroler.domain.CostSubCategorises.CLOTHES_AND_SHOES
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
import java.math.BigDecimal
import java.time.LocalDate

@NoArgAnnotation
data class Cost(
    val date: LocalDate?,
    val description: Description?,
    val category: String?,
    val subcategory: String?,
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

enum class CostCategories(val displayName: String, val subtypes: List<CostSubCategorises>) {
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

enum class CostSubCategorises(val displayName: String) {
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
