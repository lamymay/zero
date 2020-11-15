package com.arc.zero.controller.data.test.download;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 测试多文件压缩后下载
 *
 * @author May
 * @since 2020/1/15 11:59
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class LicenseController {


    @RequestMapping({"/license","/obtainTicket.action"})
    public String obtainTicket(String salt, String userName) throws Exception {
        String content = "<ObtainTicketResponse>" +
                "<message></message>" +
                "<prolongationPeriod>607875500</prolongationPeriod>" +
                "<responseCode>OK</responseCode>" +
                "<salt>" + salt + "</salt>" +
                "<ticketId>1</ticketId>" +
                "<ticketProperties>licensee=" + userName + "	licenseType=0	</ticketProperties>" +
                "</ObtainTicketResponse>";
        return LicenseUtils.rsaSign(content);
    }

    @RequestMapping("/releaseTicket.action")
    public void releaseTicket(HttpServletResponse httpServletResponse) {
        httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @RequestMapping("ping.action")
    public String ping(String salt) throws Exception {
        String content = "<PingResponse>" +
                "<message></message>" +
                "<responseCode>OK</responseCode>" +
                "<salt>" + salt + "</salt>" +
                "</PingResponse>";
        return LicenseUtils.rsaSign(content);
    }
}
