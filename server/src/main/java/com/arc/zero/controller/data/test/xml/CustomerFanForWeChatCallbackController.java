package com.arc.zero.controller.data.test.xml;

import com.arc.core.model.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信回调粉丝表数据
 * 企业可以通过获取成员的客户列表接口来获知成员添加了哪些客户，
 * 企业可设置外部联系人变更回调，成员添加和删除客户时将以事件的形式推送到指定URL，企业接收到外部联系人变更事件后，响应处理后即可保证获取到的企业客户列表是及时更新的列表。
 * //添加企业客户事件 AddCorporateCustomerEvent
 * //外部联系人免验证添加成员事件 addExternalContactsWithoutVerificationEvents.
 * //删除企业客户事件 removeCorporateCustomerEvent
 * //删除跟进成员事件 removeFollowUpMemberEvent
 *
 * @author 叶超
 * @since 2019/11/8 上午 10:54
 */
@Validated
@Slf4j
public class CustomerFanForWeChatCallbackController {

    /**
     * 测试xml 解析
     *
     * @param request
     * @return
     */

    @PostMapping(value = "/test/xml", consumes = "application/xml")
    public Object testXML(HttpServletRequest request) {
        Map<String, String> stringStringMap = xmlToMap(request);
        return ResponseVo.success(stringStringMap);
    }

    public static Map<String, String> xmlToMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();
        InputStream ins = null;
        try {
            ins = request.getInputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Document doc = null;
        try {
            doc = reader.read(ins);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
            return map;
        } catch (DocumentException e1) {
            e1.printStackTrace();
        } finally {
            try {
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}

