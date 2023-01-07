package org.example.application.monsterTradingCards.service;

import org.example.application.monsterTradingCards.model.Card;
import org.example.application.monsterTradingCards.model.Trading;
import org.example.application.monsterTradingCards.model.User;
import org.example.application.monsterTradingCards.repository.CardRepository;
import org.example.application.monsterTradingCards.repository.TradingsRepository;

public class TradeService {
    public static String checkValidTrade(Trading trading, User user, Card card) {
        // Find card + info of trader e.g. kienboec (1cb6ab86-bdb2-47e5-b6e4-68c5ab389334)
        Card checkCardHolder = CardRepository.findById(trading.getCardToTrade());
        // Find trader of current trading id path (6cd85277-4590-49d4-b0cf-ba0a921faad0)
        String checkTrader = TradingsRepository.findTrader(trading.getId());

        // trading with yourself is not possible
        assert checkCardHolder != null;
        if(!card.getCardHolder().equals(checkTrader)) {
            // check if card of customer meets requirements of trade deal
            if(card.getCategory().category.equalsIgnoreCase(trading.getType()) && card.getDamage() >= trading.getMinimumDamage()) {
                // update card of kienboec to altenhofs card
                CardRepository.updateOwner(user.getUsername(), checkCardHolder.getId());
                // update card of altenhof to kienboec card
                CardRepository.updateOwner(checkTrader, card.getId());
                // trading deal is successful -> delete trading deal
                TradingsRepository.delete(trading, checkTrader);
                return "Trade successful!";
            } else { return "Card does not meet requirements!"; }
        }
        return "Trade failed!";
    }
}
