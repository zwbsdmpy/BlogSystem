package com.duan.blogos.web.blog;

import com.duan.blogos.entity.blogger.BloggerAccount;
import com.duan.blogos.entity.blogger.BloggerProfile;
import com.duan.blogos.exception.api.blogger.BloggerNotLoggedInException;
import com.duan.blogos.exception.api.blogger.UnknownBloggerException;
import com.duan.blogos.manager.properties.BloggerProperties;
import com.duan.blogos.manager.validate.BloggerValidateManager;
import com.duan.blogos.service.blogger.BloggerAccountService;
import com.duan.blogos.service.blogger.BloggerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/3/7.
 *
 * @author DuanJiaNing
 */
@Controller
@RequestMapping("/{bloggerName}/setting")
public class BloggerSettingPageController {

    @Autowired
    private BloggerAccountService accountService;

    @Autowired
    private BloggerProfileService profileService;

    @Autowired
    private BloggerProperties bloggerProperties;

    @Autowired
    private BloggerValidateManager bloggerValidateManager;

    @RequestMapping
    public ModelAndView pageLike(HttpServletRequest request,
                                 @ModelAttribute
                                 @PathVariable String bloggerName) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/blogger/setting");

        BloggerAccount account = accountService.getAccount(bloggerName);
        int bloggerId;
        if (account == null) {
            mv.addObject("code", UnknownBloggerException.code);
            mv.addObject(bloggerProperties.getSessionNameOfErrorMsg(), "博主不存在！");
            mv.setViewName("error/error");
            return mv;
        } else if (!bloggerValidateManager.checkBloggerSignIn(request, bloggerId = account.getId())) {
            mv.addObject("code", BloggerNotLoggedInException.code);
            mv.addObject(bloggerProperties.getSessionNameOfErrorMsg(), "博主未登录！");
            mv.setViewName("error/error");
            return mv;
        }

        BloggerProfile profile = profileService.getBloggerProfile(bloggerId);
        mv.addObject("profile", profile);

        return mv;
    }

}