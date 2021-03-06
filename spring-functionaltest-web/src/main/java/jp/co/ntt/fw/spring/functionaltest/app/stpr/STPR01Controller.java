/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.fw.spring.functionaltest.app.stpr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("stpr")
@Controller
public class STPR01Controller {

    @ModelAttribute
    public StringProcessing01Form setStringProcessing01Form() {
        return new StringProcessing01Form();
    }

    @RequestMapping(value = "0101/001", method = RequestMethod.GET)
    public String handle0101001() {
        return "stpr/trimWhitespaceInput";
    }

    @RequestMapping(value = "0101/001/trimWhitespace", method = RequestMethod.POST)
    public String handle0101001TrimWhitespace(Model model,
            StringProcessing01Form form) {

        model.addAttribute("resultString", StringUtils.trimWhitespace(form
                .getTargetText()));

        return "stpr/trimWhitespaceResult";
    }

    @RequestMapping(value = "0101/002", method = RequestMethod.GET)
    public String handle0101002() {
        return "stpr/trimLeadingCharacterInput";
    }

    @RequestMapping(value = "0101/002/trimLeadingCharacter", method = RequestMethod.POST)
    public String handle0101002TrimLeadingCharacter(Model model,
            @Validated StringProcessing01Form form, BindingResult result) {

        if (result.hasErrors()) {
            return handle0101002();
        }
        model.addAttribute("resultString", StringUtils.trimLeadingCharacter(
                form.getTargetText(), form.getTrimText().charAt(0)));

        return "stpr/trimLeadingCharacterResult";
    }

    @RequestMapping(value = "0101/003", method = RequestMethod.GET)
    public String handle0101003() {
        return "stpr/trimTrailingCharacterInput";
    }

    @RequestMapping(value = "0101/003/trimTrailingCharacter", method = RequestMethod.POST)
    public String handle0101003TrimTrailingCharacter(Model model,
            @Validated StringProcessing01Form form, BindingResult result) {

        if (result.hasErrors()) {
            return handle0101003();
        }
        model.addAttribute("resultString", StringUtils.trimTrailingCharacter(
                form.getTargetText(), form.getTrimText().charAt(0)));

        return "stpr/trimTrailingCharacterResult";
    }

}
