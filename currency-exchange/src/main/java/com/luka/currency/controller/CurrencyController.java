package com.luka.currency.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luka.currency.service.CurrencyService;


@Controller
public class CurrencyController {

    private final CurrencyService currencyService;

    private static boolean isNumber(String input) {
        try {
            // Attempt to parse the input as an integer
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            // If parsing fails, it's not an integer
            return false;
        }
    }

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/")
    public String getQuotesPage(
        final Model model,
        @RequestParam(value = "q", required = false) String amount,
        @RequestParam(value = "from", required = false) String fromCurrency,
        @RequestParam(value = "to", required = false) String toCurrency
        ) throws IOException {
        String quotestr = null;
        System.out.println("Amount="+amount);

        if (amount != null && fromCurrency != null && toCurrency != null) {
            //System.out.println("AmountInt="+ Integer.toString(Integer.valueOf(amount)));
            if (!isNumber(amount)) amount="1";
            quotestr = currencyService.getQuote(fromCurrency, toCurrency, amount);
            double d = Double.parseDouble(quotestr);
            if (amount.length() == 0) amount="1";
            double a = Double.parseDouble(amount);
            //quotestr = Double.toString(d*a);
            quotestr = String.format("%.2f", d*a);
        }
        final List<String> quotes = currencyService.listQuotes();
        model.addAttribute("quotes", quotes);
        model.addAttribute("quote", quotestr);
        model.addAttribute("from", fromCurrency);
        model.addAttribute("to", toCurrency);
        model.addAttribute("amount", amount);
        return "index";
    }
}
