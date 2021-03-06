/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.fw.spring.functionaltest.app.stpr;

import java.text.Normalizer;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("stpr")
@Controller
public class STPR04Controller {

    @ModelAttribute
    public StringProcessing04Form setStringProcessing04Form() {
        return new StringProcessing04Form();
    }

    @RequestMapping(value = "04", method = RequestMethod.GET)
    public String handle0401001(Model model) {
        Map<String, String> normalizationFormMap = new LinkedHashMap<String, String>();
        for (Normalizer.Form form : Normalizer.Form.values()) {
            normalizationFormMap.put(form.toString(), form.toString());
        }
        model.addAttribute("normalizationFormMap", normalizationFormMap);
        return "stpr/normalizerInput";
    }

    @RequestMapping(value = "normalizer", method = RequestMethod.POST, params = { "normalizationForm=NFD" })
    public String handle0401001Normalizer(Model model,
            StringProcessing04Form form) {

        setNormalizationAttribute(model, form.getTargetValue(),
                Normalizer.Form.NFD);

        return "stpr/normalizerResult";
    }

    @RequestMapping(value = "normalizer", method = RequestMethod.POST, params = { "normalizationForm=NFC" })
    public String handle0402001Normalizer(Model model,
            StringProcessing04Form form) {

        setNormalizationAttribute(model, form.getTargetValue(),
                Normalizer.Form.NFC);

        return "stpr/normalizerResult";
    }

    @RequestMapping(value = "normalizer", method = RequestMethod.POST, params = { "normalizationForm=NFKD" })
    public String handle0403001Normalizer(Model model,
            StringProcessing04Form form) {

        setNormalizationAttribute(model, form.getTargetValue(),
                Normalizer.Form.NFKD);

        return "stpr/normalizerResult";
    }

    @RequestMapping(value = "normalizer", method = RequestMethod.POST, params = { "normalizationForm=NFKC" })
    public String handle0404001Normalizer(Model model,
            StringProcessing04Form form) {

        setNormalizationAttribute(model, form.getTargetValue(),
                Normalizer.Form.NFKC);

        return "stpr/normalizerResult";
    }

    private void setNormalizationAttribute(Model model, String targetValue,
            Normalizer.Form normalizationForm) {
        model.addAttribute("normalizationForm", normalizationForm.name());
        model.addAttribute("resultString", Normalizer.normalize(targetValue,
                normalizationForm));
    }

}
