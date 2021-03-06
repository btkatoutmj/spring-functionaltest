/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.fw.spring.functionaltest.app.vldt;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("vldt/0305/001")
public class VLDT0305001Controller {

    @ModelAttribute
    public DefineMessageUsingValidatedValueByValidationMessagesForm setUpForm() {
        return new DefineMessageUsingValidatedValueByValidationMessagesForm();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String handle() {
        return "vldt/defineMessageUsingValidatedValueByValidationMessagesView";
    }

    @RequestMapping(method = RequestMethod.POST, params = "validate")
    public String handleValidate(
            @Validated DefineMessageUsingValidatedValueByValidationMessagesForm form,
            BindingResult result) {

        if (result.hasErrors()) {
            return "vldt/defineMessageUsingValidatedValueByValidationMessagesView";
        }
        return "redirect:/vldt/0305/001";
    }
}
