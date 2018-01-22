package org.ibase4j.core;

import base.email.Email;
import base.utils.EmailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ComponentScan
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class EmailTest {
    @Test
    public void sendEmail() {
        Email email = new Email("iBase4J@126.com", "先生，恭喜您",
            "您好：<br/><div style='text-indent:2em'>很高兴认识您！</div>" + "<div style='text-indent:2em'>有任务疑问请和我联系！</div>");
        EmailUtil.sendEmail(email);
    }
}
