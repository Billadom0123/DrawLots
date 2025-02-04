package com.example.DrawLots.model.bo;

import com.example.DrawLots.exception.exceptions.ParamCheckException;
import com.example.DrawLots.model.po.User;
import com.example.DrawLots.util.BCryptUtil;
import com.example.DrawLots.util.CheckFormatUtil;
import kong.unirest.Unirest;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/* author: koishikiss */
@NoArgsConstructor
public class SDULoginBO {
    private String sid;  //统一认证学号

    private String password;  //统一认证密码

    //检查sid是否正确填写
    @SuppressWarnings("unused")
    public void setSid(String sid) {
        if (CheckFormatUtil.checkSid(sid)) {
            this.sid = sid;
        }
        else {
            throw new ParamCheckException("请填写正确的学号格式!");
        }

    }

    //检查password是否正确填写
    @SuppressWarnings("unused")
    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new ParamCheckException("密码不可为空!");
        }
        else {
            this.password = password;
        }
    }

    //统一认证登录
    public User login(Method method) {

        //先尝试从数据库获取成员信息
        User user = method.tryGetUser(sid);

        //如果已经注册过，则检查数据库中密码，返回数据，否则注册
        if (user != null) {
            if (BCryptUtil.validate(password,user.getPassword())) return user;
            return null;
        }


        // 第一个接口：获取ticket
        // 按理来说不应该用字符串拼接的方式作为formData
        // 但这个接口如果进行url encode将无法正确识别
        // 是学校平台的问题
        // https://pass.sdu.edu.cn/cas/restlet/tickets username=${sid}&password=$password
        String ticket = Unirest.post("https://pass.sdu.edu.cn/cas/restlet/tickets")
                .body("username=" + sid + "&password=" + password)
                .asString()
                .getBody();

        //如果ticket不以TGT开头，说明登入失败
        if (!ticket.startsWith("TGT")) {
            return null;
        }


        // 第二个接口，获取sTicket
        // body处填任何一个在统一认证登记的网站都行，如信息服务大厅、选课系统
        // https://pass.sdu.edu.cn/cas/restlet/tickets/{ticket}
        String sTicket = Unirest.post("https://pass.sdu.edu.cn/cas/restlet/tickets/" + ticket)
                .body("service=https://service.sdu.edu.cn/tp_up/view?m=up")
                .asString()
                .getBody();

        if (!sTicket.startsWith("ST")) {
            return null;
        }


        // 第三个接口，获取姓名和学号
        // 结果是一个xml文件，需要自己从中提取需要的信息，Jsoup或正则均可
        // https://pass.sdu.edu.cn/cas/serviceValidate
        String validationResult = Unirest.get("https://pass.sdu.edu.cn/cas/serviceValidate")
                .queryString("ticket", sTicket)
                .queryString("service", "https://service.sdu.edu.cn/tp_up/view?m=up")
                .asString()
                .getBody();

        Document document = Jsoup.parse(validationResult);
        String name = document.getElementsByTag("cas:USER_NAME").text();//获取学生姓名

        return method.register(new User(sid, name, BCryptUtil.encrypt(password)));
    }

    //需要传入的方法
    public interface Method {

        //需要一个获取用户的接口
        User tryGetUser(String sid);

        //需要一个注册用户的接口
        User register(User newUser);
    }
}
