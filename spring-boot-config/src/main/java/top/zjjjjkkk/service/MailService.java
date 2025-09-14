package top.zjjjjkkk.service;

import top.zjjjjkkk.enums.ResultStatus;
import top.zjjjjkkk.model.Mail;

public interface MailService {
    ResultStatus sendSimpleMail(Mail mail);

    ResultStatus sendHTMLMail(Mail mail);
}
