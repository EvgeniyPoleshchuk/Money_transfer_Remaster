package ru.netology.model;





public record CreditCard(String cardFromNumber,
                         String cardFromValidTill,
                         String cardFromCVV,
                         String cardToNumber,
                         Amount amount) {
}
