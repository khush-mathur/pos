package com.increff.pos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UIController {

    @Value("${app.baseUrl}")
    private String baseUrl;

    @RequestMapping(value = "/ui/home")
    public ModelAndView home() {
        return mav("home.html");
    }

    @RequestMapping(value = "/ui/brand")
    public ModelAndView brand() {
        return mav("brand.html");
    }

    @RequestMapping(value = "/ui/product")
    public ModelAndView product() {
        return mav("product.html");
    }

    @RequestMapping(value = "/ui/order")
    public ModelAndView order() {
        return mav("orders.html");
    }

    @RequestMapping(value = "/ui/inventory")
    public ModelAndView inventory() {
        return mav("inventory.html");
    }

    @RequestMapping(value = "/ui/report")
    public ModelAndView report() {
        return mav("report.html");
    }

    @RequestMapping(value = "/ui/cart/{orderId}")
    public ModelAndView cart(@PathVariable Integer orderId) {
        return mav("cart.html");
    }

    @RequestMapping(value = "/ui/invoice-path/{orderId}")
    public ModelAndView getInvoice(@PathVariable Integer orderId) {
        return mav("invoice"+orderId+".pdf");
    }

    private ModelAndView mav(String page) {

        ModelAndView mav = new ModelAndView(page);
        mav.addObject("baseUrl", baseUrl);
        return mav;
    }
}
