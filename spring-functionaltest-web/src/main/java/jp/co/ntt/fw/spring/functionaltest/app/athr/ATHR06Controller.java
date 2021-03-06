/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.fw.spring.functionaltest.app.athr;

import java.security.Principal;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ATHR06Controller {
    @RequestMapping("0601/001")
    public String handle0601001() {
        return "athr/loginForAccsessPolicy";
    }

    @RequestMapping(value = "0601/001/afterLogin")
    public String handle0601001(Principal principal, Model model) {
        Authentication authentication = (Authentication) principal;
        UserDetails userDetails = null;
        if (authentication != null) {
            userDetails = (UserDetails) authentication.getPrincipal();
        }
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("userEmail", userDetails.getUsername()
                    + "@example.com");
        }
        return "athr/showForAccessPolicy";
    }

    @RequestMapping({ "0601/001/accounts", "0601/001/manager", "0601/001/all" })
    public String handleAccessPolicy() {
        return "athr/showAccessPolicyAccessAllowedPage";
    }

    @RequestMapping("0601/002")
    public String handle0602001() {
        return "athr/loginForAccsessPolicyIpAddressAllow";
    }

    @RequestMapping(value = "0601/002/afterLogin")
    public String handle0601002(Principal principal, Model model) {
        Authentication authentication = (Authentication) principal;
        UserDetails userDetails = null;
        if (authentication != null) {
            userDetails = (UserDetails) authentication.getPrincipal();
        }
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("userEmail", userDetails.getUsername()
                    + "@example.com");
        }
        return "athr/showForAccessPolicyIpAddressAllow";
    }

    @RequestMapping({ "0601/002/manager", "0601/002/configurations",
            "0601/002/admin", "athr/0601/002/all" })
    public String handleAccessPolicyIpAddressAllow() {
        return "athr/showAccessPolicyAccessIpAddressAllowedPage";
    }

    @RequestMapping("0601/003")
    public String handle0601003() {
        return "athr/loginForAccsessPolicyIpAddressDeny";
    }

    @RequestMapping(value = "0601/003/afterLogin")
    public String handle0612003(Principal principal, Model model) {
        Authentication authentication = (Authentication) principal;
        UserDetails userDetails = null;
        if (authentication != null) {
            userDetails = (UserDetails) authentication.getPrincipal();
        }
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("userEmail", userDetails.getUsername()
                    + "@example.com");
        }
        return "athr/showForAccessPolicyIpAddressDeny";
    }

    @RequestMapping({ "0601/003/accounts", "0601/003/configurations",
            "0601/003/admin", "athr/0601/003/all" })
    public String handleAccessPolicyIpAddressDeny() {
        return "athr/showAccessPolicyAccessIpAddressDeniedPage";
    }

    @RequestMapping("0601/004")
    public String handle0601004() {
        return "athr/loginForAccsessPolicyDenyAll";
    }

    @RequestMapping(value = "0601/004/afterLogin")
    public String handle0601004(Principal principal, Model model) {
        Authentication authentication = (Authentication) principal;
        UserDetails userDetails = null;
        if (authentication != null) {
            userDetails = (UserDetails) authentication.getPrincipal();
        }
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("userEmail", userDetails.getUsername()
                    + "@example.com");
        }
        return "athr/showForAccessPolicyDenyAll";
    }

    @RequestMapping({ "0601/004/accounts", "0601/004/admin", "0601/004/all" })
    public String handleAccessPolicyDenyAll() {
        return "athr/showAccessPolicyAccessDenyAllPage";
    }

    @RequestMapping({ "0601/019", "0601/021" })
    public String handle0601019loginForPathVariable() {
        return "athr/loginForPathVariable";
    }

    @RequestMapping({ "0601/019/account/{userName}",
            "0601/021/account/{userName}" })
    public String handle0601019afterLogin(@PathVariable String userName,
            Model model) {
        model.addAttribute("username", userName);
        return "athr/showForPathVariableDsp";
    }

}
