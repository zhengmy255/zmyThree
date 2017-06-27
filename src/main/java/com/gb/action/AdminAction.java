package com.gb.action;


import com.gb.util.ConfigUtil;
import com.gb.util.MD5Util;
import com.gb.util.ReturnJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by admin on 2017/6/2.
 */
@Controller
@RequestMapping("admin")
public class AdminAction {

@RequestMapping("toshow")
    public String  toshow(){
        return "show";
}


}
