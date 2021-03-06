/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.fw.spring.functionaltest.app.excn;

import javax.inject.Inject;

import jp.co.ntt.fw.spring.functionaltest.domain.model.JPAStock;
import jp.co.ntt.fw.spring.functionaltest.domain.service.djpa.JPAStockService;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.DataBaseInfo;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;

@Controller
@RequestMapping("excn/0602")
public class EXCN0602Controller {

    private static final Logger logger = LoggerFactory
            .getLogger(EXCN0602Controller.class);

    @Inject
    Mapper beanMapper;

    @Inject
    JPAStockService stockService;

    @Inject
    JpaVendorAdapter jpaVendorAdapter;

    @ModelAttribute()
    public StockForm setUpForm() {
        return new StockForm();
    }

    @RequestMapping(value = "001", method = RequestMethod.GET)
    public String handle001(StockForm form, Model model) {
        beanMapper.map(stockService.findOne("EXCN0602001"), form);
        return "excn/jpaOptimisticLockViewExcpHandler";
    }

    @RequestMapping(value = "002", method = RequestMethod.GET)
    public String handle002(StockForm form, Model model) {
        beanMapper.map(stockService.findOne("EXCN0602002"), form);
        return "excn/jpaPessimisticLockViewExcpHandler";
    }

    @RequestMapping(value = "001", method = RequestMethod.POST, params = "buy")
    public String handle001Buy(StockForm form, Model model,
            RedirectAttributes attributes) {

        JPAStock stock = beanMapper.map(form, JPAStock.class);

        stock = stockService.buyWithOptimisticLock(stock, form
                .getPurchasingQuantity(), form.getSleepMillis());

        model.addAttribute("stock", stock);
        model.addAttribute(ResultMessages.success().add("excn.result.success"));
        return "excn/updateCompleteView";
    }

    @RequestMapping(value = "002", method = RequestMethod.POST, params = "buy")
    public String handle002Buy(StockForm form, Model model,
            RedirectAttributes attributes) {

        String dataBaseName = DataBaseInfo
                .getDataBaseID((HibernateJpaVendorAdapter) jpaVendorAdapter);
        logger.debug("Current Database Under Test ::" + dataBaseName);

        JPAStock stock = beanMapper.map(form, JPAStock.class);
        model.addAttribute("databaseId", dataBaseName);
        stock = stockService.buyWithPessimisticLockExcp(stock, form
                .getPurchasingQuantity(), form.getSleepMillis());

        model.addAttribute("stock", stock);
        model.addAttribute(ResultMessages.success().add("excn.result.success"));
        return "excn/updateCompleteView";
    }

    @ExceptionHandler(PessimisticLockingFailureException.class)
    // (1)
    public ModelAndView handlePessimisticLockingFailureException(
            PessimisticLockingFailureException e) {
        // (2)
        ExtendedModelMap modelMap = new ExtendedModelMap();
        ResultMessages resultMessages = ResultMessages.warning();
        resultMessages.add(ResultMessage.fromText("Other user updated!!"));
        modelMap.addAttribute(setUpForm());
        modelMap.addAttribute(resultMessages);
        String viewName = "common/error/exclusiveLockError";
        return new ModelAndView(viewName, modelMap);
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    // (1)
    public ModelAndView handleOptimisticLockingFailureException(
            OptimisticLockingFailureException e) {
        ExtendedModelMap modelMap = new ExtendedModelMap();
        ResultMessages resultMessages = ResultMessages.warning();
        resultMessages.add(ResultMessage.fromText("Other user updated!!"));
        modelMap.addAttribute(setUpForm());
        modelMap.addAttribute(resultMessages);
        String viewName = "common/error/exclusiveLockError";
        return new ModelAndView(viewName, modelMap);
    }
}
